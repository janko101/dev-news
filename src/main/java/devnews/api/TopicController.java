package devnews.api;

import devnews.api.exception.ResourceNotFoundException;
import devnews.model.Article;
import devnews.model.Topic;
import devnews.repository.ArticleRepository;
import devnews.repository.TopicRepository;
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

    @GetMapping("/topics/{topicId}/articles")
    public ResponseEntity<List<Article>> listOfArticlesByTopicId(@PathVariable Long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        List <Article> articles = topic.getArticles();
        return ResponseEntity.ok(articles);
    }

    @PostMapping("/topics")
    public ResponseEntity<Topic> create(@RequestBody Topic topic) {
        boolean isTopicNew = true;
        List<Topic> topics = topicRepository.findAll();

        for (Topic t : topics) {
            if (t.getName().equals(topic.getName())) {
                isTopicNew = false;
                break;
            }
        }

        if (isTopicNew) {
            topicRepository.save(topic);
            return ResponseEntity.status(HttpStatus.CREATED).body(topic);
        } else
            return ResponseEntity.status(HttpStatus.CONFLICT).body(topic);
    }

    @PostMapping("/articles/{articleId}/topics")
    public ResponseEntity<Topic> createAssociation(@PathVariable Long articleId, @RequestBody Topic topic) {
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

    @PutMapping("/topics/{id}")
    public ResponseEntity<Topic> edit(@PathVariable Long id, @RequestBody Topic newTopic) {
        Topic topic = topicRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        newTopic.setArticles(topic.getArticles());
        newTopic.setId(id);//
        topicRepository.save(newTopic);
        return ResponseEntity.ok(newTopic);
    }

    @DeleteMapping("/topics/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        topicRepository.delete(topic);
    }

    @DeleteMapping("/articles/{articleId}/topics/{topicId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAssociation(@PathVariable Long articleId, @PathVariable Long topicId) {
        Article article = articleRepository.findById(articleId).orElseThrow(ResourceNotFoundException::new);
        Topic topic = topicRepository.findById(topicId).orElseThrow(ResourceNotFoundException::new);
        if (article.getTopics().contains(topic)) {
            article.getTopics().remove(topic);
            articleRepository.save(article);

        } else {
            throw new ResourceNotFoundException();
        }
    }


}
