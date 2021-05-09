package com.example.cardemo.services;

import org.springframework.stereotype.Service;
import com.example.cardemo.entities.Car;
import com.example.cardemo.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerService {

    @Autowired
    private CarRepository carRepository;

    public Car save(Car car) {
        return this.carRepository.save(car);
    }

    public void CarManagerService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Optional<Car> getCarDetails(Long carId){
        return carRepository.findById(carId);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
}
