package com.example.cardemo.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@EqualsAndHashCode
@Setter
@Getter
@Data
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long car_id;

    private String maker;

    private String model;

    public Car() {
    }

    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }
}
