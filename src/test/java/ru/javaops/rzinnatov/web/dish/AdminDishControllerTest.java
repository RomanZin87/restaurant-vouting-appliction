package ru.javaops.rzinnatov.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.rzinnatov.model.Dish;
import ru.javaops.rzinnatov.repository.DishRepository;
import ru.javaops.rzinnatov.repository.RestaurantRepository;
import ru.javaops.rzinnatov.util.JsonUtil;
import ru.javaops.rzinnatov.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.rzinnatov.web.dish.DishTestData.*;
import static ru.javaops.rzinnatov.web.user.UserTestData.ADMIN_MAIL;

class AdminDishControllerTest extends AbstractControllerTest {

    public static final String REST_URL = "/api/admin/restaurant/";

    @Autowired
    DishRepository dishRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID + "/dish/" + DISH_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(DISH_MATCHER.contentJson(TEST_DISH));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT_ID + "/dish"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT_ID + "/dish/" + DISH_ID))
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.existsById(DISH_ID));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Dish newDish = getNew();
        newDish.setRestaurant(restaurantRepository.getExisted(RESTAURANT_ID));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + RESTAURANT_ID + "/dish/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated())
                .andDo(print());
        Dish created = DISH_MATCHER.readFromJson(action);
        newDish.setId(created.id());
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishRepository.getExisted(created.id()), newDish);
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void update() throws Exception {
        Dish updatedDish = getUpdated();
        updatedDish.setRestaurant(restaurantRepository.getExisted(RESTAURANT_ID));
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT_ID + "/dish/" + DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedDish)))
                .andExpect(status().isNoContent())
                .andDo(print());
        DISH_MATCHER.assertMatch(dishRepository.getById(DISH_ID), updatedDish);
    }
}