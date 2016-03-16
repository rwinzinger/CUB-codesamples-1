package senacor.cub.samples.service.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import senacor.cub.samples.service.cart.command.Item;
import senacor.cub.samples.service.cart.command.ShoppingCart;
import senacor.cub.samples.service.cart.command.additem.AddItemCommand;
import senacor.cub.samples.service.cart.command.checkout.CheckoutCommand;
import senacor.cub.samples.service.cart.command.createcart.CreateCartCommand;
import senacor.cub.samples.service.cart.command.discardcart.DiscardCartCommand;
import senacor.cub.samples.service.cart.command.readcart.ReadCartCommand;
import senacor.cub.samples.technical.ping.Pong;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by rwinzing on 14.03.16.
 */
@CrossOrigin(origins = "http://192.168.99.100:10080")
@RestController
@RequestMapping(value = "/cartsrv/api/v1")
public class CartController {
    @Autowired
    CreateCartCommand createCartCmd;

    @Autowired
    ReadCartCommand readCartCmd;

    @Autowired
    AddItemCommand addItemCmd;

    @Autowired
    DiscardCartCommand discardCartCmd;

    @Autowired
    CheckoutCommand checkoutCmd;

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public Pong ping() {
        return new Pong();
    }

    @RequestMapping(value = "/customers/{username}/carts", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShoppingCartResource> createCart(@PathVariable("username") String username) {
        ShoppingCart shoppingCart = createCartCmd.createCart(username);
        ShoppingCartResource shoppingCartResource = new ShoppingCartResource(shoppingCart);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(shoppingCartResource.getLink("self").getHref()));

        return new ResponseEntity<>(shoppingCartResource, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/customers/{username}/carts/{cartNo}/items", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShoppingCartResource> addItem(@PathVariable("username") String username, @PathVariable("cartNo") String cartNo, @RequestBody Item item) {
        ShoppingCart shoppingCart = addItemCmd.addItem(username, cartNo, item);

        return new ResponseEntity<>(new ShoppingCartResource(shoppingCart), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/customers/{username}/carts/{cartNo}/checkout", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> checkout(@PathVariable("username") String username, @PathVariable("cartNo") String cartNo) {
        checkoutCmd.checkout(username, cartNo);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/{username}/carts/{cartNo}/items/{itemId}", method = RequestMethod.DELETE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<ShoppingCartResource> deleteItem(@PathVariable("username") String username, @PathVariable("cartNo") String cartNo, @PathVariable("itemId") String itemId) {
        ShoppingCart shoppingCart = readCartCmd.readShoppingCart(username, cartNo);

        // TODO delete

        return new ResponseEntity<>(new ShoppingCartResource(shoppingCart), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/customers/{username}/carts/{cartNo}", method = RequestMethod.DELETE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> discardShoppingCart(@PathVariable("username") String username, @PathVariable("cartNo") String cartNo) {
        discardCartCmd.discardCart(username, cartNo);

        return new ResponseEntity<>(HttpStatus.GONE);
    }

    @RequestMapping(value = "/customers/{username}/carts/{cartNo}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ShoppingCartResource readShoppingCart(@PathVariable("username") String username, @PathVariable("cartNo") String cartNo) {
        Instant start = Instant.now();
        ShoppingCart shoppingCart = readCartCmd.readShoppingCart(username, cartNo);
        Instant end = Instant.now();
        System.out.println("duration: "+Duration.between(start, end));

        return new ShoppingCartResource(shoppingCart);
    }

    // ---- just for demo, please ignore

    @RequestMapping(value = "/populate/{username}", method = RequestMethod.GET)
    public ResponseEntity<?> populate(@PathVariable("username") String username) {
        return createCart(username);
    }

    @RequestMapping(value = "/buy/{username}/cart/{cartNo}/article/{articleId}", method = RequestMethod.GET)
    public ResponseEntity<?> buy(@PathVariable("username") String username, @PathVariable("cartNo") String cartNo, @PathVariable("articleId") String articleId) {
        return addItem(username, cartNo, new Item(UUID.randomUUID().toString(), articleId));
    }
}
