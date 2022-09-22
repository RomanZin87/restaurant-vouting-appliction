package com.github.romanzin87.votingapp.web.vote;

import com.github.romanzin87.votingapp.error.LateVoteException;
import com.github.romanzin87.votingapp.util.validation.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.romanzin87.votingapp.model.Vote;
import com.github.romanzin87.votingapp.repository.VoteRepository;
import com.github.romanzin87.votingapp.web.AuthUser;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    public static final String REST_URL = "/api/vote";
    public static final LocalTime VOTE_DEADLINE = LocalTime.of(11, 0);
    private final VoteRepository repository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vote> vote(@AuthenticationPrincipal AuthUser authUser, @RequestBody @Valid Vote vote) {
        int userId = authUser.id();
        vote.setUserId(userId);
        log.info("user {} vote for restaurant {}", userId, vote.getRestaurantId());
        ValidationUtil.checkNew(vote);
        checkToLateVote(vote);
        Vote created = repository.save(vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reVote(@AuthenticationPrincipal AuthUser authUser, @RequestBody @Valid Vote vote) {
        int userId = authUser.id();
        log.info("user {} revote for restaurant {}", userId, vote.getRestaurantId());
        checkToLateVote(vote);
        repository.delete(userId);
        vote.setUserId(userId);
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
