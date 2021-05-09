package com.example.cardemo.services;

import com.example.cardemo.entities.Car;
import com.example.cardemo.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class CarManagerServiceTest {

    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    @BeforeEach
    void setUp() {
        Car mclareen = new Car("McLaren", "Senna");
        mclareen.setCar_id(1L);
        Car jaguar = new Car("Jaguar", "XJ 220");
        jaguar.setCar_id(2L);
        Car lamborghini = new Car("Lamborghini", "Aventador SVJ");
        lamborghini.setCar_id(3L);
        List<Car> cars = Arrays.asList(mclareen, jaguar, lamborghini);

        given(carRepository.findById(mclareen.getCar_id())).willReturn(Optional.of(mclareen));
        given(carRepository.findById(jaguar.getCar_id())).willReturn(Optional.of(jaguar));
        given(carRepository.findById(lamborghini.getCar_id())).willReturn(Optional.of(lamborghini));

        given(carRepository.findById(0L)).willReturn(Optional.empty());
        given(carRepository.findAll()).willReturn(cars);
    }

    @Test
    public void whenValidId_thenCarShouldBeFound() {
        Car mclaren = carManagerService.getCarDetails(1L).orElse(null);
        assertThat(mclaren).isNotNull();
        assertThat(mclaren.getCar_id()).isEqualTo(1L);
        assertThat(mclaren.getMaker()).isEqualTo("McLaren");
        assertThat(mclaren.getModel()).isEqualTo("Senna");
        Mockito.verify(carRepository,
                VerificationModeFactory.times(1))
                .findById(Mockito.anyLong());
    }

    @Test
    public void whenInvalidId_thenCarShouldNotBeFound() {
        Optional<Car> invalidCar = carManagerService.getCarDetails(0L);
        Mockito.verify(carRepository,
                VerificationModeFactory.times(1))
                .findById(Mockito.anyLong());

        assertThat(invalidCar).isEmpty();
    }

    @Test
    public void whenGivenCars_thenReturnAll() {
        List<Car> allCars = carManagerService.getAllCars();

        Mockito.verify(carRepository,
                VerificationModeFactory.times(1))
                .findAll();

        assertThat(allCars)
                .hasSize(3)
                .extracting(Car::getMaker)
                .contains(
                        "McLaren",
                        "Jaguar",
                        "Lamborghini");
    }
}