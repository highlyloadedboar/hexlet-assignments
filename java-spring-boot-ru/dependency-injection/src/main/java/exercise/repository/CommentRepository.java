package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import exercise.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteAllByPostId(long postId);
}
