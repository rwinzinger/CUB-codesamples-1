package senacor.cub.samples.service.customer.command;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by rwinzing on 08.03.16.
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {
    public Customer findByUsername(String username);
}
