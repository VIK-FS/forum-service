package ait.cohort5860.post.dao;

import ait.cohort5860.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public interface PostRepository extends JpaRepository<Post, Long> {
    //    List<Post> findByAuthor(String author);
//    List<Post> findByTagsNameIn(List<String> tags);
//    List<Post> findByDateCreatedBetween(LocalDateTime start, LocalDateTime end);
    Stream<Post> findByAuthorIgnoreCase(String author);

    Stream<Post> findByTagsNameInIgnoreCase(List<String> tags);

    Stream<Post> findByDateCreatedBetween(LocalDateTime start, LocalDateTime end);

}
