package com.github.romanzin87.votingapp.web.restaurant;

import com.github.romanzin87.votingapp.error.IllegalRequestDataException;
import com.github.romanzin87.votingapp.model.Restaurant;
import com.github.romanzin87.votingapp.repository.RestaurantRepository;
import com.github.romanzin87.votingapp.to.NamedTo;
import com.github.romanzin87.votingapp.to.RestaurantTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.romanzin87.votingapp.util.validation.ValidationUtil.checkExisted;

@Slf4j
public class AbstractRestaurantController {

    @Autowired
    protected RestaurantRepository repository;

    public List<RestaurantTo> getAll() {
        log.info("getAll");
        List<Restaurant> restaurants = repository.findAll();
        return restaurants.stream()
                .map(restaurant -> new RestaurantTo(restaurant.id(), restaurant.getName()))
                .sorted(Comparator.comparing(NamedTo::getName))
                .collect(Collectors.toList());
    }

    public ResponseEntity<RestaurantTo> get(int id) {
        log.info("get restaurant {}", id);
        Optional<Restaurant> optionalRestaurant = repository.findById(id);
        checkExisted(optionalRestaurant.orElse(null), id);
        RestaurantTo restaurantTo = new RestaurantTo(optionalRestaurant.get().id(), optionalRestaurant.get().getName());
        return ResponseEntity.ok(restaurantTo);
    }

    public ResponseEntity<Restaurant> getWithDishes(int id, LocalDate inMenuDate) {
        log.info("getWithDishes for restaurant {} and date {}", id, inMenuDate);
        Optional<Restaurant> optionalWithDishes = repository.getWithDishes(id, inMenuDate);
        if (optionalWithDishes.isEmpty()) {
            throw new IllegalRequestDataException("There is no menu for restaurant " + id + " on date: " + inMenuDate);
        }
        return ResponseEntity.of(optionalWithDishes);
    }

    public List<Restaurant> getAllWithDishes(LocalDate inMenuDate) {
        log.info("getAllWithDishes on date {}", inMenuDate);
        List<Restaurant> allWithDishes = repository.getAllWithDishes(inMenuDate);
        if (allWithDishes.isEmpty()) {
            throw new IllegalRequestDataException("There is no menu for this date " + inMenuDate);
        }
        return allWithDishes;
    }
}
