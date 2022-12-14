package com.blog.domain;

import com.blog.service.animal.AnimalService;
import com.blog.service.animal.CatService;
import com.blog.service.animal.DogService;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;

@RequiredArgsConstructor
public enum AnimalType {
    CAT(CatService.class),
    DOG(DogService.class);

    private final Class<? extends AnimalService> animalService;

    public AnimalService create() {
        try {
            return animalService.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }

    }
}
