package com.application.disease.dao;

import com.application.disease.model.User;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User addUser(User user);

    User updateUser(User user);

    void deleteUser(String userId);

    User findUserById(String id);

    User findUserEmail(String email);

    List<User> findAllUsersByFilter(Query query);

    List<User> findAllUsers();

    Optional<User> findUserByEmail(String email);
}
