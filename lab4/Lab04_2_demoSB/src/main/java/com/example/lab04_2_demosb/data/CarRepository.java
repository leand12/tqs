package com.example.lab04_2_demosb.data;


import com.example.lab04_2_demosb.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByModelAndMaker(String model, String maker);
    Optional<Car> findByCarId(Long carId);
    List<Car> findAll();
}
