package senacor.cub.samples.service.login.es.customer;

import senacor.cub.samples.technical.es.Event;

/**
 * Created by rwinzing on 13.03.16.
 */
public class CustomerCreatedEvent extends Event {
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public static class Customer {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "CustomerCreatedEvent{" +
                "customer=" + customer +
                '}';
    }

    @Override
    public String getAggregateName() {
        return null;
    }

    @Override
    public String getAggregateId() {
        return null;
    }
}
