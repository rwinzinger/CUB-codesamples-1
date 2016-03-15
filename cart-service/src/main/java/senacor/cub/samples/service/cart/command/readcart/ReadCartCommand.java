package senacor.cub.samples.service.cart.command.readcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senacor.cub.samples.service.cart.command.ShoppingCart;
import senacor.cub.samples.technical.es.AggregateFactory;
import senacor.cub.samples.technical.es.Command;

/**
 * Created by rwinzing on 14.03.16.
 */
@Component
public class ReadCartCommand extends Command {
    @Autowired
    private AggregateFactory aggregateFactory;

    public ShoppingCart readShoppingCart(String username, String cartNo) {
        return (ShoppingCart) aggregateFactory.get(ShoppingCart.class, ShoppingCart.getCartId(username, cartNo));
    }
}
