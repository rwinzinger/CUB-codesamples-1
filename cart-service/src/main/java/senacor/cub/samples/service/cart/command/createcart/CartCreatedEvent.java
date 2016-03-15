package senacor.cub.samples.service.cart.command.createcart;

import senacor.cub.samples.service.cart.command.ShoppingCart;
import senacor.cub.samples.technical.es.Event;

/**
 * Created by rwinzing on 14.03.16.
 */
public class CartCreatedEvent extends Event {
    private String username;
    private String cartNo;

    public CartCreatedEvent() {
    }

    public CartCreatedEvent(String username, String cartNo) {
        this.username = username;
        this.cartNo = cartNo;
    }

    public String getCartNo() {
        return cartNo;
    }

    public String getUsername() {
        return username;
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
