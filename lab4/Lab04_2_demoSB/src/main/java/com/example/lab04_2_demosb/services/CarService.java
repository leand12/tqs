package com.example.lab04_2_demosb.services;


import com.example.lab04_2_demosb.data.CarRepository;
import com.example.lab04_2_demosb.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car saveCar(Car car){
        return carRepository.save(car);
    }

    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    public Optional<Car> getCarByName(String model, String maker){
        return carRepository.findByModelAndMaker(model, maker);
    }

    public Optional<Car> getCarById(Long carId){
        return carRepository.findByCarId(carId);
    }
}