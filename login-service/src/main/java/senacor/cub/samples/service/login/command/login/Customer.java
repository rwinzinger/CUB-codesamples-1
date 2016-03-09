package senacor.cub.samples.service.login.command.login;

import org.springframework.data.annotation.Id;

/**
 * Created by rwinzing on 08.03.16.
 */
public class Customer {
    @Id
    private String id;
    private String username;
    private String password;  // insecure !!!

    public Customer() {
    }

    public Customer(String id, String username, String password) {
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
}
