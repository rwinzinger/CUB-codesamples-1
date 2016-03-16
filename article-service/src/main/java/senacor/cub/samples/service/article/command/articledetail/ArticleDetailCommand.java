package senacor.cub.samples.service.article.command.articledetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senacor.cub.samples.service.article.command.ArticleRepository;
import senacor.cub.samples.technical.es.Command;

/**
 * Created by rwinzing on 08.03.16.
 */
@Component
public class ArticleDetailCommand extends Command {
    @Autowired
    private ArticleRepository articleRepository;

    public Article getArticle(String articleId) {
        Article article = articleRepository.findOne(articleId);

        if (article == null) {
            throw new ArticleNotFoundException();
        }

        return article;
    }
}
