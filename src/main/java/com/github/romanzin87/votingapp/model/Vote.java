package com.github.romanzin87.votingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.romanzin87.votingapp.HasId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor()
@ToString(callSuper = true)
@Table(name = "vote", uniqueConstraints = @UniqueConstraint(columnNames = {"vote_date", "user_id"},
        name = "votes_unique_vote_date_user_idx"))
public class Vote extends BaseEntity implements HasId {

    public static final LocalTime VOTE_DEADLINE = LocalTime.of(11, 0);

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "vote_date", nullable = false, columnDefinition = "date default today()")
    @NotNull
    @ToString.Exclude
    private LocalDate voteDate = LocalDate.now();

    public Vote(Integer id, User user, Restaurant restaurant) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
    }
}
