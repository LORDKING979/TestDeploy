package com.example.TestDeploy.service;


import com.example.TestDeploy.Repository.UserRepository;
import com.example.TestDeploy.model.User;

public class UserService implements UserRepository {
    public void add(User model) {
        User user= (User) model;

            userList.add(user);

    }

}
