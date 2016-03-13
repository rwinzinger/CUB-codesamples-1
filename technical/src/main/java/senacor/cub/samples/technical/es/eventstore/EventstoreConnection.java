package senacor.cub.samples.technical.es.eventstore;

import akka.actor.ActorSystem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventstore.EventData;
import eventstore.ExpectedVersion;
import eventstore.j.EsConnection;
import eventstore.j.EsConnectionFactory;
import eventstore.j.EventDataBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senacor.cub.samples.technical.es.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by rwinzing on 13.03.16.
 */
@Component
public class EventstoreConnection {
    @Autowired
    private ActorSystem actorSystem;

    private EsConnection esConnection;

    public EsConnection getConnection() {
        if (esConnection == null) {
            synchronized (this) {
                if (esConnection == null) {
                    esConnection = EsConnectionFactory.create(actorSystem);
                }
            }
        }

        return esConnection;
    }

    public void publishEvent(Event event) {
        List<Event> events = new ArrayList<>();
        events.add(event);
        publishEvents(events, event.getClass().getSimpleName());
    }

    public void publishEvents(List<Event> events, String stream) {
        ObjectMapper mapper = new ObjectMapper();
        List<EventData> evs = new ArrayList<EventData>();

        for (Event event : events) {
            final EventData ev;
            try {
                ev = new EventDataBuilder(event.getClass().getSimpleName())
                        .eventId(UUID.randomUUID())
                        .jsonData(mapper.writeValueAsString(event))
                        .build();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            evs.add(ev);
        }

        getConnection().writeEvents(
                stream,
                ExpectedVersion.Any$.MODULE$,
                evs,
                null);
    }
}
