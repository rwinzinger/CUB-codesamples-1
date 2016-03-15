package senacor.cub.samples.service.cart.command.additem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senacor.cub.samples.service.cart.command.Item;
import senacor.cub.samples.service.cart.command.ShoppingCart;
import senacor.cub.samples.technical.es.AggregateFactory;
import senacor.cub.samples.technical.es.Command;
import senacor.cub.samples.technical.es.eventstore.EventstoreConnection;

/**
 * Created by rwinzing on 15.03.16.
 */
@Component
public class AddItemCommand extends Command {
    @Autowired
    private AggregateFactory aggregateFactory;

    @Autowired
    private EventstoreConnection esConnection;

    public ShoppingCart addItem(String username, String cartNo, Item item) {
        ShoppingCart latestCart = (ShoppingCart) aggregateFactory.get(ShoppingCart.class, ShoppingCart.getCartId(username, cartNo));

        if ((latestCart == null) || (latestCart.getState() != ShoppingCart.State.OPEN)) {
            throw new NoOpenCartException();
        }

        ItemAddedEvent itemAddedEvent = new ItemAddedEvent(username, cartNo, item.getItemId(), item.getArticleId());
        esConnection.publishEvent(itemAddedEvent);

        // now we need to return the new shopping cart
        // (1) we can completely rebuild it
        // (2) we can apply this new event directly

        return (ShoppingCart) latestCart.apply(itemAddedEvent);
    }
}
