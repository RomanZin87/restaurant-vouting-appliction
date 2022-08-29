package ru.javaops.rzinnatov.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id"}, name = "vote_unique_user_idx")})
public class Vote extends BaseEntity {

    @Column(name = "restaurant_id", nullable = false)
    Integer restaurantId;

    @Column(name = "user_id", nullable = false)
    @Schema(hidden = true)
    Integer userId;

    @Column(name = "vote_time", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    @Schema(hidden = true)
    LocalTime voteTime = LocalTime.now();
}
