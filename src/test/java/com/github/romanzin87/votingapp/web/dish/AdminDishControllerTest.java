package com.github.romanzin87.votingapp.web.dish;

import com.github.romanzin87.votingapp.model.Dish;
import com.github.romanzin87.votingapp.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.github.romanzin87.votingapp.repository.DishRepository;
import com.github.romanzin87.votingapp.repository.RestaurantRepository;
import com.github.romanzin87.votingapp.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.romanzin87.votingapp.web.user.UserTestData.ADMIN_MAIL;

class AdminDishControllerTest extends AbstractControllerTest {

    public static final String REST_URL = "/api/admin/restaurant/";

    @Autowired
    DishRepository dishRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DishTestData.RESTAURANT_ID + "/dish/" + DishTestData.DISH_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(DishTestData.DISH_MATCHER.contentJson(DishTestData.TEST_DISH));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DishTestData.RESTAURANT_ID + "/dish"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DishTestData.RESTAURANT_ID + "/dish/" + DishTestData.DISH_ID))
                .andExpect(status().isNoContent());
        assertFalse(dishRepository.existsById(DishTestData.DISH_ID));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Dish newDish = DishTestData.getNew();
        newDish.setRestaurant(restaurantRepository.getExisted(DishTestData.RESTAURANT_ID));
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL + DishTestData.RESTAURANT_ID + "/dish/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated())
                .andDo(print());
        Dish created = DishTestData.DISH_MATCHER.readFromJson(action);
        newDish.setId(created.id());
        DishTestData.DISH_MATCHER.assertMatch(created, newDish);
        DishTestData.DISH_MATCHER.assertMatch(dishRepository.getExisted(created.id()), newDish);
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void update() throws Exception {
        Dish updatedDish = DishTestData.getUpdated();
        updatedDish.setRestaurant(restaurantRepository.getExisted(DishTestData.RESTAURANT_ID));
        perform(MockMvcRequestBuilders.put(REST_URL + DishTestData.RESTAURANT_ID + "/dish/" + DishTestData.DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedDish)))
                .andExpect(status().isNoContent())
                .andDo(print());
        DishTestData.DISH_MATCHER.assertMatch(dishRepository.getById(DishTestData.DISH_ID), updatedDish);
    }
}