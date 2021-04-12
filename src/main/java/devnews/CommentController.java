package devnews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CommentController {

    CommentRepository commentRepository;
    ArticleRepository articleRepository;

    @Autowired
    public CommentController(CommentRepository commentRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    @GetMapping("/articles/{articleId}/comments")
    public ResponseEntity<List<Comment>> index(@PathVariable Long articleId) {
        articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.ok(commentRepository.findByArticleId(articleId));
    }

    @GetMapping(value = "/comments", params = {"authorName"})
    public ResponseEntity<List<Comment>> indexByAuthor(@RequestParam String authorName) {
        return ResponseEntity.ok(commentRepository.findByAuthorName(authorName));
    }

    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<Comment> create(@PathVariable Long articleId, @RequestBody Comment comment) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        comment.setArticle(article);
        commentRepository.save(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @PutMapping("/comments/{id}")
    public ResponseEntity<Comment> edit(@PathVariable Long id, @Valid @RequestBody Comment editedComment) {
        commentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        editedComment.setId(id);
        Comment comment = commentRepository.save(editedComment);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("comments/{id}")
    public ResponseEntity<Comment> delete(@PathVariable Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        commentRepository.delete(comment);
        return ResponseEntity.ok(comment);
    }

}
