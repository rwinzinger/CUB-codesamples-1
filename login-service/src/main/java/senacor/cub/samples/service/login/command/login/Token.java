package senacor.cub.samples.service.login.command.login;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by rwinzing on 08.03.16.
 */
public class Token extends ResourceSupport {
    private String token;

    public Token() {
    }

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
