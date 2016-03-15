package senacor.cub.samples.technical.es;

import akka.actor.Status;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventstore.EsException;
import eventstore.EventNumber;
import eventstore.ReadStreamEventsCompleted;
import eventstore.StreamNotFoundException;
import eventstore.j.EsConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import senacor.cub.samples.technical.es.eventstore.EventstoreConnection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * Created by rwinzing on 13.03.16.
 */
@Component
public class AggregateFactory {
    @Autowired
    private EventstoreConnection esConnection;

    public Object get(Class<? extends Aggregate> clazz, String aggregateId) {
        Aggregate aggregate;
        try {
            aggregate = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String aggregateName = aggregate.getAggregateName();
        System.out.println("rebuilding aggregate '"+aggregateName+"' with id '"+aggregateId+"' ...");

        final EsConnection connection = esConnection.getConnection();
        final Future<ReadStreamEventsCompleted> future = connection.readStreamEventsForward(aggregateName+"-"+aggregateId, new EventNumber.Exact(0), 1000, true, null);

        ReadStreamEventsCompleted result = null;
        try {
            result = Await.result(future, Duration.create(5, "seconds"));
        } catch (StreamNotFoundException snfe) {
            System.out.println("no events");
            return null;
        } catch (Exception e) {
            throw new RuntimeException();
        }

        System.out.println("#events found :" + result.eventsJava().size());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<Event> events = result.eventsJava().stream().map(ev -> {
            try {
                System.out.println("trying to instantiate class of type '"+ev.data().metadata().value().utf8String()+"'");
                return (Event) mapper.readValue(ev.data().data().value().utf8String(), Class.forName(ev.data().metadata().value().utf8String()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toCollection(ArrayList::new));

        System.out.println("#events (after mapping/collecting): " + events.size());

        return aggregate.replayEvents(events);
    }

    public static class ReadResult extends UntypedActor {
        final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

        public void onReceive(Object message) throws Exception {
            System.err.println("00000: "+message.getClass().getSimpleName());
            if (message instanceof ReadStreamEventsCompleted) {
                System.err.println("AAAAA");
                final ReadStreamEventsCompleted completed = (ReadStreamEventsCompleted) message;
                final scala.collection.immutable.List<eventstore.Event> events = completed.events();
                log.info("events: {}", events);
            } else if (message instanceof Status.Failure) {
                System.err.println("BBBBB");
                final Status.Failure failure = ((Status.Failure) message);
                final EsException exception = (EsException) failure.cause();
                log.error(exception, exception.toString());
            } else {
                System.err.println("CCCCC");
                unhandled(message);
            }
        }
    }
}
