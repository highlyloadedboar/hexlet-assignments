package exercise;

import exercise.model.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public List<Post> getAllPosts(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        if (page != null && limit != null && page > 0 && limit > 0) {
            return posts.stream().skip((long) (page - 1) * limit).limit(limit).toList();
        }
        return posts;
    }

    @GetMapping("/posts/{id}")
    public Post findPostById(@PathVariable String id) {
        return posts.stream().filter(p -> p.getId().equals(id)).findAny().orElse(new Post());
    }

    @PostMapping("/posts")
    public Post createNewPost(@RequestBody(required = false) Post newPost) {
        posts.add(newPost);
        return newPost;
    }

    @PutMapping("/posts/{id}")
    public Post updatePost(@PathVariable String id, @RequestBody(required = false) Post update) {
        Optional<Post> post = posts.stream().filter(p -> p.getId().equals(id)).findFirst();
        if (post.isPresent()) {
            Post post1 = post.get();
            post1.setBody(update.getBody());
            post1.setTitle(update.getTitle());
        }

        return update;
    }

    @DeleteMapping("/posts/{id}")
    public boolean deletePost(@PathVariable String id) {
        Post post = posts.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(new Post());
        return posts.remove(post);
    }
    // END
}
