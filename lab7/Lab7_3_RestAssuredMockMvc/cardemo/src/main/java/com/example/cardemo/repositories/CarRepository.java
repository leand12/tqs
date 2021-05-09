package com.example.cardemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.cardemo.entities.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
