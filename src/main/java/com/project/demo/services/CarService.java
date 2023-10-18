package com.project.demo.services;

import com.project.demo.entities.Car;
//import com.project.demo.interfaces.CarService;
import com.project.demo.exceptions.ResponseError;
import com.project.demo.exceptions.ResponseException;
import com.project.demo.util.FuelType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class CarService {

    List<Car>cars;


    public CarService() {

        Logger logger= LoggerFactory.getLogger(CarService.class);
        logger.trace("TRACE");
        logger.debug("DEBUG");
        logger.info("INFO");
        logger.error("ERROR");
        logger.warn("WARNING");

        initDatabase();
    }

    private void initDatabase(){

        cars=new ArrayList <>(List.of(
                new Car(1,"B MM 113","Mazda CX-30",2022,280, FuelType.HYBRID,5,170.0),
                new Car(2,"NZ TH 202","BMW I5",2023,300,FuelType.ELECTRIC,5,190.0),
                new Car(3,"HB MD 01","Opel Corsa",2000,250,FuelType.DIESEL,5,90.90),
                new Car(4,"TR XB 32","Tesla X",2019,390,FuelType.ELECTRIC,5,250.0),
                new Car(5,"CUX RT 148","Mazda MX-5",2020,300,FuelType.UNKNOWN,2,200.0),
                new Car(6,"M WK 121","VW Golf IV",2009,280,FuelType.DIESEL,5,99.90)


        ));
    }


    public List<Car> getAllCars(){
        return cars;
    }
    public Optional<Car> getCarById(int id){
     return cars.stream()
              .filter(car -> car.getId()==id).findFirst();
    }

    public List<Car> getElectricCars() {
        return cars.stream()
                .filter(car -> car.getFuelType()==FuelType.ELECTRIC).collect(Collectors.toList());
    }

    public List<Car> getLuxuryCars() {
        return cars.stream()
                .filter(car -> car.getRentalPrice()>150)
                .collect(Collectors.toList());
    }
    public List<Car> getAffordableCars() {
        return cars.stream()
                .filter(car -> car.getRentalPrice()<150)
                .collect(Collectors.toList());
    }

    public List<Car> getCarsByFuelType(int fuelType){
        return cars.stream()
                .filter(car -> car.getFuelType().getId()==fuelType)
                .toList();
    }


    public Car addCar(Car car) throws ResponseException {
        Optional<Car>duplicatedCars= cars.stream()
                .filter(existingCar->existingCar.getId()==car.getId())
                .findAny();
        if(duplicatedCars.isPresent()){
            throw new ResponseException("Car already exist", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //add
        cars.add(car);
        return car;
    }


    public Car modifyCarDetails(Car car) throws ResponseException {
        Optional<Car>duplicatedCars= cars.stream()
                .filter(existingCar->
                {
                    if(existingCar.getId()==car.getId()){
                        existingCar.setLicensePlateNo(car.getLicensePlateNo());
                        existingCar.setModel(car.getModel());
                        existingCar.setRegistrationYear(car.getRegistrationYear());
                        existingCar.setHP(car.getHP());
                        existingCar.setFuelType(car.getFuelType());
                        existingCar.setSeatsNumber(car.getSeatsNumber());
                        existingCar.setRentalPrice(car.getRentalPrice());
                        return true;
                    }
                    return false;
                })
                .findAny();
        if(duplicatedCars.isPresent()){
            return car;
        }
        //add
        throw new ResponseException("Car with Id= " + car.getId()+" doesn't exist",HttpStatus.NOT_FOUND);
    }
    public boolean deleteCar(int id) throws ResponseException{
        for(Car car: cars){
            if(car.getId()==id){
                cars.remove(car);
                return true;
            }
        }
        throw new ResponseException("Id " + id + " not found ", HttpStatus.NOT_FOUND);
    }
}
