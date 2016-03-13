package senacor.cub.samples.service.login.es;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senacor.cub.samples.service.login.command.CustomerAccountRepository;
import senacor.cub.samples.service.login.command.CustomerAccount;
import senacor.cub.samples.service.login.es.customer.CustomerCreatedEvent;
import senacor.cub.samples.technical.es.EventHandler;

/**
 * Created by rwinzing on 13.03.16.
 */
@Component
public class CustomerCreatedEventHandler extends EventHandler<CustomerCreatedEvent> {
    @Autowired
    CustomerAccountRepository customerAccountRepository;

    @Override
    public void handleEvent(CustomerCreatedEvent event) {
        System.out.println("a customer was created: "+event);

        CustomerAccount ca = new CustomerAccount(null, event.getCustomer().getUsername(), event.getCustomer().getPassword());
        customerAccountRepository.save(ca);
    }
}
