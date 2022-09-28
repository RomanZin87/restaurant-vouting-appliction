package com.github.romanzin87.votingapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import com.github.romanzin87.votingapp.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    int countVotesByRestaurantId(int restaurantId);

    int countVotesByUserId(int userId);

    @Query("select v.restaurant, count(v.restaurant) from Vote v where v.voteDate=:date group by v.restaurant")
    List<Object[]> checkStatistic(LocalDate date);

    Optional<Vote> findByUserIdAndVoteDate(int userId, LocalDate date);

    Optional<List<Vote>> findAllByUserId(int userId);
}