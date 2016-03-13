package senacor.cub.samples.service.customer.command.createuser;

import senacor.cub.samples.service.customer.command.Customer;
import senacor.cub.samples.technical.es.Event;

/**
 * Created by rwinzing on 13.03.16.
 */
public class CustomerCreatedEvent extends Event {
    private Customer customer;

    public CustomerCreatedEvent() {
    }

    public CustomerCreatedEvent(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
}
