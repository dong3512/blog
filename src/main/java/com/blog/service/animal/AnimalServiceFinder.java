package com.blog.service.animal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AnimalServiceFinder {

    public final List<AnimalService> animalServices;

    public AnimalService find(String type) {
        return animalServices.stream()
                .filter(animalService -> animalService.getType().name().equals(type))
                .findAny()
                .orElseThrow(RuntimeException::new);
    }
}
