package ait.cohort5860.post.service;

import ait.cohort5860.post.dao.PostRepository;
import ait.cohort5860.post.dao.TagRepository;
import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import ait.cohort5860.post.dto.exception.PostNotFoundException;
import ait.cohort5860.post.model.Comment;
import ait.cohort5860.post.model.Post;
import ait.cohort5860.post.model.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
//    private final CommentRepository commentRepository;
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
    @Transactional
    public PostDto updatePost(Long id, NewPostDto newPostDto) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        post.setTitle(newPostDto.getTitle());
        post.setContent(newPostDto.getContent());

        Set<String> newTags = newPostDto.getTags();
        if (newTags != null) {
//            post.getTags().clear();
            for (String tagName : newTags) {
                Tag tag = tagRepository.findById(tagName).orElseGet(() -> tagRepository.save(new Tag(tagName)));
                post.addTags(tag);
            }
        }
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }


    @Override
    @Transactional
    public PostDto addComment(Long id, String commenter, NewCommentDto newCommentDto) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        if (newCommentDto.getMessage() == null || newCommentDto.getMessage().trim().isEmpty()) {
            throw new IllegalArgumentException("Comment message cannot be null or empty");
        }
        Comment comment = new Comment(commenter, newCommentDto.getMessage());
        comment.setDateCreated(LocalDateTime.now());
        comment.setPost(post);
//        comment = commentRepository.save(comment);
        post.addComment(comment);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);

//        // DEBUG: for mapping
//        System.out.println("=== DEBUG INFO ===");
//        System.out.println("Post comments count: " + post.getComments().size());
//        for (Comment c : post.getComments()) {
//            System.out.println("Comment: username=" + c.getUsername() +
//                    ", message=" + c.getMessage() +
//                    ", dateCreated=" + c.getDateCreated() +
//                    ", likes=" + c.getLikes());
//        }
//
//        PostDto result = modelMapper.map(post, PostDto.class);
//
//        // DEBUG:  result mapping
//        System.out.println("PostDto comments count: " + result.getComments().size());
//        for (CommentDto cd : result.getComments()) {
//            System.out.println("CommentDto: username=" + cd.getUsername() +
//                    ", message=" + cd.getMessage() +
//                    ", dateCreated=" + cd.getDateCreated() +
//                    ", likes=" + cd.getLikes());
//        }
//        System.out.println("=== END DEBUG ===");

//        return result;

    }

    @Override
    @Transactional
    public PostDto deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    @Transactional
    public void addLike(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        post.addLike();
        postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDto> findPostsByAuthor(String author) {
        return postRepository.findByAuthorIgnoreCase(author)
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDto> findPostsByTags(List<String> tags) {
        return postRepository.findByTagsNameInIgnoreCase(tags)
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostDto> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo) {
        LocalDateTime start = dateFrom.atStartOfDay();
        LocalDateTime end = dateTo.atTime(LocalTime.MAX);
        return postRepository.findByDateCreatedBetween(start, end)
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }
}
