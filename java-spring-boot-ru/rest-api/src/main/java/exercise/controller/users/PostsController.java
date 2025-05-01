package exercise.controller.users;

import java.util.List;

import exercise.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;

// BEGIN
@RestController("/api/users")
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
