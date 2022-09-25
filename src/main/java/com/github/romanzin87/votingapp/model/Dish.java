package com.github.romanzin87.votingapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.nio.file.LinkOption;
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
    private LocalDate inMenuDate;

    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 100, max = 2500)
    private double price;

    public Dish(Integer id, String name, double price, LocalDate inMenuDate) {
        super(id, name);
        this.inMenuDate = inMenuDate;
        this.price = price;
    }
}
