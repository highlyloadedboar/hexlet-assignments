package exercise.controller;

import exercise.dto.CommentDTO;
import exercise.dto.PostDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.model.Comment;
import exercise.model.Post;
import exercise.repository.CommentRepository;
import exercise.repository.PostRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream().map(p ->
        {
            List<CommentDTO> byPostId = commentRepository.findByPostId(p.getId()).stream().map(c -> {
                CommentDTO dto = new CommentDTO();

                dto.setBody(c.getBody());
                dto.setId(c.getId());

                return dto;
            }).toList();
            PostDTO dto = new PostDTO();
            dto.setId(p.getId());
            dto.setTitle(p.getTitle());
            dto.setBody(p.getBody());
            dto.setComments(byPostId);
            return dto;
        }).toList();

    }

    @GetMapping("/{id}")
    public PostDTO getPostById(@PathVariable Long id) {
        Optional<Post> byId = postRepository.findById(id);

        if (byId.isEmpty()) {
            throw new ResourceNotFoundException("Post with id "+ id + " not found");
        }
        Post post = byId.get();
        List<CommentDTO> byPostId = commentRepository.findByPostId(post.getId()).stream().map(c -> {
            CommentDTO dto = new CommentDTO();

            dto.setBody(c.getBody());
            dto.setId(c.getId());

            return dto;
        }).toList();

        PostDTO dto = new PostDTO();
        dto.setBody(post.getBody());
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setComments(byPostId);
        return dto;
    }
}
// END
