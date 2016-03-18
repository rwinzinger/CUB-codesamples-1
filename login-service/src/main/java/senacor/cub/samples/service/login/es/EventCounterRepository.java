package senacor.cub.samples.service.login.es;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by rwinzing on 17.03.16.
 */
public interface EventCounterRepository extends MongoRepository<EventCounter, String> {
    EventCounter findByEventname(String eventname);
}
