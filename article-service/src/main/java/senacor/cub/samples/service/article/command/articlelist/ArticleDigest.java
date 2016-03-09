package senacor.cub.samples.service.article.command.articlelist;

import org.springframework.hateoas.ResourceSupport;
import senacor.cub.samples.service.article.command.articledetail.Article;

/**
 * Created by rwinzing on 08.03.16.
 */
public class ArticleDigest extends ResourceSupport {
    private String articleid;
    private String brand;
    private String model;
    private Integer price;

    public ArticleDigest() {
    }

    public ArticleDigest(String articleid, String brand, String model, Integer price) {
        this.articleid = articleid;
        this.brand = brand;
        this.model = model;
        this.price = price;
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public static ArticleDigest fromArticle(Article article) {
        return new ArticleDigest(article.getArticleid(), article.getBrand(), article.getModel(), article.getPrice());
    }
}
