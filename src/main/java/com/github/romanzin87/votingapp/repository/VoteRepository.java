package com.github.romanzin87.votingapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.github.romanzin87.votingapp.model.Vote;

import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("DELETE FROM Vote v WHERE v.user.id = :userId")
    int delete(int userId);

    int countVotesByRestaurantId(Integer restaurantId);

    @Query("select v.restaurant, count(v.restaurant) from Vote v group by v.restaurant")
    List<Object[]> checkStatistic();

    Vote findByUserId(int userId);

    void deleteByUserId(int userId);
}