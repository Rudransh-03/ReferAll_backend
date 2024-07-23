package com.referAll.backend.services;

import com.referAll.backend.entities.dtos.UserDto;
import com.referAll.backend.entities.models.User;
import com.referAll.backend.respositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public static final int ID_LENGTH = 15;

    @Override
    public UserDto getUserByUserId(String userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found with id: " + userId));
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            UserDto userDto = modelMapper.map(user, UserDto.class);
            userDtoList.add(userDto);
        }
        System.out.println(userDtoList);
        return userDtoList;
    }

    @Override
    public String addPointsToUser(int points, String userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found with id: " + userId));
        Long userPoints = user.getPoints();
        user.setPoints(userPoints+points);
        userRepository.save(user);
        return "Added "+ points +" points to the user!";
    }

    @Override
    public String removePointsFromUser(int points, String userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found with id: " + userId));
        Long userPoints = user.getPoints();

        if(points <= userPoints) user.setPoints(userPoints-points);
        else user.setPoints(0L);

        userRepository.save(user);

        return "Removed "+ points +" points from the user!";
    }

    @Override
    public String addUser(UserDto newUserDto) {
        String id = generateUniqueId();
        while (userRepository.existsById(id)) {
            id = generateUniqueId();
        }
        newUserDto.setUserId(id);
        User newUser = modelMapper.map(newUserDto, User.class);
        userRepository.save(newUser);
        return "User Created Successfully";
    }

    @Override
    public String updateUser(UserDto updatedUserDto, String userId) {
        Optional<User> user = userRepository.findById(userId);
        updatedUserDto.setUserId(userId);
        user.ifPresent(value -> updatedUserDto.setPassword(value.getPassword()));
        User updatedUser = modelMapper.map(updatedUserDto, User.class);
        userRepository.save(updatedUser);
        return "User Updated Successfully";
    }

    @Override
    public String deleteUser(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.equals(Optional.empty())) return "User with this userId does not exist!!!!";
        userRepository.deleteById(userId);
        return "User Deleted Successfully";
    }

    public String generateUniqueId() {
        return RandomStringUtils.randomAlphanumeric(ID_LENGTH);
    }
}
