package senacor.cub.samples.service.cart.command.additem;

import senacor.cub.samples.service.cart.command.ShoppingCart;
import senacor.cub.samples.technical.es.Event;

/**
 * Created by rwinzing on 15.03.16.
 */
public class ItemAddedEvent extends Event {
    private String username;
    private String cartNo;
    private String itemId;
    private String articleId;

    public ItemAddedEvent() {
    }

    public ItemAddedEvent(String username, String cartNo, String itemId, String articleId) {
        this.username = username;
        this.cartNo = cartNo;
        this.itemId = itemId;
        this.articleId = articleId;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCartNo() {
        return cartNo;
    }

    public void setCartNo(String cartId) {
        this.cartNo = cartNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getAggregateName() {
        return ShoppingCart.class.getSimpleName();
    }

    @Override
    public String getAggregateId() {
        return ShoppingCart.getCartId(username, cartNo);
    }
}
