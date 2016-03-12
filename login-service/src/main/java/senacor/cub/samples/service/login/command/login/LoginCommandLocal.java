package senacor.cub.samples.service.login.command.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import senacor.cub.samples.service.login.command.CustomerRepository;

/**
 * Created by rwinzing on 08.03.16.
 */
@Component
@Profile("local")
public class LoginCommandLocal implements LoginCommand {
    @Autowired
    private CustomerRepository repository;

    public Token verify(Credentials credentials) {
        String urlToCustomer = "/customersrv/api/v1/customer/"+credentials.getUsername();

        Customer customer = repository.findByUsername(credentials.getUsername());

        if (customer == null) {
            throw new CustomerNotFoundException(); // we would not do that for login - insecure !!!
        }

        Token token = new Token(new String(Base64Utils.encode(customer.getPassword().getBytes())));
        token.add(new Link(urlToCustomer, "customer"));

        return token;
    }
}
