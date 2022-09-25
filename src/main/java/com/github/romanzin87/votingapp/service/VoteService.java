package com.github.romanzin87.votingapp.service;

import com.github.romanzin87.votingapp.error.LateVoteException;
import com.github.romanzin87.votingapp.model.Vote;
import com.github.romanzin87.votingapp.repository.RestaurantRepository;
import com.github.romanzin87.votingapp.repository.UserRepository;
import com.github.romanzin87.votingapp.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.romanzin87.votingapp.util.validation.ValidationUtil.checkExisted;
import static com.github.romanzin87.votingapp.util.validation.ValidationUtil.checkNew;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Vote create(int userId, int restaurantId, LocalDateTime voteTime) {
        checkNew(voteRepository.findByUserId(userId));
        return voteRepository.save(new Vote(userRepository.get(userId), restaurantRepository.get(restaurantId)));
    }

    @Transactional
    public void update(int id, int userId, int restaurantId, LocalDateTime voteTime) {
        checkExisted(voteRepository.get(id), id);
        checkToLateVote(voteTime);
        voteRepository.save(new Vote(id, userRepository.get(userId), restaurantRepository.get(restaurantId)));
    }


    private static void checkToLateVote(LocalDateTime voteTime) {
        if (voteTime.toLocalTime().isAfter(Vote.VOTE_DEADLINE)) {
            throw new LateVoteException("All votes are accepted until 11:00. It's too late to vote");
        }
    }

    public void delete(int userId) {
        voteRepository.deleteByUserId(userId);
    }

    public List<Object[]> checkStatistic() {
        return voteRepository.checkStatistic();
    }
}
