package com.github.romanzin87.votingapp.web.vote;

import com.github.romanzin87.votingapp.model.Vote;
import com.github.romanzin87.votingapp.web.MatcherFactory;

import static com.github.romanzin87.votingapp.web.restaurant.RestaurantTestData.PIZZERIA;
import static com.github.romanzin87.votingapp.web.user.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);

    public static Vote getNewVoteForUser() {
        return new Vote(null, user, PIZZERIA);
    }
}
