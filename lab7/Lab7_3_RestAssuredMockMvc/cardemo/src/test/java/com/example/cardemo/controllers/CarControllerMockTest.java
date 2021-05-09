package com.example.cardemo.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.cardemo.JsonUtil;
import com.example.cardemo.entities.Car;
import com.example.cardemo.services.CarManagerService;

import java.util.Arrays;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
class CarControllerMockTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private CarManagerService carManagerService;


    @Test
    public void whenGetCar_thenReturnCar() throws Exception {
        when(carManagerService.getCarDetails(anyLong())).thenReturn(
                java.util.Optional.of(
                        new Car("McLaren", "Senna"))
        );
        mvc.perform(get("/api/v1/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is("McLaren")))
                .andExpect(jsonPath("$.model", is("Senna")));
    }

    @Test
    public void whenGetCars_thenReturnAllCars() throws  Exception {
        Car mclareen = new Car("McLaren", "Senna");
        Car jaguar = new Car("Jaguar", "XJ 220");
        Car lamborghini = new Car("Lamborghini", "Aventador SVJ");

        when(carManagerService.getAllCars()).thenReturn(Arrays.asList(mclareen, jaguar, lamborghini));

        mvc.perform(get("/api/v1/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$[0].maker", is("McLaren")))
                .andExpect(jsonPath("$[0].model", is("Senna")))
                .andExpect(jsonPath("$[1].maker", is("Jaguar")))
                .andExpect(jsonPath("$[1].model", is("XJ 220")))
                .andExpect(jsonPath("$[2].maker", is("Lamborghini")))
                .andExpect(jsonPath("$[2].model", is("Aventador SVJ")));
    }

    @Test
    public void whenPostCar_thenCreateCar() throws Exception {
        Car mclaren = new Car("McLaren", "Senna");
        when(carManagerService.save(Mockito.any())).thenReturn(mclaren);

        mvc.perform(post("/api/v1/cars")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(mclaren)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("McLaren")))
                .andExpect(jsonPath("$.model", is("Senna")));

        verify(carManagerService, times(1)).save(Mockito.any());
    }
}