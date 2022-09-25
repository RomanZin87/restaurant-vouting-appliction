package com.github.romanzin87.votingapp.web.restaurant;

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

    public ResponseEntity<Restaurant> get(int id) {
        log.info("get restaurant {}", id);
        return ResponseEntity.of(checkExisted(repository.findById(id), id));
    }

    public ResponseEntity<Restaurant> getWithDishes(int id, LocalDate inMenuDate) {
        log.info("getWithDishes for restaurant {} and date {}", id, inMenuDate);
        return ResponseEntity.of(repository.getWithDishes(id, inMenuDate));
    }

    public List<Restaurant> getAllWithDishes(LocalDate inMenuDate) {
        log.info("getAllWithDishes on date {}", inMenuDate);
        return repository.getAllWithDishes(inMenuDate);
    }
}
