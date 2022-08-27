package ru.javaops.rzinnatov.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.rzinnatov.model.Dish;
import ru.javaops.rzinnatov.repository.DishRepository;
import ru.javaops.rzinnatov.repository.RestaurantRepository;

@Service
@AllArgsConstructor
public class DishService {
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    @Transactional
    public Dish save(Dish dish, int restId) {
        dish.setRestaurant(restaurantRepository.getExisted(restId));
        return dishRepository.save(dish);
    }
}
