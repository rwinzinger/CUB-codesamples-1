package senacor.cub.samples.service.customer.command;

import org.springframework.data.annotation.Id;

/**
 * Created by rwinzing on 08.03.16.
 */
public class Customer {
    @Id
    private String id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;  // insecure !!!

    public Customer() {
    }

    public Customer(String id, String username, String firstname, String lastname, String password) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
