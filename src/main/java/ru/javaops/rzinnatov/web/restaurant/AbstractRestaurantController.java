package ru.javaops.rzinnatov.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import ru.javaops.rzinnatov.model.Restaurant;
import ru.javaops.rzinnatov.repository.RestaurantRepository;

import java.util.List;

@Slf4j
public class AbstractRestaurantController {

    @Autowired
    protected RestaurantRepository repository;

    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public ResponseEntity<Restaurant> getWithDishes(int id) {
        log.info("getWithDishes {}", id);
        return ResponseEntity.of(repository.getWithDishes(id));
    }

    public List<Restaurant> getAllWithDishes() {
        log.info("getAllWithDishes");
        return repository.getAllWithDishes();
    }
}
