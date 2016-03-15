package senacor.cub.samples.service.login.es.customer;

import senacor.cub.samples.technical.es.Event;

/**
 * Created by rwinzing on 13.03.16.
 */
public class CustomerDeletedEvent extends Event {
    @Override
    public String getAggregateName() {
        return null;
    }

    @Override
    public String getAggregateId() {
        return null;
    }
}
