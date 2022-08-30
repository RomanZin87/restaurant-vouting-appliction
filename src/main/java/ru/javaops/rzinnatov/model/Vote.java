package ru.javaops.rzinnatov.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"voteTime"})
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id"}, name = "vote_unique_user_idx")})
public class Vote extends BaseEntity {

    @Column(name = "restaurant_id", nullable = false)
    @NotNull
    Integer restaurantId;

    @Column(name = "user_id", nullable = false)
    @Schema(hidden = true)
    Integer userId;

    @Column(name = "vote_time", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @Schema(hidden = true)
    LocalTime voteTime = LocalTime.now();

    public Vote(Integer id, Integer restaurantId, Integer userId, LocalTime voteTime) {
        super(id);
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.voteTime = voteTime;
    }
}
