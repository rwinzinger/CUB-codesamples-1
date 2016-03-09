package senacor.cub.samples.technical.ping;

import java.time.Instant;

/**
 * Created by rwinzing on 08.03.16.
 */
public class Pong {
    private String message;
    private Instant timestamp;

    public Pong() {
        message = "Hello, I'm still alive";
        timestamp = Instant.now();
    }

    public String getMessage() {
        return message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}
