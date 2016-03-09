package senacor.cub.samples.service.article.command;

import org.springframework.data.mongodb.repository.MongoRepository;
import senacor.cub.samples.service.article.command.articledetail.Article;

/**
 * Created by rwinzing on 08.03.16.
 */
public interface ArticleRepository extends MongoRepository<Article, String> {
}
