package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant"})
public class Dish extends NamedEntity {

    @ManyToOne(fetch= FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(name = "created", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    private LocalDate created = LocalDate.now();

    @Column(name = "price", nullable = false)
    private Integer price;
}
