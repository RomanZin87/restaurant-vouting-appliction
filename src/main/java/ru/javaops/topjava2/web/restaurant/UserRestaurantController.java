package ru.javaops.topjava2.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.javaops.topjava2.model.Restaurant;

import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController extends AbstractRestaurantController {
    static final String REST_URL = "/api/restaurant";

    @Override
    public List<Restaurant> getAll() {
        return super.getAll();
    }
}