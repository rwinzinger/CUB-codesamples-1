package senacor.cub.samples.service.cart.command.discardcart;

import senacor.cub.samples.service.cart.command.ShoppingCart;
import senacor.cub.samples.technical.es.Event;

/**
 * Created by rwinzing on 15.03.16.
 */
public class CartDiscardedEvent extends Event {
    private String username;
    private String cartNo;

    public CartDiscardedEvent() {
    }

    public CartDiscardedEvent(String username, String cartNo) {
        this.username = username;
        this.cartNo = cartNo;
    }

    public String getUsername() {
        return username;
    }

    public String getCartNo() {
        return cartNo;
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
