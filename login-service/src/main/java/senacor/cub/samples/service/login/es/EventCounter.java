package senacor.cub.samples.service.login.es;

import org.springframework.data.annotation.Id;

/**
 * Created by rwinzing on 17.03.16.
 */
public class EventCounter {
    @Id
    private String eventname;
    private Integer lastProcessedEventNo;

    public EventCounter() {
    }

    public EventCounter(String eventname, Integer lastProcessedEventNo) {
        this.eventname = eventname;
        this.lastProcessedEventNo = lastProcessedEventNo;
    }

    public String getEventname() {
        return eventname;
    }

    public Integer getLastProcessedEventNo() {
        return lastProcessedEventNo;
    }
}
