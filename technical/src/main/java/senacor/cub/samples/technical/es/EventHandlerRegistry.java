package senacor.cub.samples.technical.es;

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
            for (int i = 0; i < handlers.size(); i++) {
                EventHandler eventHandler = handlers.get(i);
                handlerMap.put(eventHandler.getEventName(), eventHandler);
                subscribeToEvent(eventHandler.getEventName());
                System.out.println("would subscribe to " + eventHandler.getEventName());
            }
        }
    }

    private void subscribeToEvent(String eventname) {
        // TODO: use real logging
        EsConnection connection = esConnection.getConnection();
        connection.subscribeToStream(eventname, new SubscriptionObserver<eventstore.Event>() {
            @Override
            public void onLiveProcessingStart(Closeable closeable) {
                System.out.println("connected to "+closeable.toString());
            }

            @Override
            public void onEvent(eventstore.Event ev, Closeable closeable) {
                EventHandler eh = handlerMap.get(ev.data().eventType());
                eh.handleEvent(eh.mapJsonToEvent(ev.data().data().value().utf8String()));
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t.toString());
            }

            @Override
            public void onClose() {
                System.out.println("closing subscription");
            }
        }, false, null);
    }
}
