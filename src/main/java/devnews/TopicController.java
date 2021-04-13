package devnews;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicController {

    TopicRepository topicRepository;
    ArticleRepository articleRepository;

    public TopicController(TopicRepository topicRepository, ArticleRepository articleRepository) {
        this.topicRepository = topicRepository;
        this.articleRepository = articleRepository;
    }

    @GetMapping("/topics")
    public List<Topic> index() {
        return topicRepository.findAll();
    }

    @GetMapping("/articles/{articleId}/topics")
    public List<Topic> listOfTopicsByArticleId(@PathVariable Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        return article.getTopics();
    }

    @PostMapping("/topics")
    public ResponseEntity<Topic> create(@RequestBody Topic topic) {
        topicRepository.save(topic);
        return ResponseEntity.status(HttpStatus.CREATED).body(topic);
    }

    @PostMapping("/articles/{articleId}/topics")
    public ResponseEntity<Topic> associateTopicToArticle(@PathVariable Long articleId, @RequestBody Topic topic) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        boolean isTopicNew = true;
        List<Topic> topics = article.getTopics();

        for (Topic t : topics) {
            if (t.getName().equals(topic.getName())) {
                isTopicNew = false;
                break;
            }
        }
        if (isTopicNew) {
            topics.add(topic);
        }
        article.setTopics(topics);
        articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(topic);
    }
}
