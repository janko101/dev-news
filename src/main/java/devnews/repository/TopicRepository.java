package devnews.repository;

import devnews.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TopicRepository extends JpaRepository<Topic, Long> {
}
