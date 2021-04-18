package com.example.lab04_2_demosb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.*;


@Entity
@Getter @Setter
public class Car {

    @Id
    @GeneratedValue
    private Long carId;

    private String maker;
    private String model;

    public Car() {
    }

    public Car(String model, String maker) {
        this.model = model;
        this.maker = maker;
    }
}
