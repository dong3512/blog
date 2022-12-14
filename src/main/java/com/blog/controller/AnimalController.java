package com.blog.controller;

// type -> cat -> CatService
// type -> dog -> DogService

// cat -> 냐옹
// dog -> 멍멍

import com.blog.domain.AnimalType;
import com.blog.service.animal.AnimalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AnimalController {

//    private final AnimalServiceFinder animalServiceFinder;

    private final Map<String, AnimalService> animalService;
    @GetMapping("/sound")
    public String sound(@RequestParam String type) {
        AnimalType animalType = AnimalType.valueOf(type);
        AnimalService service = animalType.create();

        return service.getSound();

    }

}
