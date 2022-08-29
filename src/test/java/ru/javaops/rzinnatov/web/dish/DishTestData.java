package ru.javaops.rzinnatov.web.dish;

import ru.javaops.rzinnatov.model.Dish;
import ru.javaops.rzinnatov.web.MatcherFactory;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant", "created");
    public static final Integer RESTAURANT_ID = 1;
    public static final Integer DISH_ID = 1;
    public static final Dish TEST_DISH = new Dish(1, "Маргарита", 500 );

    public static Dish getNew() {
        return new Dish(null, "Шаверма", 250 );
    }

    public static Dish getUpdated() {
        return new Dish(1, "Паста", 550 );
    }
}
