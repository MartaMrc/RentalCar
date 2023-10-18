package com.project.demo.entities;


import com.project.demo.util.FuelType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private int id;
    private String licensePlateNo;
    @NotBlank
    private String model;
    private int registrationYear;
    private int HP;
    @NotNull
    private FuelType fuelType;
    @Max(value = 7, message = "We can only rent cars up to 7 seats!!! ")
    private int seatsNumber;
    @Min(value = 0, message = " The rental price cannot be 0!!!")
    private Double rentalPrice;

//    private boolean isElectric;


}
