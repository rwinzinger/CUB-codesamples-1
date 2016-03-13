package senacor.cub.samples.technical.es;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rwinzing on 13.03.16.
 */
public abstract class Aggregate {
    protected List<Event> events = new ArrayList<>();

    protected abstract void apply(Event ev);

    protected String getAggregateName() {
        return this.getClass().getSimpleName();
    }

    Aggregate replayEvents(List<Event> events) {
        for (Event ev:events) {
            apply(ev);
        }

        return this;
    }
}
