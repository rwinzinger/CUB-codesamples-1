package senacor.cub.samples.technical.es;

/**
 * Created by rwinzing on 17.03.16.
 */
public interface CatchUpEventHandler {
    Integer getLastProcessedEventNo();
    void saveLastProcessedEventNo(Integer eventNo);
}
