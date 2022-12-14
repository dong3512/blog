package com.blog.service.animal;

import com.blog.domain.AnimalType;

public class CatService implements AnimalService{

    @Override
    public String getSound() {
        return "냐옹";
    }

    @Override
    public AnimalType getType() {
        return AnimalType.CAT;
    }
}
