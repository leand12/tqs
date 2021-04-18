package com.example.lab04_2_demosb;

import com.example.lab04_2_demosb.data.CarRepository;
import com.example.lab04_2_demosb.model.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CarRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void whenFindCarByModelAndMaker_thenReturnCar(){
        Car car = new Car("cybertruck", "tesla");
        entityManager.persistAndFlush(car);
        Car found = carRepository.findByModelAndMaker(car.getModel(), car.getMaker()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getModel()).isEqualTo(car.getModel());
        assertThat(found.getMaker()).isEqualTo(car.getMaker());
    }

    @Test
    public void whenFindCarById_thenReturnCar(){
        Car car = new Car("cybertruck", "tesla");
        entityManager.persistAndFlush(car);
        Car found = carRepository.findById(car.getCarId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getCarId()).isEqualTo(car.getCarId());
        assertThat(found.getModel()).isEqualTo(car.getModel());
        assertThat(found.getMaker()).isEqualTo(car.getMaker());
    }

    @Test
    public void whenNonExistingCarId_thenReturnNull() {
        Car car_db = carRepository.findById(-1L).orElse(null);
        assertThat(car_db).isNull();
    }

    @Test
    public void givenSetOfCars_whenFindAll_thenReturnAllCats() {
        Car tesla1 = new Car("cybertruck", "tesla");
        Car tesla2 = new Car("X", "tesla");
        Car tesla3 = new Car("Y", "tesla");

        entityManager.persist(tesla1);
        entityManager.persist(tesla2);
        entityManager.persist(tesla3);
        entityManager.flush();

        List<Car> allCars = carRepository.findAll();

        assertThat(allCars).hasSize(3).extracting(Car::getModel)
                .containsOnly(tesla1.getModel(), tesla2.getModel(), tesla3.getModel());
    }
}
