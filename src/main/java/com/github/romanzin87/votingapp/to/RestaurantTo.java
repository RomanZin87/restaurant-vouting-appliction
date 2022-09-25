package com.github.romanzin87.votingapp.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTo extends NamedTo{

    public RestaurantTo(Integer id, String name) {
        super(id, name);
    }
}
