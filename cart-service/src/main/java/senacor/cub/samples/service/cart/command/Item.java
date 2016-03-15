package senacor.cub.samples.service.cart.command;

/**
 * Created by rwinzing on 15.03.16.
 */
public class Item {
    private String itemId;
    private String articleId;

    public Item() {
    }

    public Item(String itemId, String articleId) {
        this.itemId = itemId;
        this.articleId = articleId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}
