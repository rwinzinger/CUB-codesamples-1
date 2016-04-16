package senacor.cub.samples.technical.es;

import eventstore.Position;
import eventstore.SubscriptionObserver;
import eventstore.j.EsConnection;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import senacor.cub.samples.technical.es.eventstore.EventstoreConnection;

import java.io.Closeable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rwinzing on 13.03.16.
 */
@Component
public class EventHandlerRegistry implements InitializingBean {
    private List<EventHandler> handlers;
    private HashMap<String, EventHandler> handlerMap = new HashMap<>();

    @Autowired
    private EventstoreConnection esConnection;

    @Autowired(required=false)
    public void setEventHandlers(List<EventHandler> handlers){
        this.handlers = handlers;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (handlers == null) {
            System.err.println("warning: no handlers found");
        } else {
            for (EventHandler eventHandler : handlers) {
                handlerMap.put(eventHandler.getEventName(), eventHandler);
                subscribeToEvent(eventHandler.getEventName(), eventHandler);
                System.out.println("would subscribe to " + eventHandler.getEventName());
            }
        }
    }

    private void subscribeToEvent(String eventname, EventHandler eventHandler) {
        // TODO: use real logging
        EsConnection connection = esConnection.getConnection();

        SubscriptionObserver observer = new SubscriptionObserver<eventstore.Event>() {
            @Override
            public void onLiveProcessingStart(Closeable closeable) {
                System.out.println("connected to "+closeable.toString());
            }

            @Override
            public void onEvent(eventstore.Event ev, Closeable closeable) {
                EventHandler eh = handlerMap.get(ev.data().eventType());
                if (eh == null) {
                    System.err.println("no handler for ev '"+ev.data().eventType()+"'");
                } else {
                    eh.handleEvent(ev.record().number().value(), eh.mapJsonToEvent(ev.data().data().value().utf8String()));
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t.toString());
            }

            @Override
            public void onClose() {
                System.out.println("closing subscription");
            }
        };

        if (eventHandler instanceof CatchUpEventHandler) {
            Integer lastProcessedEventNo = ((CatchUpEventHandler)eventHandler).getLastProcessedEventNo();
            System.out.println(eventHandler.getClass().getSimpleName()+" is a catchup handler - starting from "+lastProcessedEventNo);
            connection.subscribeToStreamFrom(eventname, observer, lastProcessedEventNo, true, null);
        } else {
            connection.subscribeToStream(eventname, observer, true, null);
        }
    }
}
