package ait.cohort5860.post.controller;

import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import ait.cohort5860.post.dto.exception.PostNotFoundException;
import ait.cohort5860.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class PostController {
    private final PostService postService;

    @PostMapping("/post/{author}")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto addNewPost(
            @PathVariable
            @NotBlank(message = "Author cannot be blank")
            @Size(min = 3, max = 20, message = "Author must be between 3 and 20 characters")
            @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Author can only contain letters, numbers and underscores")
            String author,
            @RequestBody @Valid NewPostDto newPostDto) {
        return postService.addNewPost(author, newPostDto);
    }

    @GetMapping("/post/{id}")
    public PostDto findPostById(
            @PathVariable
            @Positive(message = "Post ID must be positive")
            Long id) {
        try {
            return postService.findPostById(id);
        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/post/{id}")
    public PostDto updatePost(
            @PathVariable
            @Positive(message = "Post ID must be positive")
            Long id,
            @RequestBody @Valid NewPostDto newPostDto) {
        try {
            return postService.updatePost(id, newPostDto);
        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/post/{id}/comment/{commenter}")
    public PostDto addComment(
            @PathVariable
            @Positive(message = "Post ID must be positive")
            Long id,
            @PathVariable
            @NotBlank(message = "Commenter cannot be blank")
            @Size(min = 3, max = 20, message = "Commenter must be between 3 and 20 characters")
            @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Commenter can only contain letters, numbers and underscores")
            String commenter,
            @RequestBody @Valid NewCommentDto newCommentDto) {
        try {
            return postService.addComment(id, commenter, newCommentDto);
        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/post/{id}")
    public PostDto deletePost(
            @PathVariable
            @Positive(message = "Post ID must be positive")
            Long id) {
        try {
            return postService.deletePost(id);
        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/post/{id}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addLike(
            @PathVariable
            @Positive(message = "Post ID must be positive")
            Long id) {
        try {
            postService.addLike(id);
        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/posts/author/{author}")
    public Iterable<PostDto> findPostsByAuthor(
            @PathVariable
            @NotBlank(message = "Author cannot be blank")
            @Size(min = 3, max = 20, message = "Author must be between 3 and 20 characters")
            @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Author can only contain letters, numbers and underscores")
            String author) {
        return postService.findPostsByAuthor(author);
    }

    @GetMapping("/posts/tags")
    public Iterable<PostDto> findPostsByTags(
            @RequestParam("values")
            @NotEmpty(message = "At least one tag is required")
            @Size(max = 10, message = "Maximum 10 tags allowed")
            List<@NotBlank(message = "Tag cannot be blank")
            @Size(min = 1, max = 20, message = "Tag must be between 1 and 20 characters") String> tags) {
        return postService.findPostsByTags(tags);
    }

    @GetMapping("/posts/period")
    public Iterable<PostDto> findPostsByPeriod(
            @RequestParam
            @NotNull(message = "Date from is required")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateFrom,
            @RequestParam
            @NotNull(message = "Date to is required")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate dateTo) {

        if (dateFrom.isAfter(dateTo)) {
            throw new IllegalArgumentException("Date from cannot be after date to");
        }

        return postService.findPostsByPeriod(dateFrom, dateTo);
    }

}
