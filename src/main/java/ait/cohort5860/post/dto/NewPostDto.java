package ait.cohort5860.post.dto;

import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewPostDto {
    private String title;
    private String content;
    private Set<String> tags;
}
