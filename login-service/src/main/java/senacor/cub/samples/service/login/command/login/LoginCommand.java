package senacor.cub.samples.service.login.command.login;

/**
 * Created by rwinzing on 08.03.16.
 */
public interface LoginCommand {
    Token verify(Credentials credentials);
}
