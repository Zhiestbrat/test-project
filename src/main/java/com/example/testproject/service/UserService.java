package com.example.testproject.service;

import com.example.testproject.dto.UserDto;
import com.example.testproject.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(UserDto userDto);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}
