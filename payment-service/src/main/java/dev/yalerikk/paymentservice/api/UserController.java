package dev.yalerikk.paymentservice.api;

import dev.yalerikk.paymentservice.api.dto.CreateUserRequest;
import dev.yalerikk.paymentservice.api.dto.UserDto;
import dev.yalerikk.paymentservice.domain.UserService;
import jakarta.validation.Valid;
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
    public UserDto create(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDto confirm(@PathVariable Long id) {
        return userService.getUserOrThrow(id);
    }
}
