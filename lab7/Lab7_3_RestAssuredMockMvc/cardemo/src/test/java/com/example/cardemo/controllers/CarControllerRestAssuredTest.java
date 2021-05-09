package com.example.cardemo.controllers;

import com.example.cardemo.JsonUtil;
import com.example.cardemo.entities.Car;
import com.example.cardemo.services.CarManagerService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@WebMvcTest(CarController.class)
public class CarControllerRestAssuredTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService carManagerService;

    @BeforeEach
    public void associateAssured() {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @Test
    public void whenPostCar_thenCreateCar() throws Exception {
        Car mclaren = new Car("McLaren", "Senna");
        when(carManagerService.save(Mockito.any())).thenReturn(mclaren);

        RestAssuredMockMvc.given().header("Content-Type", "application/json")
                .body(JsonUtil.toJson(mclaren))
                .post("/api/v1/cars")
                .then().assertThat().statusCode(201)
                .and().body("maker", equalTo("McLaren"))
                .and().body("model", equalTo("Senna"));

        verify(carManagerService, times(1)).save(mclaren);
    }

    @Test
    public void testGetValidCarDetails() {
        Car mclaren = new Car("McLaren", "Senna");
        mclaren.setCar_id(1L);
        when(carManagerService.getCarDetails(1L)).thenReturn(Optional.of(mclaren));

        RestAssuredMockMvc.given().when()
                .get("/api/v1/cars/1")
                .then().assertThat().statusCode(200)
                .and().body("maker", equalTo("McLaren"))
                .and().body("model", equalTo("Senna"));

        verify(carManagerService, times(1)).getCarDetails(mclaren.getCar_id());
    }

    @Test
    public void testGetInvalidCarDetails() {
        RestAssuredMockMvc.given().when()
                .get("/api/v1/cars/99")
                .then().assertThat().statusCode(404);
        verify(carManagerService, times(1)).getCarDetails(99L);
    }

    @Test
    public void testGetAllCars() {
        Car mclaren = new Car("McLaren", "Senna");
        Car jaguar = new Car("Jaguar", "XJ 220");
        Car lamborghini = new Car("Lamborghini", "Aventador SVJ");

        List<Car> allCars = Arrays.asList(mclaren, jaguar, lamborghini);

        given(carManagerService.getAllCars()).willReturn(allCars);

        RestAssuredMockMvc.given().when()
                .get("/api/v1/cars")
                .then().assertThat().statusCode(200)
                .and().body("", hasSize(3))
                .and().body("maker[0]", is(mclaren.getMaker()))
                .and().body("model[0]", is(mclaren.getModel()))
                .and().body("maker[1]", is(jaguar.getMaker()))
                .and().body("model[1]", is(jaguar.getModel()))
                .and().body("maker[2]", is(lamborghini.getMaker()))
                .and().body("model[2]", is(lamborghini.getModel()));

        verify(carManagerService, times(1)).getAllCars();
    }
}