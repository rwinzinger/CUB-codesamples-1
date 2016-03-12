package senacor.cub.samples.service.article.command.articlelist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import senacor.cub.samples.service.article.command.ArticleRepository;
import senacor.cub.samples.service.article.command.articledetail.Article;

/**
 * Created by rwinzing on 08.03.16.
 */
@Component
public class ArticleListCommand {
    @Autowired
    private ArticleRepository repository;

    public Articles getArticles(int pagenum, int pagesize) {
        Page<Article> page = repository.findAll(new PageRequest(pagenum, pagesize));

        Articles articles = new Articles(page.getTotalElements(), pagenum, page.getNumberOfElements());
        for (Article article : page) {
            ArticleDigest articleDigest = ArticleDigest.fromArticle(article);
            articleDigest.add(new Link("/articlesrv/api/v1/article/"+article.getArticleid(), "detail"));
            articles.getArticles().add(articleDigest);
        }

        if (page.hasPrevious()) {
            articles.add(new Link(linkToPage(page.previousPageable()), "prev"));
        }
        if (page.hasNext()) {
            articles.add(new Link(linkToPage(page.nextPageable()), "next"));
        }

        return articles;
    }

    private String linkToPage(Pageable pageable) {
        return "/articlesrv/api/v1/articles?pn="+pageable.getPageNumber()+"&ps="+pageable.getPageSize();
    }
}
