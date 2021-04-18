package com.example.lab04_2_demosb;

import com.example.lab04_2_demosb.model.Car;
import com.example.lab04_2_demosb.services.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebMvcTest
public class CarControllerTest {

    @Autowired
    MockMvc servlet;

    @MockBean
    CarService carService;

    @Test
    public void getCarById() throws Exception {
        when( carService.getCarById( anyLong() ) ).thenReturn(
                Optional.of(new Car("cybertruck", "tesla")));

        servlet.perform( MockMvcRequestBuilders.get("/cars/1") )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("maker").value("tesla"))
                .andExpect(jsonPath("model").value("cybertruck"));
    }

    @Test
    public void getCarByName() throws Exception {
        when( carService.getCarByName( anyString(), anyString() ) ).thenReturn(
                Optional.of(new Car("cybertruck", "tesla")));

        servlet.perform( MockMvcRequestBuilders.get("/cars/tesla/cybertruck")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("maker").value("tesla"))
                .andExpect(jsonPath("model").value("cybertruck"));
    }

    @Test
    public void getAllCars() throws Exception {
        Car tesla1 = new Car("cybertruck", "tesla");
        Car tesla2 = new Car("X", "tesla");
        when( carService.getAllCars() ).thenReturn( Arrays.asList(tesla1, tesla2) );

        servlet.perform( MockMvcRequestBuilders.get("/cars").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(equalTo(2))))
                .andExpect(jsonPath("$[0].model", is("cybertruck")))
                .andExpect(jsonPath("$[1].model", is("X")));
    }

    @Test
    public void createCar() throws Exception {
        Car tesla = new Car("cybertruck", "tesla");
        when( carService.saveCar( any() ) ).thenReturn( tesla );

        servlet.perform( MockMvcRequestBuilders.post("/cars").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(tesla)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.model", is("cybertruck")));
    }

}
