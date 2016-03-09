package senacor.cub.samples.service.login.command;

import org.springframework.data.mongodb.repository.MongoRepository;
import senacor.cub.samples.service.login.command.login.Customer;

/**
 * Created by rwinzing on 08.03.16.
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {
    public Customer findByUsername(String username);
}
