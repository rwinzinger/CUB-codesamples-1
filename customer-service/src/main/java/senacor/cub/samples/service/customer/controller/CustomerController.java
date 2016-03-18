package senacor.cub.samples.service.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import senacor.cub.samples.service.customer.command.Customer;
import senacor.cub.samples.service.customer.command.CustomerRepository;
import senacor.cub.samples.service.customer.command.createuser.CreateCustomerCommand;
import senacor.cub.samples.service.customer.command.createuser.Registration;
import senacor.cub.samples.service.customer.command.readdetail.ReadDetailCommand;
import senacor.cub.samples.technical.ping.Pong;

/**
 * Created by rwinzing on 08.03.16.
 */
@CrossOrigin(origins = "http://192.168.99.100:10080")
@RestController
@RequestMapping(value = "/customersrv/api/v1")
public class CustomerController {
    @Autowired
    private ReadDetailCommand customerDetailCommand;

    @Autowired
    private CreateCustomerCommand customerCreationCommand;

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public Pong ping() {
        return new Pong();

    }

    @RequestMapping(value = "/customer", method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public Customer getCustomer(@RequestBody() Registration registration) {
        return customerCreationCommand.createCustomer(registration);
    }

    @RequestMapping(value = "/customer/{username}", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public Customer getCustomer(@PathVariable("username") String username) {
        return customerDetailCommand.getCustomerByUsername(username);
    }

    // ---- just for demo, please ignore

    @Autowired
    private CustomerRepository repository;

    @RequestMapping(value = "/populate", method = RequestMethod.GET)
    public String populate() {
        repository.deleteAll();

        customerCreationCommand.createCustomer(new Registration("rhwinzin", "Ralph", "Winzinger", "secret"));
        customerCreationCommand.createCustomer(new Registration("mmouse", "Mickey", "Mouse", "secret"));

        return repository.findAll().size()+" customers created";
    }

    @RequestMapping(value = "/pop_dupe", method = RequestMethod.GET)
    public String populate_dupe() {
        customerCreationCommand.createCustomer(new Registration("rhwinzin", "Ralph", "Winzinger", "secret"));

        return repository.findAll().size()+" customers created";
    }
}
