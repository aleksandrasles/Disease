package com.application.disease.model;

public class UserEditor {
    public User user;
    public UserState savedUserState;

    public UserEditor(User user){
        this.user = user;
    }

    public void hitSave() {
        savedUserState = user.save();
    }

    public void hitUndo() {
        user.restore(savedUserState);
    }

    public User getUser() {
        return user;
    }

    public User print(){
        return user.currentUser;
    }
}
