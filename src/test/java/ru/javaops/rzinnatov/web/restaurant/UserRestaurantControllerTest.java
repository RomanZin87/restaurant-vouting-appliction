package ru.javaops.rzinnatov.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.rzinnatov.repository.RestaurantRepository;
import ru.javaops.rzinnatov.web.AbstractControllerTest;

import static ru.javaops.rzinnatov.web.user.UserTestData.USER_MAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.rzinnatov.web.restaurant.RestaurantTestData.*;


class UserRestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL = UserRestaurantController.REST_URL + "/";

    @Autowired
    RestaurantRepository repository;

    @Test
    @WithUserDetails(USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getAllWithDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "with-dishes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getWithDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "with-dishes/" + PIZZERIA_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }
}