package senacor.cub.samples.service.login.command;

import org.springframework.data.annotation.Id;
import senacor.cub.samples.technical.es.Aggregate;
import senacor.cub.samples.technical.es.Event;

import java.time.Instant;

/**
 * Created by rwinzing on 08.03.16.
 */
public class CustomerAccount extends Aggregate {
    @Id
    private String id;
    private String username;
    private String password;  // insecure !!!
    private Instant lastlogin;
    private boolean locked;

    public CustomerAccount() {
    }

    @Override
    public Aggregate apply(Event ev) {
        System.out.println("ev = " + ev);
        return this;
    }

    public CustomerAccount(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Instant lastlogin) {
        this.lastlogin = lastlogin;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public String toString() {
        return "CustomerAccount{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", lastlogin=" + lastlogin +
                ", locked=" + locked +
                '}';
    }
}
