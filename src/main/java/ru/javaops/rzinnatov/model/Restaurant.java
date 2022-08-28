package ru.javaops.rzinnatov.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"dishes"})
public class Restaurant extends NamedEntity {

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @Schema(hidden = true)
    @OrderBy("name ASC")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Dish> dishes;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}
