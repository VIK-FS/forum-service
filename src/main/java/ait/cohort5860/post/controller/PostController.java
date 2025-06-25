package ait.cohort5860.post.controller;

import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import ait.cohort5860.post.dto.exeption.PostNotFoundException;
import ait.cohort5860.post.service.PostService;
import lombok.RequiredArgsConstructor;
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

    /**
     * Creates a new post for the specified author.
     *
     * @param author      the username of the post author
     * @param newPostDto  the DTO containing the new post data
     * @return the created post
     */
    @PostMapping("/post/{author}")
    public PostDto addNewPost(@PathVariable String author, @RequestBody NewPostDto newPostDto) {
        return postService.addNewPost(author, newPostDto);
    }

    /**
     * Retrieves a post by its identifier.
     *
     * @param id the post identifier
     * @return the found post
     * @throws ResponseStatusException if the post is not found
     */
    @GetMapping("/post/{id}")
    public PostDto findPostById(@PathVariable Long id) {
        try {
            return postService.findPostById(id);
        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Updates a post by its identifier.
     *
     * @param id         the post identifier
     * @param newPostDto the DTO containing the updated post data
     * @return the updated post
     * @throws ResponseStatusException if the post is not found
     */
    @PatchMapping("/post/{id}")
    public PostDto updatePost(@PathVariable Long id, @RequestBody NewPostDto newPostDto) {
        try {
            return postService.updatePost(id, newPostDto);
        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Adds a comment to the specified post.
     *
     * @param id            the post identifier
     * @param commenter     the username of the commenter
     * @param newCommentDto the DTO containing the comment data
     * @return the updated post with the new comment
     * @throws ResponseStatusException if the post is not found
     */
    @PatchMapping("/post/{id}/comment/{commenter}")
    public PostDto addComment(@PathVariable Long id, @PathVariable String commenter, @RequestBody NewCommentDto newCommentDto) {
        try {
            return postService.addComment(id, commenter, newCommentDto);
        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Deletes a post by its identifier.
     *
     * @param id the post identifier
     * @return the deleted post
     * @throws ResponseStatusException if the post is not found
     */
    @DeleteMapping("/post/{id}")
    public PostDto deletePost(@PathVariable Long id) {
        try {
            return postService.deletePost(id);
        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Adds a like to the specified post.
     *
     * @param id the post identifier
     * @throws ResponseStatusException if the post is not found
     */
    @PatchMapping("/post/{id}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addLike(@PathVariable Long id) {
        try {
            postService.addLike(id);
        } catch (PostNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Retrieves all posts by the specified author.
     *
     * @param author the username of the author
     * @return an iterable collection of posts by the author
     */
    @GetMapping("/posts/author/{author}")
    public Iterable<PostDto> findPostsByAuthor(@PathVariable String author) {
        return postService.findPostsByAuthor(author);
    }

    /**
     * Retrieves posts by the specified tags.
     *
     * @param tags a comma-separated list of tags
     * @return an iterable collection of posts containing the specified tags
     */
    @GetMapping("/posts/tags")
    public Iterable<PostDto> findPostsByTags(@RequestParam("tags") List<String> tags) {
        return postService.findPostsByTags(tags);
    }

    /**
     * Retrieves posts created within the specified period.
     *
     * @param dateFrom the start date
     * @param dateTo   the end date
     * @return an iterable collection of posts created within the period
     */
    @GetMapping("/posts/period")
    public Iterable<PostDto> findPostsByPeriod(@RequestParam LocalDate dateFrom, @RequestParam LocalDate dateTo) {
        return postService.findPostsByPeriod(dateFrom, dateTo);
    }

}
