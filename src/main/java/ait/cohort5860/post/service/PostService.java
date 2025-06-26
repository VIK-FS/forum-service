package ait.cohort5860.post.service;

import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import ait.cohort5860.post.dto.exception.PostNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface PostService {

    PostDto addNewPost(String author, NewPostDto newPostDto);

    PostDto findPostById(Long id);

    PostDto updatePost(Long id, NewPostDto newPostDto);

    PostDto addComment(Long id, String commenter, NewCommentDto newCommentDto);

    PostDto deletePost(Long id);

    void addLike(Long id);

    List<PostDto> findPostsByAuthor(String author);

    List<PostDto> findPostsByTags(List<String> tags);

    List<PostDto> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo);

}