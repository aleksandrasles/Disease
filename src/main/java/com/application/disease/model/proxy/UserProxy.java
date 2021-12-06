package com.application.disease.model.proxy;

public class UserProxy extends RoleGetter{

    private User user;

    @Override
    public String getRoleString() {
        if (user == null)
            user = new User();
        return user.getRoleString();
    }
}
