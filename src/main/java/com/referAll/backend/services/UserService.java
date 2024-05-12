package com.referAll.backend.services;

import com.referAll.backend.entities.dtos.UserDto;
import com.referAll.backend.entities.models.User;

import java.util.List;

public interface UserService {
    UserDto getUserByUserId(String userId) throws Exception;

    List<UserDto> getAllUsers();

    String addPointsToUser(int points, String userId) throws Exception;

    String removePointsFromUser(int points, String userId) throws Exception;

    String addUser(UserDto newUserDto);

    String updateUser(UserDto updatedUserDto, String userId);

    String deleteUser(String userId);
}
