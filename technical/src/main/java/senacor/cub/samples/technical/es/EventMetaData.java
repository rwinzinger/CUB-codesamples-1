package senacor.cub.samples.technical.es;

import java.time.Instant;

/**
 * Created by rwinzing on 13.03.16.
 */
public class EventMetaData {
    private Instant timestamp;

    public EventMetaData() {
    }

    public EventMetaData(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
