package com.github.romanzin87.votingapp.web.vote;

import com.github.romanzin87.votingapp.util.JsonUtil;
import com.github.romanzin87.votingapp.web.AbstractControllerTest;
import com.github.romanzin87.votingapp.web.restaurant.RestaurantTestData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.github.romanzin87.votingapp.model.Vote;
import com.github.romanzin87.votingapp.repository.VoteRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.romanzin87.votingapp.web.user.UserTestData.USER_MAIL;
import static com.github.romanzin87.votingapp.web.user.UserTestData.USER_MAIL2;

class VoteControllerTest extends AbstractControllerTest {
    public static final String URL = VoteController.REST_URL + "/";

    @Autowired
    VoteRepository repository;

//    @Test
//    @WithUserDetails(USER_MAIL)
//    void vote() throws Exception {
//        ResultActions action = perform(MockMvcRequestBuilders.post(URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(VoteTestData.TEST_VOTE)))
//                .andExpect(status().isCreated())
//                .andDo(print());
//        Vote created = VoteTestData.VOTE_MATCHER.readFromJson(action);
//        VoteTestData.TEST_VOTE.setId(created.id());
//        VoteTestData.VOTE_MATCHER.assertMatch(created, VoteTestData.TEST_VOTE);
//    }
//
//    @Test
//    @WithUserDetails(USER_MAIL)
//    void lateVote() throws Exception {
//        perform(MockMvcRequestBuilders.post(URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(VoteTestData.TEST_LATE_VOTE)))
//                .andExpect(status().isMethodNotAllowed())
//                .andDo(print());
//    }
//
//    @Test
//    @WithUserDetails(USER_MAIL2)
//    void reVote() throws Exception {
//        perform(MockMvcRequestBuilders.put(URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(JsonUtil.writeValue(VoteTestData.TEST_VOTE_UPDATED)))
//                .andExpect(status().isNoContent());
//        Vote updated = repository.findByUserId(VoteTestData.TEST_VOTE_UPDATED.getUserId());
//        VoteTestData.TEST_VOTE_UPDATED.setId(updated.id());
//        VoteTestData.VOTE_MATCHER.assertMatch(updated, VoteTestData.TEST_VOTE_UPDATED);
//    }

    @Test
    @WithUserDetails(USER_MAIL2)
    void unVote() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL))
                .andExpect(status().isNoContent());
        assertFalse(repository.existsById(VoteTestData.USER_MAIL2_VOTE_ID));
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void checkResults() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + RestaurantTestData.PIZZERIA_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
        assertEquals(0, repository.countVotesByRestaurantId(RestaurantTestData.PIZZERIA_ID));
    }
}