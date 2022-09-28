package com.github.romanzin87.votingapp.web.dish;

import com.github.romanzin87.votingapp.model.Dish;
import com.github.romanzin87.votingapp.web.MatcherFactory;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant", "created");
    public static final Integer RESTAURANT_ID = 1;
    public static final Integer DISH_ID = 1;
    public static final Dish MARGARITA = new Dish(1, "Маргарита", 499.9, LocalDate.of(2022, Month.SEPTEMBER, 25));
    public static final Dish BISHBARMAK = new Dish(5, "Бишбармак", 500, LocalDate.of(2022, Month.SEPTEMBER, 24));
    public static final Dish SASHLIK = new Dish(7, "Шашлык", 700, LocalDate.of(2022, Month.SEPTEMBER, 24));

    public static final List<Dish> allPizzas = List.of(MARGARITA,
            new Dish(2, "Пепперони", 649.9, LocalDate.of(2022, Month.SEPTEMBER, 24)),
            new Dish(3, "Гавайская", 579.9, LocalDate.of(2022, Month.SEPTEMBER, 25)),
            new Dish(4, "Моцарела", 649.9, LocalDate.of(2022, Month.SEPTEMBER, 26))
    );

    public static Dish getNew() {
        return new Dish(null, "Шаверма", 250, LocalDate.now());
    }

    public static Dish getUpdated() {
        return new Dish(1, "Паста", 550, LocalDate.now());
    }
}
