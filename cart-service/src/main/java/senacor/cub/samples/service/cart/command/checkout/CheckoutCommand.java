package senacor.cub.samples.service.cart.command.checkout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senacor.cub.samples.service.cart.command.ShoppingCart;
import senacor.cub.samples.technical.es.AggregateFactory;
import senacor.cub.samples.technical.es.eventstore.EventstoreConnection;

/**
 * Created by rwinzing on 16.03.16.
 */
@Component
public class CheckoutCommand {
    @Autowired
    private AggregateFactory aggregateFactory;

    @Autowired
    private EventstoreConnection esConnection;


    public void checkout(String username, String cartNo) {
        ShoppingCart latestCart = (ShoppingCart) aggregateFactory.get(ShoppingCart.class, ShoppingCart.getCartId(username, cartNo));

        if ((latestCart == null) || (latestCart.getState() != ShoppingCart.State.OPEN)) {
            throw new NoOpenCartException();
        }

        esConnection.publishEvent(new CartCheckedOutEvent(username, cartNo));
    }
}
