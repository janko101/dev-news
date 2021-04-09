package devnews;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/create/")
    public Article create(@RequestBody Article article) {
        articleRepository.save(article);
        return article;
    }

}
