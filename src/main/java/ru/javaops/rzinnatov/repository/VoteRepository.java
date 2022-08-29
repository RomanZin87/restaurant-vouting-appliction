package ru.javaops.rzinnatov.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.rzinnatov.model.Vote;

import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("DELETE FROM Vote v WHERE v.userId = :userId")
    int delete(int userId);
    int countVotesByRestaurantId(Integer restaurantId);
}
