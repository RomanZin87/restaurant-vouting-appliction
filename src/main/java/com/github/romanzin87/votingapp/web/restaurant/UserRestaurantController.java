package com.github.romanzin87.votingapp.web.restaurant;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.romanzin87.votingapp.model.Restaurant;

import java.util.List;

@RestController
@CacheConfig(cacheNames = "restaurants")
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController extends AbstractRestaurantController {
    static final String REST_URL = "/api/restaurant";

    @Override
    @GetMapping
    @Cacheable
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/with-dishes/{id}")
    public ResponseEntity<Restaurant> getWithDishes(@PathVariable int id) {
        return super.getWithDishes(id);
    }

    @Override
    @GetMapping("/with-dishes")
    public List<Restaurant> getAllWithDishes() {
        return super.getAllWithDishes();
    }
}
