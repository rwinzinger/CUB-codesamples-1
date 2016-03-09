package senacor.cub.samples.service.customer.command.userdetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senacor.cub.samples.service.customer.command.CustomerRepository;

/**
 * Created by rwinzing on 08.03.16.
 */
@Component
public class CustomerDetailCommand {
    @Autowired
    private CustomerRepository repository;

    public Customer getCustomerByUsername(String username) {
        Customer customer = repository.findByUsername(username);

        if (customer == null) {
            throw new CustomerNotFoundException();
        }

        return customer;
    }
}
