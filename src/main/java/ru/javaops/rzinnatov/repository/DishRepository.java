package ru.javaops.rzinnatov.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.javaops.rzinnatov.model.Dish;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
}
