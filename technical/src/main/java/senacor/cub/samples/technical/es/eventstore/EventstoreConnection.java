package senacor.cub.samples.technical.es.eventstore;

import akka.actor.ActorSystem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventstore.*;
import eventstore.j.EsConnection;
import eventstore.j.EsConnectionFactory;
import eventstore.j.EventDataBuilder;
import eventstore.j.SettingsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import senacor.cub.samples.technical.es.Event;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

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
                    String esServer = System.getenv().get("ES_SERVER");

                    System.err.println("creating ES connection");
                    Settings settings = new SettingsBuilder()
                            .address(new InetSocketAddress(esServer, 1113))
                            .defaultCredentials("admin", "changeit")
                            .build();
                    esConnection = EsConnectionFactory.create(actorSystem, settings);
                }
            }
        }

        return esConnection;
    }

    public Event readLatest(Class eventClass) {
        System.out.println("looking for latest event of type " + eventClass.getSimpleName());

        return readLatest(eventClass, event -> true);
    }

    public Event readLatest(Class eventClass, Predicate<? super Event> predicate) {
        System.out.println("filtering by " + predicate);

        final Future<ReadStreamEventsCompleted> future = getConnection().readStreamEventsBackward(eventClass.getSimpleName(), new EventNumber.Last$(), 1000, true, null);

        ReadStreamEventsCompleted result = null;
        try {
            result = Await.result(future, Duration.create(5, "seconds"));
        } catch (StreamNotFoundException snfe) {
            System.out.println("no stream -> no event");
            return null;
        } catch (Exception e) {
            throw new RuntimeException();
        }
        System.out.println("#events found :" + result.eventsJava().size());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Optional<Event> event = result.eventsJava().stream().map(ev -> {
            try {
                return (Event) mapper.readValue(ev.data().data().value().utf8String(), eventClass);
            } catch (IOException e) {
                throw new RuntimeException(e);
        }
        }).filter(predicate).findFirst();

        System.out.println("event found: "+event.isPresent());

        if (event.isPresent()) {
            return event.get();
        }
        return null;
    }

    public void publishEvent(Event event) {
        List<Event> events = new ArrayList<>();
        events.add(event);
        publishEvents(events);
    }

    public void publishEvents(List<Event> events) {
        ObjectMapper mapper = new ObjectMapper();
        List<EventData> evs = new ArrayList<EventData>();

        for (Event event : events) {
            final EventData ev;
            try {
                ev = new EventDataBuilder(event.getClass().getSimpleName())
                        .metadata(event.getClass().getName())
                        .eventId(UUID.randomUUID())
                        .jsonData(mapper.writeValueAsString(event))
                        .build();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            evs.add(ev);
        }

        getConnection().writeEvents(
                "CUB",
                ExpectedVersion.Any$.MODULE$,
                evs,
                null);
    }
}
