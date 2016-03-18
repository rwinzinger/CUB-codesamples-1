package senacor.cub.samples.technical.es;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

/**
 * Created by rwinzing on 13.03.16.
 */
public abstract class EventHandler<E extends Event> {
    public void handleEvent(Integer eventNo, E event) {
        System.out.println(event.getClass().getSimpleName()+"#"+eventNo);
        handleEvent(event);
        if (this instanceof CatchUpEventHandler) {
            ((CatchUpEventHandler) this).saveLastProcessedEventNo(eventNo);
        }
    }

    public abstract void handleEvent(E event);

    public String getEventName() {
        return getEventClassViaTypeParameter().getSimpleName();
    }

    public Class getEventClass() {
        return getEventClassViaTypeParameter();
    }

    private Class getEventClassViaTypeParameter() {
        try {
            return Class.forName(((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public E mapJsonToEvent(String json) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            return (E) mapper.readValue(json, getEventClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
