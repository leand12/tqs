package com.example.lab04_2_demosb.controller;

import com.example.lab04_2_demosb.model.Car;
import com.example.lab04_2_demosb.services.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CarController {

    private CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars/{maker}/{model}")
    private ResponseEntity<Car> getCarByName(@PathVariable String model, @PathVariable String maker) {
        Car car = carService.getCarByName(model, maker).orElse(null);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<?> getCarById(@PathVariable long id){
        Car car = carService.getCarById(id).orElse(null);
        return new ResponseEntity<>(car, HttpStatus.OK);
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getCars(){
        List cars = carService.getAllCars();
        return new ResponseEntity<>(cars,HttpStatus.OK);
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car car){
        Car savedCar = carService.saveCar(car);
        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }
}
