package senacor.cub.samples.service.cart.command.createcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senacor.cub.samples.service.cart.command.ShoppingCart;
import senacor.cub.samples.technical.es.AggregateFactory;
import senacor.cub.samples.technical.es.Command;
import senacor.cub.samples.technical.es.eventstore.EventstoreConnection;

/**
 * Created by rwinzing on 14.03.16.
 */
@Component
public class CreateCartCommand extends Command {
    @Autowired
    private EventstoreConnection esConnection;

    @Autowired
    private AggregateFactory aggregateFactory;

    public ShoppingCart createCart(String username) {
        // only one open cart per customer
        // get latest cart
        CartCreatedEvent latestCce = (CartCreatedEvent) esConnection.readLatest(CartCreatedEvent.class, ev -> ((CartCreatedEvent) ev).getUsername().equalsIgnoreCase(username));
        ShoppingCart latestCart = null;
        if (latestCce != null) {
            latestCart = (ShoppingCart) aggregateFactory.get(ShoppingCart.class, latestCce.getAggregateId());
        }

        // initial value would be 0 - if there already was a cart in the past, let's increment by one
        String cartNo = "0";

        if (latestCart != null) {
            if (latestCart.getState() == ShoppingCart.State.OPEN) {
                throw new CartExistsException();
            }
            cartNo = String.valueOf(Integer.parseInt(latestCart.getCartNo())+1);
        }

        // create the new cart and an event
        esConnection.publishEvent(new CartCreatedEvent(username, cartNo));

        return new ShoppingCart(username, cartNo);
    }
}
