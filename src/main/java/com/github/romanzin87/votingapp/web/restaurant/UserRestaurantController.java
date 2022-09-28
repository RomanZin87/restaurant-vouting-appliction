package com.github.romanzin87.votingapp.web.restaurant;

import com.github.romanzin87.votingapp.model.Restaurant;
import com.github.romanzin87.votingapp.to.RestaurantTo;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@CacheConfig(cacheNames = "restaurants")
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController extends AbstractRestaurantController {
    static final String REST_URL = "/api/restaurants";
    public static final LocalDate TODAY_MENU_DATE = LocalDate.now();

    @Override
    @GetMapping
    @Cacheable
    public List<RestaurantTo> getAll() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTo> get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping("{id}/with-dishes/")
    public ResponseEntity<Restaurant> getWithDishes(@PathVariable int id, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inMenuDate) {
        return super.getWithDishes(id, TODAY_MENU_DATE);
    }

    @GetMapping("/with-dishes")
    public List<Restaurant> getAllWithDishes(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inMenuDate) {
        return super.getAllWithDishes(TODAY_MENU_DATE);
    }
}
