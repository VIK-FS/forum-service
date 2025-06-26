package ait.cohort5860.post.service;

import ait.cohort5860.post.dao.PostRepository;
import ait.cohort5860.post.dao.TagRepository;
import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import ait.cohort5860.post.dto.exception.PostNotFoundException;
import ait.cohort5860.post.model.Post;
import ait.cohort5860.post.model.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public PostDto addNewPost(String author, NewPostDto newPostDto) {
        Post post = new Post(newPostDto.getTitle(), newPostDto.getContent(), author);

        // Handle tags
        Set<String> tags = newPostDto.getTags();
        if (tags != null) {
            for (String tagName : tags) {
                Tag tag = tagRepository.findById(tagName).orElseGet(() -> tagRepository.save(new Tag(tagName)));
                post.addTags(tag);
            }
        }
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto findPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);

        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto updatePost(Long id, NewPostDto newPostDto) {
        return null;
    }

    @Override
    public PostDto addComment(Long id, String commenter, NewCommentDto newCommentDto) {
        return null;
    }

    @Override
    public PostDto deletePost(Long id) {
        return null;
    }

    @Override
    public void addLike(Long id) {

    }

    @Override
    public List<PostDto> findPostsByAuthor(String author) {
        return List.of();
    }

    @Override
    public List<PostDto> findPostsByTags(List<String> tags) {
        return List.of();
    }

    @Override
    public List<PostDto> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo) {
        return List.of();
    }
}
