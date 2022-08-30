package ru.javaops.rzinnatov.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.rzinnatov.model.Vote;

import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("DELETE FROM Vote v WHERE v.userId = :userId")
    int delete(int userId);

    int countVotesByRestaurantId(Integer restaurantId);

    @Query("select v.restaurantId, count(v.restaurantId) from Vote v group by v.restaurantId")
    List<Object[]> searchCustom();

    Vote findByUserId(int userId);
}