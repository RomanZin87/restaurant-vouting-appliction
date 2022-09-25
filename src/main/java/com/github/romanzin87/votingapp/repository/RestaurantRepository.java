package com.github.romanzin87.votingapp.repository;

import com.github.romanzin87.votingapp.model.Restaurant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("SELECT r FROM Restaurant r LEFT JOIN FETCH r.dishes d WHERE r.id=:id AND d.inMenuDate=:inMenuDate")
    Optional<Restaurant> getWithDishes(int id, LocalDate inMenuDate);

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.dishes d WHERE d.inMenuDate=:inMenuDate ORDER BY r.name")
    List<Restaurant> getAllWithDishes(LocalDate inMenuDate);
}
