package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// BEGIN
@RestController
@RequestMapping("/comments")
public class CommentsController {

    private final CommentRepository commentRepository;

    public CommentsController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    @GetMapping
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }


    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        Optional<Comment> byId = commentRepository.findById(id);

        if (byId.isEmpty()) {
            throw new ResourceNotFoundException("Comment not found!");
        }

        return byId.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Comment createNewComment(@RequestBody Comment newComment) {
        commentRepository.save(newComment);

        return newComment;
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment newComment) {
        Optional<Comment> byId = commentRepository.findById(id);

        if (byId.isEmpty()) {
            throw new ResourceNotFoundException("Comment not found!");
        }

        Comment comment = byId.get();
        comment.setBody(newComment.getBody());
        comment.setPostId(newComment.getPostId());

        commentRepository.save(comment);

        return comment;
    }


    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
    }
}
// END
