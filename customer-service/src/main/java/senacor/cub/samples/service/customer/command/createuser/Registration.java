package senacor.cub.samples.service.customer.command.createuser;

/**
 * Created by rwinzing on 18.03.16.
 */
public class Registration {
    private String username;
    private String password;
    private String firstname;
    private String lastname;

    public Registration() {
    }

    public Registration(String username, String firstname, String lastname, String password) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
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
}
