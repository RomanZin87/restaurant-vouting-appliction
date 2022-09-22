package com.github.romanzin87.votingapp.web.restaurant;

import com.github.romanzin87.votingapp.model.Restaurant;
import com.github.romanzin87.votingapp.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes");
    public static final Restaurant PIZZERIA = new Restaurant(1, "Pizzitalia");
    public static final int PIZZERIA_ID = 1;
    public static final int HALVA_ID = 2;
    public static final int NOT_FOUND_RESTAURANT = 100_000;

    public static Restaurant getNew() {
        return new Restaurant(null, "MacDuck");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(HALVA_ID, "Халва updated");
    }
}
