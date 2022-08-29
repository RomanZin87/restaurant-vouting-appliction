package ru.javaops.rzinnatov.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.javaops.rzinnatov.error.LateVoteException;
import ru.javaops.rzinnatov.model.Vote;
import ru.javaops.rzinnatov.repository.VoteRepository;
import ru.javaops.rzinnatov.web.AuthUser;

import java.time.LocalTime;
import java.util.List;

import static ru.javaops.rzinnatov.util.validation.ValidationUtil.checkNew;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    public static final String REST_URL = "/api/vote";
    public static final LocalTime VOTE_DEADLINE = LocalTime.of(11, 0);

    private final VoteRepository repository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void vote(@AuthenticationPrincipal AuthUser authUser, @RequestBody Vote vote) {
        int userId = authUser.id();
        vote.setUserId(userId);
        log.info("user {} vote for restaurant {}", userId, vote.getRestaurantId());
        checkNew(vote);
        checkToLateVote(vote);
        repository.save(vote);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unVote(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("user {} cancel the vote", userId);
        repository.delete(userId);
    }

    @GetMapping("/{restaurantId}")
    public Integer checkResults(@AuthenticationPrincipal AuthUser authUser, @PathVariable int restaurantId) {
        log.info("checking vote counts for restaurant {}", restaurantId);
        return repository.countVotesByRestaurantId(restaurantId);
    }

    @GetMapping("/statistic")
    public List<Object[]> checkResults(@AuthenticationPrincipal AuthUser authUser) {
        log.info("checking vote results");
        return repository.searchCustom();
    }

    private static void checkToLateVote(Vote vote) {
        if (vote.getVoteTime().isAfter(VOTE_DEADLINE)) {
            throw new LateVoteException("All votes are accepted until 11:00. It's too late to vote");
        }
    }
}
