package com.security.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("product")
@RestController
public class ProductController {

    List<Car> carList = new ArrayList<>(List.of(
            new Car(1, "Nexon", "Tata", 1400000),
            new Car(2, "Xuv 300", "Mahindra", 1500000)
    ));
     record Car(int id, String name, String Company, int price){}

    @GetMapping("/get-all")
    public List<Car> getAllCars()
    {
        return carList;
    }

    @PostMapping("add")
    public String addCar(@RequestBody Car car)
    {
        int size = carList.size();
        this.carList.add(car);
        if(size < carList.size())
            return "Car is added successfully";
        else
            return "Something wrong happened! Try again after some time!";

    }
}
