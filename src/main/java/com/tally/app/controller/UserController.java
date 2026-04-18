package com.tally.app.controller;

import com.tally.app.dto.UserRequest;
import com.tally.app.dto.UserResponse;
import com.tally.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


//    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
//    @GetMapping
//    public ResponseEntity<List<User>> getAllUsers(){
//        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUser(@PathVariable Long id){
//        return userService.fetchUser(id)
//                .map(ResponseEntity::ok)
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
        return userService.fetchUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

//    @PostMapping
//    public ResponseEntity<String> createUser(@RequestBody User user){
//        userService.addUser(user);
//        return  ResponseEntity.ok("User added successfully");
//    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest){
        userService.addUser(userRequest);
        return  ResponseEntity.ok("User added successfully");
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updateUser){
//        boolean updated = userService.updateUser(id, updateUser);
//        if (updated) {
//            return ResponseEntity.ok("User updated successfully");
//        }
//        return  ResponseEntity.notFound().build();
//    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserRequest updateUserRequest){
        boolean updated = userService.updateUser(id, updateUserRequest);
        if (updated) {
            return ResponseEntity.ok("User updated successfully");
        }
        return  ResponseEntity.notFound().build();
    }
}
