package senacor.cub.samples.service.login.command.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by rwinzing on 08.03.16.
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class LoginFailedException extends RuntimeException {
}
