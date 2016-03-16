package senacor.cub.samples.service.cart.command;

import senacor.cub.samples.service.cart.command.additem.ItemAddedEvent;
import senacor.cub.samples.service.cart.command.checkout.CartCheckedOutEvent;
import senacor.cub.samples.service.cart.command.createcart.CartCreatedEvent;
import senacor.cub.samples.service.cart.command.discardcart.CartDiscardedEvent;
import senacor.cub.samples.technical.es.Aggregate;
import senacor.cub.samples.technical.es.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rwinzing on 14.03.16.
 */
public class ShoppingCart extends Aggregate {
    private String username;
    private String cartNo;
    private State state;

    private List<Item> items = new ArrayList<>();

    public ShoppingCart() {
    }

    public ShoppingCart(String username, String cartNo) {
        this.username = username;
        this.cartNo = cartNo;
    }

    public String getCartId() {
        return getCartId(username, cartNo);
    }

    public String getCartNo() {
        return cartNo;
    }

    public void setCartNo(String id) {
        this.cartNo = cartNo;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public enum State {
        OPEN (0),
        CHECKEDOUT (1),
        DISCARDED (2);

        private final int id;
        State(int id) {
            this.id = id;
        }
    }

    @Override
    public Aggregate apply(Event ev) {
        if (ev instanceof CartCreatedEvent) {
            cartNo = ((CartCreatedEvent)ev).getCartNo();
            username = ((CartCreatedEvent)ev).getUsername();
            state = State.OPEN;
        } else if (ev instanceof ItemAddedEvent) {
            Item item = new Item(((ItemAddedEvent)ev).getItemId(), ((ItemAddedEvent)ev).getArticleId());
            items.add(item);
        } else if (ev instanceof CartDiscardedEvent) {
            state = State.DISCARDED;
        } else if (ev instanceof CartCheckedOutEvent) {
            state = State.CHECKEDOUT;
        }

        return this;
    }

    public static String getCartId(String username, String cartNo) {
        return username+"-"+cartNo;
    }
}
