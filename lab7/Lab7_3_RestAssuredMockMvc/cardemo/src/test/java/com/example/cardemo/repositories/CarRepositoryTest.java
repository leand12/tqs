package com.example.cardemo.repositories;

import com.example.cardemo.entities.Car;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;


    @Test
    public void getCarById_returnCarDetails(){
        Car ferrari = new Car("Ferrari", "Enzo");
        entityManager.persistAndFlush(ferrari);
        Car foundCar = carRepository.findById(ferrari.getCar_id()).orElse(null);
        assertThat( foundCar ).isNotNull();
        assertThat( foundCar.getMaker() ).isEqualTo( ferrari.getMaker() );
        assertThat( foundCar.getCar_id() ).isEqualTo( ferrari.getCar_id() );
        assertThat( foundCar.getModel() ).isEqualTo( ferrari.getModel() );
    }

    @Test
    public void whenInvalidId_thenReturnNull() {
        Car car_db = carRepository.findById(-1L).orElse(null);
        assertThat(car_db).isNull();
    }

    @Test
    public void givenSetOfCars_whenFindAll_thenReturnAllCars() {
        Car ferrari = new Car("Ferrari", "Enzo");
        Car bugatti = new Car("Bugatti", "Chiron");
        Car ssc = new Car("SSC", "Tuatara");
        Arrays.asList(ferrari, bugatti, ssc).forEach(car -> {
            entityManager.persist(car);
        });
        List<Car> allCars = carRepository.findAll();
        assertThat(allCars).hasSize(3).extracting(Car::getModel).containsOnly(
                ferrari.getModel(), bugatti.getModel(), ssc.getModel()
        );
    }
}