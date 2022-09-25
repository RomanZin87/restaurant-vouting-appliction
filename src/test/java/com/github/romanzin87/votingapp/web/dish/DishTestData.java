package com.github.romanzin87.votingapp.web.dish;

import com.github.romanzin87.votingapp.model.Dish;
import com.github.romanzin87.votingapp.web.MatcherFactory;

import java.time.LocalDate;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant", "created");
    public static final Integer RESTAURANT_ID = 1;
    public static final Integer DISH_ID = 1;
    public static final Dish TEST_DISH = new Dish(1, "Маргарита", 500, LocalDate.now());

    public static Dish getNew() {
        return new Dish(null, "Шаверма", 250, LocalDate.now());
    }

    public static Dish getUpdated() {
        return new Dish(1, "Паста", 550, LocalDate.now());
    }
}
