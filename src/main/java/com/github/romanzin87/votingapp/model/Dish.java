package com.github.romanzin87.votingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Dish extends NamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "restaurant_id", nullable = false)
    @ToString.Exclude
    private Restaurant restaurant;

    @Column(name = "in_menu_date", nullable = false)
    @NotNull
    @Schema(type = "String", format = "yyyy-MM-dd", pattern = "([0-9]{4})-(?:[0-9]{2})-([0-9]{2})", required = true)
    private LocalDate inMenuDate;

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 100, max = 2500)
    private double price;

    public Dish(Integer id, String name, double price, LocalDate inMenuDate) {
        super(id, name);
        this.price = price;
        this.inMenuDate = inMenuDate;
    }
}
