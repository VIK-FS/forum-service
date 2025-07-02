package ait.cohort5860.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewCommentDto {
    @NotBlank(message = "Comment message is required")
    @Size(min = 1, max = 1000, message = "Comment must be between 1 and 1000 characters")
    private String message;
}
