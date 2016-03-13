package senacor.cub.samples.service.customer.command.readdetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senacor.cub.samples.service.customer.command.Customer;
import senacor.cub.samples.service.customer.command.CustomerNotFoundException;
import senacor.cub.samples.service.customer.command.CustomerRepository;
import senacor.cub.samples.technical.es.Command;

/**
 * Created by rwinzing on 08.03.16.
 */
@Component
public class ReadDetailCommand extends Command {
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
