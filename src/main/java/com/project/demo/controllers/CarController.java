package com.project.demo.controllers;

import com.project.demo.entities.Car;
//import com.project.demo.interfaces.CarService;
import com.project.demo.exceptions.ResponseException;
import com.project.demo.services.CarService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@Validated
@RequestMapping(path = "/cars")
public class CarController {

    private CarService carService;


    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }



    /*
    1. get electricCars, get affordableCars, get luxuryCars
     */

    // get all cars
    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List <Car> cars = carService.getAllCars();
        if (cars.isEmpty()) {
            return new ResponseEntity <>(cars, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity <>(cars, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity< Car> getCarById(@PathVariable int id) {
        Optional <Car> car = carService.getCarById(id);

        /*
        if (car.isPresent()) {
            return new ResponseEntity <>(car.get(), HttpStatus.OK);
        }
        return new ResponseEntity <>(null, HttpStatus.NOT_FOUND);
         */

        return car.map(value -> new ResponseEntity <>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity <>(null, HttpStatus.NOT_FOUND));

    }


    //get luxury cars
    @GetMapping("/luxury")
    public ResponseEntity<List<Car>> getLuxuryCars(){
        return new ResponseEntity<>(carService.getLuxuryCars(),HttpStatus.OK);
    }

    //get affordable cars
    @GetMapping("/affordable")
    public ResponseEntity<List<Car>>getAffordableCars(){
        return new ResponseEntity<>(carService.getAffordableCars(),HttpStatus.OK);
    }

    //get electric cars
    @GetMapping("/electric")
    public ResponseEntity<List<Car>>getElectricCars(){
        return new ResponseEntity <>( carService.getElectricCars(),HttpStatus.OK);
    }

    /*
    boolean
    public List<Car> getElectricCars(){
        return cars.stream.filter(Car::isElectric).toList;
    }     */



    // get electric cars by fuel type(enum)
    @GetMapping("/fuel")
    //@Valid for annotation like @NotNul/ @Email/ @NotBlank on the 'Car.java' class
    public ResponseEntity<List<Car>> getCarsByFuelType(@RequestParam(defaultValue = "0") int fuelType){
        return new ResponseEntity <>(carService.getCarsByFuelType(fuelType),HttpStatus.OK);
    }


}
