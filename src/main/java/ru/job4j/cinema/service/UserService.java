package ru.job4j.cinema.service;


import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserStore;

import java.util.Optional;

@Service
@ThreadSafe
public class UserService {
    private final UserStore store;

    public UserService(UserStore store) {
        this.store = store;
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return store.findUserByEmailAndPassword(email, password);
    }
}



