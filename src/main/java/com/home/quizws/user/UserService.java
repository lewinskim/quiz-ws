package com.home.quizws.user;

import com.home.quizws.security.MyPasswordEncoder;
import com.home.quizws.user.dao.UserRepository;
import com.home.quizws.user.exceptions.UserAlreadyExistsException;
import com.home.quizws.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService implements UserDetailsService {


    private final PasswordEncoder encoder;

    private final UserRepository repository;


    @Autowired
    public UserService(UserRepository repository, MyPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.encoder = passwordEncoder.getEncoder();

    }

    public User register(User user) {
        List<User> users = new ArrayList<>();
        repository.findAll().forEach(users::add);

        boolean emailExists = users.stream()
                .map(User::getEmail)
                .anyMatch(mail -> user.getEmail().equals(mail));

        if (emailExists) {
            throw new UserAlreadyExistsException(user.getEmail());
        } else {
            repository.save(user);
            return user;
        }
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        repository.findAll().forEach(users::add);
        log.info("users size is {}", users.size());
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byEmail = repository.findByEmail(username);
        log.info("Passed User email is {}", byEmail.getEmail());

        if (byEmail != null) {
            return byEmail;
        } else throw new UsernameNotFoundException("Username not found :" + username);
    }
}
