package com.github.romanzin87.votingapp.web.vote;

import com.github.romanzin87.votingapp.model.Vote;
import com.github.romanzin87.votingapp.service.VoteService;
import com.github.romanzin87.votingapp.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    public static final String REST_URL = "/api";
    private final VoteService service;

    @Transactional
    @PostMapping(value = "/vote")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        int userId = authUser.id();
        log.info("user {} vote for restaurant {}", userId, restaurantId);
        Vote created = service.vote(userId, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/vote-statistics")
    public List<Object[]> checkStatistic(@AuthenticationPrincipal AuthUser authUser, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        log.info("checking vote results on date: {}", date);
        return service.checkStatistic(date);
    }

    @GetMapping("/vote-for-date")
    public Vote get(@AuthenticationPrincipal AuthUser authUser, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        int userId = authUser.id();
        log.info("user {} checks his vote on date: {}", userId, date);
        return service.getByUserOrElseThrowException(userId, date);
    }

    @GetMapping("/vote-history")
    public List<Vote> get(@AuthenticationPrincipal AuthUser authUser) {
        int userId = authUser.id();
        log.info("user {} checks his vote history", userId);
        return service.getAllByUserOrElseThrowException(userId);
    }

}
