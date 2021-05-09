package com.example.cardemo.controllers;

import com.example.cardemo.JsonUtil;
import com.example.cardemo.entities.Car;
import com.example.cardemo.repositories.CarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import io.restassured.RestAssured;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class CarControllerTestContainersTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private CarRepository repository;

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres")
            .withUsername("admin")
            .withPassword("password")
            .withDatabaseName("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }


    @Test
    public void whenPostCar_thenCreateCar() throws Exception {
        Car mclaren = new Car("McLaren", "Senna");
        repository.saveAndFlush(mclaren);

        RestAssured.given().header("Content-Type", "application/json")
                .body(JsonUtil.toJson(mclaren))
                .post(getBaseUrl()+"/api/v1/cars")
                .then().assertThat().statusCode(201)
                .and().body("maker", equalTo("McLaren"))
                .and().body("model", equalTo("Senna"));
    }

    @Test
    public void testGetValidCarDetails() {
        Car mclaren = new Car("McLaren", "Senna");
        repository.saveAndFlush(mclaren);


        RestAssured.given().when()
                .get(getBaseUrl()+"/api/v1/cars/"+mclaren.getCar_id().intValue())
                .then().assertThat().statusCode(200)
                .and().body("maker", equalTo("McLaren"))
                .and().body("model", equalTo("Senna"));
    }

    @Test
    public void testGetInvalidCarDetails() {
        RestAssured.given().when()
                .get(getBaseUrl()+"/api/v1/cars/99")
                .then().assertThat().statusCode(404);
    }

    @Test
    public void testGetAllCars() {
        createTestCar("McLaren", "Senna");
        createTestCar("Jaguar", "XJ 220");
        createTestCar("Lamborghini", "Aventador SVJ");

        RestAssured.given().when()
                .get(getBaseUrl()+"/api/v1/cars")
                .then().assertThat().statusCode(200)
                .and().body("", hasSize(3))
                .and().body("maker[0]", is("McLaren"))
                .and().body("model[0]", is("Senna"))
                .and().body("maker[1]", is("Jaguar"))
                .and().body("model[1]", is("XJ 220"))
                .and().body("maker[2]", is("Lamborghini"))
                .and().body("model[2]", is("Aventador SVJ"));
    }


    private void createTestCar(String maker, String model) {
        Car car = new Car(maker, model);
        repository.saveAndFlush(car);
    }

    public String getBaseUrl() {
        return "http://localhost:"+randomServerPort;
    }
}
