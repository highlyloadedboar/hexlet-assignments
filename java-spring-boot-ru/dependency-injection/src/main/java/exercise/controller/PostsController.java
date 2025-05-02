package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Post;
import exercise.repository.CommentRepository;
import exercise.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostsController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        Optional<Post> byId = postRepository.findById(id);

        if (byId.isEmpty()) {
            throw new ResourceNotFoundException("Post with id " + id + " not found");
        }

        return byId.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Post createNewPost(@RequestBody Post post) {
        postRepository.save(post);

        return post;
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post post) {
        Optional<Post> byId = postRepository.findById(id);

        if (byId.isEmpty()) {
            throw new ResourceNotFoundException("Post not found!");
        }

        Post postForUpdate = byId.get();

        postForUpdate.setTitle(post.getTitle());
        postForUpdate.setBody(post.getBody());

        postRepository.save(postForUpdate);

        return postForUpdate;
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        commentRepository.deleteAllByPostId(id);
    }
}
// END
