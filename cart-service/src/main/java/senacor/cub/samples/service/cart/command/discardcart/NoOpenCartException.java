package senacor.cub.samples.service.cart.command.discardcart;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by rwinzing on 15.03.16.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoOpenCartException extends RuntimeException {
}
