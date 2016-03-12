package senacor.cub.samples.service.login.command.login;

import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Created by rwinzing on 08.03.16.
 */
@Component
@Profile("remote")
public class LoginCommandRemote implements LoginCommand{

    public Token verify(Credentials credentials) {
        String urlPrefix = "http://customerservice:8080";
        String urlToCustomer = "/customersrv/api/v1/customer/"+credentials.getUsername();

        RestTemplate getCustomerTemplate = new RestTemplate();
        Customer customer = null;
        try {
            customer = getCustomerTemplate.getForObject(urlPrefix+urlToCustomer, Customer.class);
        } catch (HttpClientErrorException e) {
            throw new LoginFailedException();
        }

        Token token = new Token(new String(Base64Utils.encode(customer.getPassword().getBytes())));
        token.add(new Link(urlToCustomer, "customer"));

        return token;
    }
}
