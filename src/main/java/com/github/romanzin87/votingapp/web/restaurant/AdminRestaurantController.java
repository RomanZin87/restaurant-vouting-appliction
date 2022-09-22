package com.github.romanzin87.votingapp.web.restaurant;

import com.github.romanzin87.votingapp.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.romanzin87.votingapp.model.Restaurant;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@CacheConfig(cacheNames = "restaurants")
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractRestaurantController {
    static final String REST_URL = "/api/admin/restaurant";

    @Override
    @GetMapping
    @Cacheable
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        ValidationUtil.checkExisted(repository.getExisted(id), id);
        return ResponseEntity.of(repository.findById(id));
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void delete(@PathVariable int id) {
        ValidationUtil.checkExisted(repository.getExisted(id), id);
        repository.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        ValidationUtil.checkNew(restaurant);
        Restaurant created = repository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {} with id {}", restaurant, id);
        ValidationUtil.checkExisted(repository.getExisted(id), id);
        ValidationUtil.assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }
}