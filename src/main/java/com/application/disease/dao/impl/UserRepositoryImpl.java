package com.application.disease.dao.impl;

import com.application.disease.model.User;
import com.application.disease.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private MongoTemplate mt;

    @Override
    public User addUser(User user) { return mt.insert(user); }

    @Override
    public User updateUser(User user) { return mt.save(user); }

    @Override
    public void deleteUser(String userId) {
        mt.remove(findUserById(userId));
    }

    @Override
    public User findUserById(String id) {
        return mt.findById(id, User.class);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Query query = Query.query(Criteria.where("email").is(email));
        return Optional.of(mt.findOne(query, User.class));
    }

    @Override
    public List<User> findAllUsersByFilter(Query query) {
        return mt.find(query, User.class);
    }

    @Override
    public List<User> findAllUsers() {
        return mt.findAll(User.class);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        Query query = Query.query(Criteria.where("email").is(email));
        return Optional.ofNullable(mt.findOne(query,User.class));
    }
}
