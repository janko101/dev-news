package devnews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/create/{title}/{body}/{authorName}")
    public Article create(@PathVariable("title") String title, @PathVariable("body") String body, @PathVariable("authorName") String authorName) {
        Article article = new Article(title, body, authorName);
        articleRepository.save(article);
        return article;
    }

}
