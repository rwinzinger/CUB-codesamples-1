package senacor.cub.samples.service.login.es;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senacor.cub.samples.service.login.command.CustomerAccountRepository;
import senacor.cub.samples.service.login.command.CustomerAccount;
import senacor.cub.samples.service.login.es.customer.CustomerCreatedEvent;
import senacor.cub.samples.technical.es.CatchUpEventHandler;
import senacor.cub.samples.technical.es.EventHandler;

/**
 * Created by rwinzing on 13.03.16.
 */
@Component
public class CustomerCreatedEventHandler extends EventHandler<CustomerCreatedEvent> implements CatchUpEventHandler {
    @Autowired
    CustomerAccountRepository customerAccountRepository;

    @Autowired
    EventCounterRepository eventCounterRepository;

    @Override
    public void handleEvent(CustomerCreatedEvent event) {
        System.out.println("a customer was created: "+event);

        CustomerAccount ca = new CustomerAccount(null, event.getCustomer().getUsername(), event.getCustomer().getPassword());
        customerAccountRepository.save(ca);
    }

    public Integer getLastProcessedEventNo() {
        EventCounter eventCounter = eventCounterRepository.findByEventname(CustomerCreatedEvent.class.getSimpleName());

        if (eventCounter == null) {
            return 0;
        }
        return eventCounter.getLastProcessedEventNo();
    }

    public void saveLastProcessedEventNo(Integer eventNo) {
        eventCounterRepository.save(new EventCounter(CustomerCreatedEvent.class.getSimpleName(), eventNo));
    }
}
