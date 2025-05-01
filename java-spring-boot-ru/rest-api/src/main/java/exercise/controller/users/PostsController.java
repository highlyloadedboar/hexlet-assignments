package exercise.controller.users;

import exercise.Data;
import exercise.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// BEGIN
@RestController
@RequestMapping("/api/users")
public class PostsController {

    private List<Post> posts = Data.getPosts();

    @GetMapping("/{id}/posts")
    public List<Post> getAllPostsById(@PathVariable int id) {
        return posts.stream().filter(p -> p.getUserId() == id).toList();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{id}/posts")
    public Post createPost(@PathVariable int id, @RequestBody Post post) {
        Post newPost = new Post();
        newPost.setUserId(id);
        newPost.setBody(post.getBody());
        newPost.setTitle(post.getTitle());
        newPost.setSlug(post.getSlug());
        posts.add(newPost);

        return newPost;
    }
}
// END
