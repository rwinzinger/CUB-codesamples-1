package senacor.cub.samples.service.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import senacor.cub.samples.service.login.command.login.Credentials;
import senacor.cub.samples.service.login.command.CustomerAccount;
import senacor.cub.samples.service.login.command.login.LoginCommand;
import senacor.cub.samples.service.login.command.login.Token;
import senacor.cub.samples.technical.es.AggregateFactory;
import senacor.cub.samples.technical.ping.Pong;

/**
 * Created by rwinzing on 08.03.16.
 */
@RestController
@RequestMapping(value = "/loginsrv/api/v1")
public class LoginController {
    @Autowired
    private LoginCommand loginCmd;

    @Autowired
    private AggregateFactory af;

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public Pong ping() {
        return new Pong();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Token> login(@RequestBody Credentials credentials) {
        // return "Token for "+credentials.getUsername()+"/"+credentials.getPassword()+" is abc";
        return new ResponseEntity<Token>(loginCmd.verify(credentials), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public String test() {
        return af.get(CustomerAccount.class, "123").toString();
    }
}
