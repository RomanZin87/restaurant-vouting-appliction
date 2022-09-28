package com.github.romanzin87.votingapp.web.restaurant;

import com.github.romanzin87.votingapp.model.Restaurant;
import com.github.romanzin87.votingapp.web.MatcherFactory;
import com.github.romanzin87.votingapp.web.dish.DishTestData;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes");
    public static final Restaurant PIZZERIA = new Restaurant(1, "Pizzitalia");
    public static final Restaurant HUTOROK = new Restaurant(3, "Хуторок");
    public static final int PIZZERIA_ID = 1;
    public static final int HALVA_ID = 2;
    public static final int NOT_FOUND_RESTAURANT = 100_000;
    public static final List<Restaurant> allRestaurants = List.of(
            new Restaurant(4, "Parmesan"),
            new Restaurant(1, "Pizzitalia"),
            new Restaurant(2, "Халва"),
            new Restaurant(3, "Хуторок")
    );

    public static final Restaurant HALVA = new Restaurant(HALVA_ID, "Халва");

    public static final String TEST_DATE = "2022-09-24";

    static {
        HALVA.setDishes(List.of(DishTestData.BISHBARMAK, DishTestData.SASHLIK));
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "MacDuck");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(HALVA_ID, "Халва updated");
    }



}
