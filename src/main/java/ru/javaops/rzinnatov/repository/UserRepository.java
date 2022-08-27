package ru.javaops.rzinnatov.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.javaops.rzinnatov.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}