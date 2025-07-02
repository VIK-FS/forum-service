package ait.cohort5860.accounting.controller;

import ait.cohort5860.accounting.dto.RolesDto;
import ait.cohort5860.accounting.dto.UserDto;
import ait.cohort5860.accounting.dto.UserUpdateDto;
import ait.cohort5860.accounting.dto.UserRegisterDto;
import ait.cohort5860.accounting.dto.exception.UserExistsException;
import ait.cohort5860.accounting.service.UserAccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Validated
public class UserAccountController {
    private final UserAccountService userAccountService;

    @PostMapping("/register")
    public UserDto register(@RequestBody @Valid UserRegisterDto userRegisterDto) {
        System.out.println("Register endpoint called");
        return userAccountService.register(userRegisterDto);
    }

//    @PostMapping("/register")
//    public ResponseEntity<UserDto> register(@RequestBody @Valid UserRegisterDto userRegisterDto) {
//        System.out.println("Register endpoint called");
//        try {
//            UserDto user = userAccountService.register(userRegisterDto);
//            return ResponseEntity.status(HttpStatus.CREATED).body(user);
//        } catch (UserExistsException e) {
//            System.out.println("Caught UserExistsException in controller");
//            return ResponseEntity.status(HttpStatus.CONFLICT).build();
//        }
//    }

    @PostMapping("/login")
    public UserDto login(Principal principal) {
        return userAccountService.getUser(principal.getName());
    }


    @DeleteMapping("/user/{login}")
    public UserDto removeUser(
        @PathVariable
        @NotBlank(message = "Login cannot be blank")
        @Size(min = 3, max = 20, message = "Login must be between 3 and 20 characters")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Login can only contain letters, numbers and underscores")
        String login) {
        return userAccountService.removeUser(login);
    }

    @PatchMapping("/user/{login}")
    public UserDto updateUser(
            @PathVariable
            @NotBlank(message = "Login cannot be blank")
            @Size(min = 3, max = 20, message = "Login must be between 3 and 20 characters")
            @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Login can only contain letters, numbers and underscores")
            String login,
            @RequestBody @Valid UserUpdateDto userUpdateDto) {
        return userAccountService.updateUser(login, userUpdateDto);
    }

    @PatchMapping("/user/{login}/role/{role}")
    public RolesDto addRole(
            @PathVariable
            @NotBlank(message = "Login cannot be blank")
            @Size(min = 3, max = 20, message = "Login must be between 3 and 20 characters")
            @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Login can only contain letters, numbers and underscores")
            String login,
            @PathVariable
            @NotBlank(message = "Role cannot be blank")
            @Pattern(regexp = "^[A-Z_]+$", message = "Role must contain only uppercase letters and underscores")
            String role) {
        return userAccountService.changeRolesList(login, role, true);
    }

    @DeleteMapping("/user/{login}/role/{role}")
    public RolesDto removeRole(
            @PathVariable
            @NotBlank(message = "Login cannot be blank")
            @Size(min = 3, max = 20, message = "Login must be between 3 and 20 characters")
            @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Login can only contain letters, numbers and underscores")
            String login,
            @PathVariable
            @NotBlank(message = "Role cannot be blank")
            @Pattern(regexp = "^[A-Z_]+$", message = "Role must contain only uppercase letters and underscores")
            String role) {
        return userAccountService.changeRolesList(login, role, false);
    }

    @PatchMapping("/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(
            Principal principal,
            @RequestHeader("X-Password")
            @NotBlank(message = "Password cannot be blank")
            @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
            @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
                    message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
            String newPassword) {
        userAccountService.changePassword(principal.getName(), newPassword);
    }
    @GetMapping("/user/{login}")
    public UserDto getUser(
            @PathVariable
            @NotBlank(message = "Login cannot be blank")
            @Size(min = 3, max = 20, message = "Login must be between 3 and 20 characters")
            @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Login can only contain letters, numbers and underscores")
            String login) {
        return userAccountService.getUser(login);
    }
}