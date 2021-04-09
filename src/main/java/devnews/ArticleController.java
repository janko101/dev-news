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

    @PutMapping("/edit/{id}")
    public ResponseEntity<Article> edit(@PathVariable Long id, @RequestBody Article updatedArticle) {
        articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        updatedArticle.setId(id);
        Article article = articleRepository.save(updatedArticle);
        return ResponseEntity.ok(article);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        articleRepository.delete(article);
        return ResponseEntity.ok(article);
    }

}
