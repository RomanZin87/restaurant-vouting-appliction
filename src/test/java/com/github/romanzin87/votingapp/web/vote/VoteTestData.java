package com.github.romanzin87.votingapp.web.vote;

import com.github.romanzin87.votingapp.model.Vote;
import com.github.romanzin87.votingapp.web.MatcherFactory;

import java.time.LocalTime;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "voteTime");
    public static final Integer RESTAURANT_ID = 1;
    public static final Integer USER_ID = 1;
    public static final Integer USER_MAIL2_VOTE_ID = 1;
    public static final Vote TEST_VOTE = new Vote(null, RESTAURANT_ID, USER_ID, LocalTime.of(10, 0));
    public static final Vote TEST_LATE_VOTE = new Vote(null, RESTAURANT_ID, USER_ID, LocalTime.of(15, 0));
    public static final Vote TEST_VOTE_UPDATED = new Vote(1, RESTAURANT_ID + 1, USER_ID + 2, LocalTime.of(10, 0));
}
