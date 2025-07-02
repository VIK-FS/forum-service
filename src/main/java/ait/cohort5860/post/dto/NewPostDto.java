package ait.cohort5860.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewPostDto {
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(min = 10, max = 5000, message = "Content must be between 10 and 5000 characters")
    private String content;

    @Size(max = 10, message = "Maximum 10 tags allowed")
    private Set<@NotBlank(message = "Tag cannot be blank")
    @Size(min = 1, max = 20, message = "Tag must be between 1 and 20 characters") String> tags;
}
