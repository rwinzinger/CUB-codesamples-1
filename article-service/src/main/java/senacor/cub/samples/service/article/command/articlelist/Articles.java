package senacor.cub.samples.service.article.command.articlelist;

import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rwinzing on 08.03.16.
 */
public class Articles extends ResourceSupport {
    private long total;
    private int page;
    private int pagesize;
    private List<ArticleDigest> articles = new ArrayList<ArticleDigest>();

    public Articles(long total, int page, int pagesize) {
        this.total = total;
        this.page = page;
        this.pagesize = pagesize;
    }

    public long getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getPagesize() {
        return pagesize;
    }

    public List<ArticleDigest> getArticles() {
        return articles;
    }
}
