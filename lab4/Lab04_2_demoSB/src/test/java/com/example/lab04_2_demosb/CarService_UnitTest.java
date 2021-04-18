package com.example.lab04_2_demosb;

import com.example.lab04_2_demosb.data.CarRepository;
import com.example.lab04_2_demosb.model.Car;
import com.example.lab04_2_demosb.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
public class CarService_UnitTest {

    @Mock(lenient=true)
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {
        Car tesla1 = new Car("cybertruck", "tesla");
        Car tesla2 = new Car("X", "tesla");
        Car tesla3 = new Car("Y", "tesla");

        tesla1.setCarId(1L);

        when( carRepository.findByCarId(tesla1.getCarId()) ).thenReturn(Optional.of(tesla1));
        when( carRepository.findByModelAndMaker(tesla1.getModel(), tesla1.getMaker()) ).thenReturn(Optional.of(tesla1));
        when( carRepository.findByModelAndMaker(tesla2.getModel(), tesla2.getMaker()) ).thenReturn(Optional.of(tesla2));
        when( carRepository.findByModelAndMaker("???","???") ).thenReturn(null);
        when( carRepository.findAll() ).thenReturn(Arrays.asList(tesla1, tesla2, tesla3));
        when( carRepository.findByCarId(2L) ).thenReturn( Optional.empty() );
    }

    @Test
    public void getCarByName_returnCar() {
        Car car = carService.getCarByName( "cybertruck", "tesla").orElse(null);
        assertThat( car.getModel() ).isEqualTo("cybertruck");
        assertThat( car.getMaker() ).isEqualTo("tesla");
        verifyFindByModelAndMakerIsCalledOnce("cybertruck", "tesla");
    }

    @Test
    public void getCarByName_whenNonExisting_returnNull() {
        Optional<Car> car = carService.getCarByName( "???", "???");
        assertThat(car).isNull();
    }

    @Test
    public void getCarById_returnCar() {
        Car car = carService.getCarById( 1L).orElse(null);
        assertThat( car.getModel() ).isEqualTo("cybertruck");
        assertThat( car.getMaker() ).isEqualTo("tesla");
        verifyFindByIdIsCalledOnce();
    }

    @Test
    public void getCarById_whenNonExisting_returnNull() {
        Car car = carService.getCarById( 2L).orElse(null);
        assertThat(car).isNull();
        verifyFindByIdIsCalledOnce();
    }

    @Test
    public void getAllCars_return3Cars() {
        Car tesla1 = new Car("cybertruck", "tesla");
        Car tesla2 = new Car("X", "tesla");
        Car tesla3 = new Car("Y", "tesla");

        List<Car> allEmployees = carService.getAllCars();
        verifyFindAllCarsIsCalledOnce();
        assertThat(allEmployees)
                .hasSize(3)
                .extracting(Car::getModel)
                .contains(tesla1.getModel(), tesla2.getModel(), tesla3.getModel());
    }

    private void verifyFindByModelAndMakerIsCalledOnce(String model, String maker) {
        verify(carRepository, VerificationModeFactory.times(1)).findByModelAndMaker(model, maker);
    }

    private void verifyFindByIdIsCalledOnce() {
        verify(carRepository, VerificationModeFactory.times(1)).findByCarId(anyLong());
    }

    private void verifyFindAllCarsIsCalledOnce() {
        verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }

}
