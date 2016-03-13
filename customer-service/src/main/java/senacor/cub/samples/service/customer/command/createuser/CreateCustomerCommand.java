package senacor.cub.samples.service.customer.command.createuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senacor.cub.samples.service.customer.command.Customer;
import senacor.cub.samples.service.customer.command.CustomerAlreadyExistsException;
import senacor.cub.samples.service.customer.command.CustomerRepository;
import senacor.cub.samples.technical.es.Command;
import senacor.cub.samples.technical.es.eventstore.EventstoreConnection;

/**
 * Created by rwinzing on 12.03.16.
 */
@Component
public class CreateCustomerCommand extends Command {
    @Autowired
    private CustomerRepository repository;

    @Autowired
    private EventstoreConnection esConnection;

    public Customer createCustomer(Customer customer) {
        if (repository.findByUsername(customer.getUsername()) != null) {
            throw new CustomerAlreadyExistsException();
        }

        Customer newCustomer = repository.save(customer);
        esConnection.publishEvent(new CustomerCreatedEvent(newCustomer));

        return newCustomer;
    }
}
