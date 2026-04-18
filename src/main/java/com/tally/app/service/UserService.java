package com.tally.app.service;

import com.tally.app.dto.AddressDTO;
import com.tally.app.dto.UserRequest;
import com.tally.app.dto.UserResponse;
import com.tally.app.entity.Address;
import com.tally.app.entity.User;
import com.tally.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

//    private List<User> userList = new ArrayList<>();
    private Long nextId = 1L;

//    public List<User> fetchAllUsers(){
//        return  userRepository.findAll();
//    }

    public List<UserResponse> fetchAllUsers(){
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

//    public void addUser(User user){
//        userRepository.save(user);
//    }

    public void addUser(UserRequest userRequest){
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }

//    public Optional<User> fetchUser(Long id) {
//
//        return userRepository.findById(id);
//    }

    public Optional<UserResponse> fetchUser(Long id) {

        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

//    public boolean updateUser(Long id, User updateUser){
//        return userRepository.findById(id)
//                .map(existingUser -> {
//                    existingUser.setFirstName(updateUser.getFirstName());
//                    existingUser.setLastName(updateUser.getLastName());
//                    userRepository.save(existingUser);
//                    return true;
//                }).orElse(false);
//    }

    public boolean updateUser(Long id, UserRequest updateUserRequest){
        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, updateUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(String.valueOf(user.getId()));
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if (user.getAddress() != null) {
            Address address = user.getAddress();

            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(address.getStreet());
            addressDTO.setCity(address.getCity());
            addressDTO.setState(address.getState());
            addressDTO.setCountry(address.getCountry());
            addressDTO.setZipcode(address.getZipcode());

            response.setAddress(addressDTO);
        }
        return response;
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());

            user.setAddress(address);
        }
    }
}
