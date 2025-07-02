package ait.cohort5860.accounting.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateDto {
    @Size(min = 3, max = 30, message = "First name must be between 2 and 30 characters")
    @Pattern(regexp = "^[a-zA-ZА-Яа-я\\s\\-']*$", message = "First name can only contain letters, spaces, hyphens, and apostrophes")
    private String firstName;

    @Size(min = 3, max = 30, message = "Last name must be between 2 and 30 characters")
    @Pattern(regexp = "^[a-zA-ZА-Яа-я\\s\\-']*$", message = "Last name can only contain letters, spaces, hyphens, and apostrophes")
    private String lastName;
}
