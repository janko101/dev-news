package devnews;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    CommentRepository commentRepository;
    ArticleRepository articleRepository;

    public CommentController(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    @GetMapping("/articles/{articleId}/comments")
    public List<Comment> index(@PathVariable Long articleId) {
        articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        return commentRepository.findAll();
    }

    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<Comment> create(@PathVariable Long articleId, @RequestBody Comment comment) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        comment.setSelectedArticle(article);
        commentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }
}
