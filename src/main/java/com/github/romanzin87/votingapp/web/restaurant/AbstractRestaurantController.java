package com.github.romanzin87.votingapp.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import com.github.romanzin87.votingapp.model.Restaurant;
import com.github.romanzin87.votingapp.repository.RestaurantRepository;

import java.util.List;

import static com.github.romanzin87.votingapp.util.validation.ValidationUtil.checkExisted;

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
        checkExisted(repository.getExisted(id), id);
        return ResponseEntity.of(repository.getWithDishes(id));
    }

    public List<Restaurant> getAllWithDishes() {
        log.info("getAllWithDishes");
        return repository.getAllWithDishes();
    }
}
