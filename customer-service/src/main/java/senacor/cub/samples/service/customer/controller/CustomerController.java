package senacor.cub.samples.service.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import senacor.cub.samples.service.customer.command.userdetail.Customer;
import senacor.cub.samples.service.customer.command.CustomerRepository;
import senacor.cub.samples.service.customer.command.userdetail.CustomerDetailCommand;
import senacor.cub.samples.technical.ping.Pong;

/**
 * Created by rwinzing on 08.03.16.
 */
@RestController
@RequestMapping(value = "/customersrv/api/v1")
public class CustomerController {
    @Autowired
    private CustomerDetailCommand customerDetailCommand;

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public Pong ping() {
        return new Pong();
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

        repository.save(new Customer("187", "rhwinzin", "Ralph", "Winzinger", "secret"));
        repository.save(new Customer("207", "mmouse", "Mickey", "Mouse", "secret"));

        return repository.findAll().size()+" customers created";
    }
}
