package com.example.BlogEngine.controller;

import com.example.BlogEngine.dto.UserDTO;
import com.example.BlogEngine.dto.UserResponseDTO;
import com.example.BlogEngine.entities.User;
import com.example.BlogEngine.factory.UserFactory;
import com.example.BlogEngine.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDTO> responseDTO = users.stream()
                .map(UserFactory::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(UserFactory.convertToResponseDTO(user));
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserDTO userDTO) {
        User createdUser = UserFactory.convertToEntity(userDTO);
        createdUser = userService.createUser(createdUser);
        UserResponseDTO user = UserFactory.convertToResponseDTO(createdUser);
        return ResponseEntity.ok(user);
    }

    // @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserResponseDTO updatedUserDTO = UserFactory.convertToResponseDTO(userService.updateUser(id, userDTO));
        return ResponseEntity.ok(updatedUserDTO);
    }

    // @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
            userService.deleteUser(id);
            return ResponseEntity.ok("Utente cancellato con successo!");
    }
}
