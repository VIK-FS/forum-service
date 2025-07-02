package ait.cohort5860.accounting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRegisterDto {
    @NotBlank(message = "Login is required")
    @Size(min = 3, max = 20, message = "Login must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Login can only contain letters, numbers and underscores")
    private String login;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 50, message = "Password must be between 6 and 50 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit")
    private String password;

    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 30, message = "First name must be between 2 and 30 characters")
    @Pattern(regexp = "^[a-zA-ZА-Яа-я\\s\\-']+$", message = "First name can only contain letters, spaces, hyphens, and apostrophes")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 30, message = "Last name must be between 2 and 30 characters")
    @Pattern(regexp = "^[a-zA-ZА-Яа-я\\s\\-']+$", message = "Last name can only contain letters, spaces, hyphens, and apostrophes")
    private String lastName;
}