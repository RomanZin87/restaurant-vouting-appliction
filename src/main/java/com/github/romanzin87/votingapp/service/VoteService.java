package com.github.romanzin87.votingapp.service;

import com.github.romanzin87.votingapp.error.IllegalRequestDataException;
import com.github.romanzin87.votingapp.error.LateVoteException;
import com.github.romanzin87.votingapp.model.Vote;
import com.github.romanzin87.votingapp.repository.RestaurantRepository;
import com.github.romanzin87.votingapp.repository.UserRepository;
import com.github.romanzin87.votingapp.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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
    public void vote(int userId, int restaurantId) throws LateVoteException {
        Vote vote = getByUser(userId, LocalDate.now());
        if (vote == null) {
            save(userId, restaurantId);
        } else {
            checkToLateVote(LocalTime.now());
            update(vote, userId, restaurantId);
        }
    }

    public List<Object[]> checkStatistic(LocalDate date) {
        return voteRepository.checkStatistic(date);
    }

    private Vote getByUser(int userId, LocalDate date) {
        return voteRepository.findByUserIdAndVoteDate(userId, date).orElse(null);
    }

    public Vote getByUserOrElseThrowException(int userId, LocalDate date) {
        return voteRepository.findByUserIdAndVoteDate(userId, date)
                .orElseThrow(() -> new IllegalRequestDataException("There are no votes for user " + userId + " on date " + date));
    }

    public List<Vote> getAllByUserOrElseThrowException(int userId) {
        Optional<List<Vote>> allByUserId = voteRepository.findAllByUserId(userId);
        if (allByUserId.get().isEmpty()) {
            throw new IllegalRequestDataException("There are no votes for user");
        } else return allByUserId.get();
    }

    private static void checkToLateVote(LocalTime voteTime) {
        if (voteTime.isAfter(Vote.VOTE_DEADLINE)) {
            throw new LateVoteException("All votes are accepted until 11:00. It's too late to vote");
        }
    }

    private void save(int userId, int restaurantId) {
        Vote vote = new Vote();
        vote.setUser(userRepository.get(userId));
        vote.setRestaurant(restaurantRepository.get(restaurantId));
        voteRepository.save(vote);
    }

    private void update(Vote vote, int userId, int restaurantId) {
        vote.setUser(userRepository.get(userId));
        vote.setRestaurant(restaurantRepository.get(restaurantId));
        voteRepository.save(vote);
    }


}
