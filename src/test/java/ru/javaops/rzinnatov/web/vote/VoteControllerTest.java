package ru.javaops.rzinnatov.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.rzinnatov.model.Vote;
import ru.javaops.rzinnatov.repository.VoteRepository;
import ru.javaops.rzinnatov.util.JsonUtil;
import ru.javaops.rzinnatov.web.AbstractControllerTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.rzinnatov.web.restaurant.RestaurantTestData.PIZZERIA_ID;
import static ru.javaops.rzinnatov.web.user.UserTestData.USER_MAIL;
import static ru.javaops.rzinnatov.web.user.UserTestData.USER_MAIL2;
import static ru.javaops.rzinnatov.web.vote.VoteTestData.*;

class VoteControllerTest extends AbstractControllerTest {
    public static final String URL = VoteController.REST_URL + "/";

    @Autowired
    VoteRepository repository;

    @Test
    @WithUserDetails(USER_MAIL)
    void vote() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(TEST_VOTE)))
                .andExpect(status().isCreated())
                .andDo(print());
        Vote created = VOTE_MATCHER.readFromJson(action);
        TEST_VOTE.setId(created.id());
        VOTE_MATCHER.assertMatch(created, TEST_VOTE);
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void lateVote() throws Exception {
        perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(TEST_LATE_VOTE)))
                .andExpect(status().isMethodNotAllowed())
                .andDo(print());
    }

    @Test
    @WithUserDetails(USER_MAIL2)
    void reVote() throws Exception {
        perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(TEST_VOTE_UPDATED)))
                .andExpect(status().isNoContent());
        Vote updated = repository.findByUserId(TEST_VOTE_UPDATED.getUserId());
        TEST_VOTE_UPDATED.setId(updated.id());
        VOTE_MATCHER.assertMatch(updated, TEST_VOTE_UPDATED);
    }

    @Test
    @WithUserDetails(USER_MAIL2)
    void unVote() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL))
                .andExpect(status().isNoContent());
        assertFalse(repository.existsById(USER_MAIL2_VOTE_ID));
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void checkResults() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + PIZZERIA_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
        assertEquals(0, repository.countVotesByRestaurantId(PIZZERIA_ID));
    }
}