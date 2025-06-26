package ait.cohort5860.post.service;

import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import ait.cohort5860.post.dto.exception.PostNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface PostService {

    /**
     * Creates a new post for the specified author.
     *
     * @param author       the username
     * @param newPostDto the DTO containing the new post data
     * @return the created post
     */
    PostDto addNewPost(String author, NewPostDto newPostDto);

    /**
     * Finds a post by its identifier.
     *
     * @param id the post identifier
     * @return the found post
     * @throws PostNotFoundException if the post is not found
     */
    PostDto findPostById(Long id);

    /**
     * Updates a post by its identifier.
     *
     * @param id         the post identifier
     * @param newPostDto the DTO containing the updated post data
     * @return the updated post
     * @throws PostNotFoundException if the post is not found
     */
    PostDto updatePost(Long id, NewPostDto newPostDto);

    /**
     * Adds a comment to the post.
     *
     * @param id            the post identifier
     * @param commenter     the username of the commenter
     * @param newCommentDto the DTO containing the comment data
     * @return the updated post with the new comment
     * @throws PostNotFoundException if the post is not found
     */
    PostDto addComment(Long id, String commenter, NewCommentDto newCommentDto);

    /**
     * Deletes a post by its identifier.
     *
     * @param id the post identifier
     * @return the deleted post
     * @throws PostNotFoundException if the post is not found
     */
    PostDto deletePost(Long id);

    /**
     * Adds a like to the post.
     *
     * @param id the post identifier
     * @throws PostNotFoundException if the post is not found
     */
    void addLike(Long id);

    /**
     * Finds all posts by the specified author.
     *
     * @param author the username of the author
     * @return a list of posts by the author
     */
    List<PostDto> findPostsByAuthor(String author);

    /**
     * Finds posts by the specified tags.
     *
     * @param tags a list of tags
     * @return a list of posts containing the specified tags
     */
    List<PostDto> findPostsByTags(List<String> tags);

    /**
     * Finds posts created within the specified period.
     *
     * @param dateFrom the start date
     * @param dateTo   the end date
     * @return a list of posts created within the specified period
     */
    List<PostDto> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo);

}