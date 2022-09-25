package com.github.romanzin87.votingapp.web.vote;

import com.github.romanzin87.votingapp.model.Vote;
import com.github.romanzin87.votingapp.web.MatcherFactory;
import com.github.romanzin87.votingapp.web.restaurant.RestaurantTestData;
import com.github.romanzin87.votingapp.web.user.UserTestData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "voteTime");
    public static final Integer RESTAURANT_ID = 1;
    public static final Integer USER_ID = 1;
    public static final Integer USER_MAIL2_VOTE_ID = 1;
    //public static final Vote TEST_VOTE = new Vote(null, UserTestData.user, RestaurantTestData.PIZZERIA, LocalDateTime.of(LocalDate.now(),LocalTime.now()));
    //public static final Vote TEST_LATE_VOTE = new Vote(null, UserTestData.user, RestaurantTestData.PIZZERIA, LocalDateTime.of(15, 0));
    //public static final Vote TEST_VOTE_UPDATED = new Vote(1, RESTAURANT_ID + 1, USER_ID + 2, LocalDateTime.of(10, 0));
}
