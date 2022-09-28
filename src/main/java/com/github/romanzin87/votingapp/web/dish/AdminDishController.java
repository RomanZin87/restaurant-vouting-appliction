package com.github.romanzin87.votingapp.web.dish;

import com.github.romanzin87.votingapp.model.Dish;
import com.github.romanzin87.votingapp.model.Restaurant;
import com.github.romanzin87.votingapp.repository.DishRepository;
import com.github.romanzin87.votingapp.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static com.github.romanzin87.votingapp.util.validation.ValidationUtil.*;

@Slf4j
@RestController
@AllArgsConstructor
@CacheConfig(cacheNames = {"dishes", "restaurants"})
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminDishController {
    public static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final EntityManager em;

    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get dish with id {}", id);
        Dish dish = dishRepository.checkBelong(id, restaurantId);
        return ResponseEntity.ok().body(dish);
    }

    @GetMapping
    @Cacheable
    @Transactional
    public List<Dish> getAll(@PathVariable int restaurantId) {
        log.info("getAll for restaurant {}", restaurantId);
        checkExisted(restaurantRepository.getExisted(restaurantId), restaurantId);
        return dishRepository.getAll(restaurantId);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(allEntries = true)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete dish with id {}", id);
        dishRepository.checkBelong(id, restaurantId);
        dishRepository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(allEntries = true)
    @Transactional
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        log.info("create dish {} for restaurant {}", dish, restaurantId);
        checkNew(dish);
        dish.setRestaurant(restaurantRepository.getExisted(restaurantId));
        Dish created = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    @Transactional
    public void update(@PathVariable int restaurantId, @PathVariable int id, @Valid @RequestBody Dish dish) {
        log.info("update {} for restaurant {}", dish, restaurantId);
        assureIdConsistent(dish, id);
        dishRepository.checkBelong(id, restaurantId);
        dish.setRestaurant(em.getReference(Restaurant.class, restaurantId));
        dishRepository.save(dish);
    }
}
