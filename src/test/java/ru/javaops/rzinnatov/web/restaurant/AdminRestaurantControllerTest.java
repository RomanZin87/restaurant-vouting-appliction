package ru.javaops.rzinnatov.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.rzinnatov.model.Restaurant;
import ru.javaops.rzinnatov.repository.RestaurantRepository;
import ru.javaops.rzinnatov.util.JsonUtil;
import ru.javaops.rzinnatov.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.rzinnatov.web.restaurant.RestaurantTestData.*;
import static ru.javaops.rzinnatov.web.user.UserTestData.ADMIN_MAIL;
import static ru.javaops.rzinnatov.web.user.UserTestData.USER_MAIL;

class AdminRestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL = AdminRestaurantController.REST_URL + "/";

    @Autowired
    RestaurantRepository repository;

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + PIZZERIA_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(PIZZERIA));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + HALVA_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND_RESTAURANT))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void getWithDishes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "with-dishes/" + HALVA_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + PIZZERIA_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.existsById(PIZZERIA_ID));
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void deleteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + PIZZERIA_ID))
                .andDo(print())
                .andExpect(status().isForbidden());
        assertTrue(repository.existsById(PIZZERIA_ID));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated())
                .andDo(print());
        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(repository.getById(newId), newRestaurant);
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void update() throws Exception {
        Restaurant updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + HALVA_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent())
                .andDo(print());
        RESTAURANT_MATCHER.assertMatch(repository.getById(HALVA_ID), updated);
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void updateInvalid() throws Exception {
        Restaurant invalidRestaurant = new Restaurant(HALVA_ID, "");
        perform(MockMvcRequestBuilders.put(REST_URL + HALVA_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidRestaurant)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void createInvalid() throws Exception {
        Restaurant invalidRestaurant = new Restaurant(null, "");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalidRestaurant)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        Restaurant updated = getUpdated();
        updated.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_URL + HALVA_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

}