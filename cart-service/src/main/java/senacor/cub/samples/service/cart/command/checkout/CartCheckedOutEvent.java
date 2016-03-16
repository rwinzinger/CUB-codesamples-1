package senacor.cub.samples.service.cart.command.checkout;

import senacor.cub.samples.service.cart.command.ShoppingCart;
import senacor.cub.samples.technical.es.Event;

/**
 * Created by rwinzing on 15.03.16.
 */
public class CartCheckedOutEvent extends Event {
    private String username;
    private String cartNo;

    public CartCheckedOutEvent() {
    }

    public CartCheckedOutEvent(String username, String cartNo) {
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
