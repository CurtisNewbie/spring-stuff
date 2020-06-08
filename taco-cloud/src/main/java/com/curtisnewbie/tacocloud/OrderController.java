package com.curtisnewbie.tacocloud;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderRepository orderRepo;

    public OrderController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
        // model.addAttribute("order", new Order()); // nolonger needed, because of @ModelAttribute
        // and @SessionAttributes("order")
        return "orderForm"; // go to orderForm.html template
    }

    // @Valid, means using the declared annotations to validate a bean, and an Errors object is
    // injected for validation result
    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus session) {
        if (errors.hasErrors()) {
            return "orderForm"; // if bean validation fails, redirect to orderForm.html
        }
        // persist the order
        orderRepo.save(order);
        // this is the end of the session, this also means that all sessionAttributes are dumped.
        session.setComplete();
        // log.info("Order submitted: " + order.toString());
        return "redirect:/";
    }
}
