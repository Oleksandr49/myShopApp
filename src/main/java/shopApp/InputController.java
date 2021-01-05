package shopApp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import shopApp.model.user.customer.Customer;



@Controller
public class InputController {

    @RequestMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("customer", new Customer());
        return "index";
    }

}
