package ait.cohort5860.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    @JsonProperty("user")
    private String username;
    private String message;
    private LocalDateTime dateCreated;
    private Integer likes;
}
