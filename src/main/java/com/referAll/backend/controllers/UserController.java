package com.referAll.backend.controllers;

import com.referAll.backend.entities.dtos.UserDto;
import com.referAll.backend.entities.models.User;
import com.referAll.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDto> getUserByUserId(@PathVariable("userId") String userId) throws Exception {
        System.out.println(userId);

        UserDto fetchedUser = userService.getUserByUserId(userId);
        return ResponseEntity.ok(fetchedUser);
    }

    @GetMapping("/users/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> userDtoList = userService.getAllUsers();
        return ResponseEntity.ok(userDtoList);
    }

    @GetMapping("/users/add/{points}/to/{userId}")
    public ResponseEntity<String> addPointsToUser(@PathVariable("points") int points, @PathVariable("userId") String userId) throws Exception {
        System.out.println(points);

        String response = userService.addPointsToUser(points, userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/remove/{points}/from/{userId}")
    public ResponseEntity<String> removePointsFromUser(@PathVariable("points") int points, @PathVariable("userId") String userId) throws Exception {

        String response = userService.removePointsFromUser(points, userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserDto newUserDto){
        System.out.println(newUserDto);

        String response = userService.addUser(newUserDto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/updateUser/{userId}")
    public ResponseEntity<String> updateUser(@RequestBody UserDto updatedUserDto, @PathVariable("userId") String userId){
        String response = userService.updateUser(updatedUserDto, userId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") String userId){
        String response = userService.deleteUser(userId);
        return ResponseEntity.ok(response);
    }
}
