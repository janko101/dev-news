package devnews;

import org.springframework.stereotype.Controller;

@Controller
public class CarController {

    CarRepository carRepository;
    ArticleRepository articleRepository;

    public CarController(CarRepository carRepository, ArticleRepository articleRepository) {
        this.carRepository = carRepository;
        this.articleRepository = articleRepository;
    }
}
