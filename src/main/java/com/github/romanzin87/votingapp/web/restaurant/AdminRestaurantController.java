package com.github.romanzin87.votingapp.web.restaurant;

import com.github.romanzin87.votingapp.model.Restaurant;
import com.github.romanzin87.votingapp.to.RestaurantTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.github.romanzin87.votingapp.util.validation.ValidationUtil.*;

@RestController
@Slf4j
@CacheConfig(cacheNames = "restaurants")
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractRestaurantController {
    static final String REST_URL = "/api/admin/restaurants";

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

    @Override
    @GetMapping("{id}/with-dishes/")
    public ResponseEntity<Restaurant> getWithDishes(@PathVariable int id, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inMenuDate) {
        return super.getWithDishes(id, inMenuDate);
    }

    @Override
    @GetMapping("/with-dishes")
    public List<Restaurant> getAllWithDishes(@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate inMenuDate) {
        return super.getAllWithDishes(inMenuDate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant with id {}", id);
        repository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        Restaurant created = repository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Transactional
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {} with id {}", restaurant, id);
        Restaurant updated = repository.getExisted(id);
        updated.setName(restaurant.getName());
    }
}
