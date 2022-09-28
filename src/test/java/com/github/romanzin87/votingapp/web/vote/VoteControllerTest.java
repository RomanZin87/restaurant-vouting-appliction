package com.github.romanzin87.votingapp.web.vote;

import com.github.romanzin87.votingapp.model.Restaurant;
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

import java.time.LocalDate;
import java.time.Month;

import static com.github.romanzin87.votingapp.web.restaurant.RestaurantTestData.RESTAURANT_MATCHER;
import static com.github.romanzin87.votingapp.web.user.UserTestData.*;
import static com.github.romanzin87.votingapp.web.user.UserTestData.USER_ID;
import static com.github.romanzin87.votingapp.web.vote.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {
    public static final String URL = VoteController.REST_URL + "/";

    @Autowired
    VoteRepository repository;

    @Test
    @WithUserDetails(USER_MAIL)
    void vote() throws Exception {
        Vote newForUser = getNewVoteForUser();
        ResultActions actions = perform(MockMvcRequestBuilders.post(URL + "vote")
                .param("restaurantId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
        Vote created = VOTE_MATCHER.readFromJson(actions);
        newForUser.setId(created.id());
        VOTE_MATCHER.assertMatch(created, newForUser);
    }
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
    @WithUserDetails(USER_MAIL3)
    void checkVoteForDate() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + "vote-for-date")
                .param("date", "2022-09-24"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        assertEquals(1,
                repository.findByUserIdAndVoteDate(USER3_ID, LocalDate.of(2022, Month.SEPTEMBER, 24)).stream().count());
    }

    @Test
    @WithUserDetails(USER_MAIL3)
    void checkVoteHistory() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + "vote-history"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(print());
        assertEquals(3, repository.countVotesByUserId(USER3_ID));
    }
}