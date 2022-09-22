package com.github.romanzin87.votingapp.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.github.romanzin87.votingapp.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r WHERE r.id=?1")
    Optional<Restaurant> getWithDishes(int id);

    @EntityGraph(attributePaths = {"dishes"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r")
    List<Restaurant> getAllWithDishes();

}