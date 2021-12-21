package com.application.disease.model;

public class UserState {
    private User user;

    public UserState(User user){
        this.user = new User();
        this.user.setId(user.getId());
        this.user.setFirstName(user.getFirstName());
        this.user.setLastName(user.getLastName());
        this.user.setPassword(user.getPassword());
        this.user.setLocked(user.getLocked());
        this.user.setEmail(user.getEmail());
        this.user.setEnabled(user.getEnabled());
    }

    public User getUser(){
        return this.user;
    }
}
