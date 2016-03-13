package senacor.cub.samples.service.login.es;

import org.springframework.stereotype.Component;
import senacor.cub.samples.service.login.es.customer.CustomerDeletedEvent;
import senacor.cub.samples.technical.es.EventHandler;

/**
 * Created by rwinzing on 13.03.16.
 */
@Component
public class CustomerDeletedEventHandler extends EventHandler<CustomerDeletedEvent> {
    @Override
    public void handleEvent(CustomerDeletedEvent event) {
        System.out.println("a customer was deleted");
    }
}
