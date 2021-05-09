package com.example.cardemo.controllers;

import com.example.cardemo.entities.Car;
import com.example.cardemo.services.CarManagerService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@RestController
@RequestMapping("/api/v1/")
public class CarController {

    @Autowired
    private CarManagerService carManagerService;

    @PostMapping(path = "/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        HttpStatus status = HttpStatus.CREATED;
        Car newCar = carManagerService.save(car);
        return new ResponseEntity<>(newCar, status);
    }

    @GetMapping(path = "/cars", produces = "application/json")
    public ResponseEntity<?> getAllCars() {
        HttpStatus status = HttpStatus.OK;
        List cars = carManagerService.getAllCars();
        return new ResponseEntity<>(cars, status);
    }

    @GetMapping(path = "/cars/{id}")
    public  ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long carId) {
        HttpStatus status = HttpStatus.OK;

        Car car = carManagerService.getCarDetails(carId).orElse(null);
        if (car == null) status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(car, status);
    }
}
