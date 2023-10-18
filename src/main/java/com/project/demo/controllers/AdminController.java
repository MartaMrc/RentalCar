package com.project.demo.controllers;

import com.project.demo.entities.Car;
import com.project.demo.exceptions.ResponseException;
import com.project.demo.services.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping(path = "/admin/cars")
public class AdminController {

    Car car= new Car();

    private final String ADMIN_KEY;
    private CarService carService;


    @Autowired
    public AdminController(CarService carService, @Value("${admin.key}") String key ) {
        this.ADMIN_KEY = key;
        this.carService = carService;
    }


    // POST -> add car
    @PostMapping
    public ResponseEntity<Car> addCar(@Valid @RequestBody Car car,
                                      @RequestHeader(name = "ADMIN-KEY", required = false) String key) throws ResponseException {

        System.out.println("You have added a new car: ");
        System.out.println(car.getModel()+ " with the license plate number: "+ car.getLicensePlateNo() + " has been successfully added!!!");
        // view all cars
        System.out.print(carService.getAllCars());

        if (!ADMIN_KEY.equals(key)) {
            return new ResponseEntity <>(null, HttpStatus.UNAUTHORIZED);
        }

        Car addedCar= carService.addCar(car);
        return new ResponseEntity<>(addedCar,HttpStatus.CREATED);

    }


    // PUT -> modify car
    @PutMapping
    public ResponseEntity<Car> modifyCarDetails(@Valid @RequestBody Car car,
                                                @RequestHeader(name = "ADMIN-KEY", required = false) String key) throws ResponseException{
        if (!ADMIN_KEY.equals(key)) {
            return new ResponseEntity <>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity <>(carService.modifyCarDetails(car),HttpStatus.OK);
    }


    // DELETE CAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCar(@PathVariable int id,
                                             @RequestHeader(name = "ADMIN-KEY", required = false) String key) throws ResponseException {
        System.out.println("The car was deleted!!!");

        if (!ADMIN_KEY.equals(key)) {
            return new ResponseEntity <>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity <>( carService.deleteCar(id),HttpStatus.OK);
    }
}
