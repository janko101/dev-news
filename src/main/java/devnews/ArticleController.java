package devnews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/index")
    public List<Article> index() {
        return articleRepository.findAll();
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<Article> show(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(article);
    }

    @PostMapping("/create")
    public ResponseEntity<Article> create(@RequestBody Article article) {
        articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }

}
