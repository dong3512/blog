package com.blog.service.animal;

import com.blog.domain.AnimalType;

public class DogService implements AnimalService{

    @Override
    public String getSound() {
        return "멍멍";
    }

    @Override
    public AnimalType getType() {
        return AnimalType.DOG;
    }
}
