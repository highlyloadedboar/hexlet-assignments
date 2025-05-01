package exercise;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import exercise.model.Post;
import lombok.Setter;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    @Setter
    private static  List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {
        return ResponseEntity.ok().header("X-Total-Count", String.valueOf(posts.size())).body(posts);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Post> findPostByIf(@PathVariable  String id) {
        Optional<Post> postOptional = posts.stream().filter(p -> p.getId().equals(id)).findFirst();

        if (postOptional.isPresent()) {
            return ResponseEntity.ok().body(postOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        posts.add(post);
        return ResponseEntity.status(201).body(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody Post post) {
        Optional<Post> postOptional = posts.stream().filter(p -> p.getId().equals(id)).findFirst();

        if (postOptional.isPresent()) {
            Post post1 = postOptional.get();
            post1.setBody(post.getBody());
            post1.setTitle(post.getTitle());

            return ResponseEntity.ok(post1);
        }

        return ResponseEntity.status(404).build();
    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
