package com.github.romanzin87.votingapp.repository;

import com.github.romanzin87.votingapp.error.DataConflictException;
import com.github.romanzin87.votingapp.model.Dish;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id=:restaurantId")
    List<Dish> getAll(int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.id = :id and d.restaurant.id = :restaurantId")
    Optional<Dish> get(int id, int restaurantId);

    default Dish checkBelong(int id, int restaurantId) {
        return get(id, restaurantId).orElseThrow(
                () -> new DataConflictException("Dish with id=" + id + " doesn't belong to Restaurant with id=" + restaurantId));
    }
}
