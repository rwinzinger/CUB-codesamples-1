package senacor.cub.samples.service.login.command;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by rwinzing on 08.03.16.
 */
public interface CustomerAccountRepository extends MongoRepository<CustomerAccount, String> {
    public CustomerAccount findByUsername(String username);
}
