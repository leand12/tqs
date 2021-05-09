package com.example.cardemo.controllers;

import com.example.cardemo.entities.Car;
import com.example.cardemo.repositories.CarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "application-integrationtest.properties")
public class CarControllerITTestRealDB {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }

    @Test
    public void testPostCreateValidCar(){
        Car ferrari = new Car("Ferrari", "Senna");
        ferrari.setCar_id(1L);

        ResponseEntity<Car> entity = restTemplate
                .postForEntity("/api/v1/cars",
                        ferrari, Car.class
                );

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        List<Car> cars = carRepository.findAll();
        assertThat(cars)
                .extracting(Car::getModel)
                .containsOnly("Senna");
    }

    @Test
    public void testGetAllCars() {
        createTestCar("McLaren", "Senna");
        createTestCar("Jaguar", "XJ 220");
        createTestCar("Lamborghini", "Aventador SVJ");

        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/v1/cars",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Car>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .extracting(Car::getModel)
                .containsExactly("Senna", "XJ 220", "Aventador SVJ");
    }

    @Test
    void testGetValidCarDetails() {
        createTestCar("Ferrari","Senna");

        ResponseEntity<Car> entity = restTemplate.getForEntity("/api/v1/cars/1", Car.class);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).extracting(Car::getModel).isEqualTo("Senna");
    }

    @Test
    public void testGetInvalidCarDetails() {
        ResponseEntity<Car> response = restTemplate
                .exchange("/api/cars/1",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private void createTestCar(String maker, String model) {
        Car car = new Car(maker, model);
        carRepository.saveAndFlush(car);
    }
}
