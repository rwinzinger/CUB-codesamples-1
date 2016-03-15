package senacor.cub.samples.service.cart.command.createcart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by rwinzing on 14.03.16.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class CartExistsException extends RuntimeException {
}
