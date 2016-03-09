package senacor.cub.samples.service.article.command.articledetail;

import org.springframework.data.annotation.Id;
import org.springframework.hateoas.ResourceSupport;

/**
 * Created by rwinzing on 08.03.16.
 */
public class Article extends ResourceSupport {
    @Id
    private String articleid;
    private String type;
    private String brand;
    private String model;
    private Integer price;
    private Integer shipping;

    public Article() {
    }

    public Article(String articleid, String type, String brand, String model, Integer price, Integer shipping) {
        this.articleid = articleid;
        this.type = type;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.shipping = shipping;
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Integer getShipping() {
        return shipping;
    }

    public void setShipping(Integer shipping) {
        this.shipping = shipping;
    }
}
