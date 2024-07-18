package com.vicarius.users_quota.infrastructure.adapter.rest;

import com.vicarius.users_quota.domain.model.User;
import com.vicarius.users_quota.domain.model.UserQuota;
import com.vicarius.users_quota.domain.service.UserService;
import com.vicarius.users_quota.infrastructure.adapter.rest.request.UserRequest;
import com.vicarius.users_quota.infrastructure.adapter.rest.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        User user = userRequest.toDomain(userRequest);
        User createdUser = userService.createUser(user);
        return UserResponse.fromDomain(createdUser);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        return UserResponse.fromDomain(user);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable String userId, @RequestBody UserRequest userRequest) {
        User updatedUser = userRequest.toDomain(userRequest);
        User user = userService.updateUser(userId, updatedUser);
        return UserResponse.fromDomain(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/consume-quota")
    public ResponseEntity<Void> consumeQuota(@PathVariable String userId) {
        userService.consumeQuota(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/quotas")
    public List<UserQuota> getUsersQuota() {
        return userService.getUsersQuota();
    }
}
