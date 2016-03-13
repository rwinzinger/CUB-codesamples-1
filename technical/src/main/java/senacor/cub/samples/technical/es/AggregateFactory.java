package senacor.cub.samples.technical.es;

import akka.actor.Status;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
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

import java.util.ArrayList;
import java.util.List;


/**
 * Created by rwinzing on 13.03.16.
 */
@Component
public class AggregateFactory {
    @Autowired
    private EventstoreConnection esCon;

    public Object get(Class<? extends Aggregate> clazz, String aggregateID) {
        Aggregate aggregate;
        try {
            aggregate = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<Event> events = new ArrayList<>();

        final EsConnection connection = esCon.getConnection();
        final Future<ReadStreamEventsCompleted> future = connection.readStreamEventsForward("my-stream", new EventNumber.Exact(0), 5, true, null);

        ReadStreamEventsCompleted result = null;
        try {
            result = Await.result(future, Duration.create(5, "seconds"));
        } catch (StreamNotFoundException snfe) {
            System.out.println("no events");
        } catch (Exception e) {
            throw new RuntimeException();
        }
        System.out.println("result = " + result);
        /*
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        eventstore.Event result = null;
        try {
            result = Await.result(future, timeout.duration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        // System.out.println("result = " + result.toString());
        /*
        List<IndexedEvent> evs = result.value().get().get().eventsJava();
        for (int i = 0; i < evs.size(); i++) {
            IndexedEvent indexedEvent = evs.get(i);
            System.out.println("indexedEvent = " + indexedEvent);
        }
        return aggregate.replayEvents(events);
        */
        return aggregate;
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
