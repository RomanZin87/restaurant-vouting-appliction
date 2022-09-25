package com.github.romanzin87.votingapp.web.vote;

import com.github.romanzin87.votingapp.error.LateVoteException;
import com.github.romanzin87.votingapp.service.VoteService;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    public static final String REST_URL = "/api/vote";
    private final VoteService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vote> vote(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        int userId = authUser.id();
        log.info("user {} vote for restaurant {}", userId, restaurantId);
        Vote created = service.create(restaurantId, userId, LocalDateTime.now());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reVote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id, @RequestParam int restaurantId) {
        int userId = authUser.id();
        log.info("user {} revote for restaurant {}", userId, restaurantId);
        service.update(id, userId, restaurantId, LocalDateTime.now());
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unVote(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("user {} cancel the vote", userId);
        service.delete(userId);
    }

    @GetMapping("/statistic")
    public List<Object[]> checkResults(@AuthenticationPrincipal AuthUser authUser) {
        log.info("checking vote results");
        return service.checkStatistic();
    }

}
