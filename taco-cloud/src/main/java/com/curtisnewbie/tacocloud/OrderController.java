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
        log.info("Navigating to orderForm.html");
        return "orderForm"; // go to orderForm.html template
    }

    // @Valid, means using the declared annotations to validate a bean, and an Errors object is
    // injected for validation result
    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus session) {
        log.info("Processing Order: {}", order.toString());
        if (errors.hasErrors()) {
            return "orderForm"; // if bean validation fails, redirect to orderForm.html
        }
        orderRepo.save(order);
        // this is the end of the session, this also means that all sessionAttributes are dumped.
        session.setComplete();
        return "redirect:/";
    }
}
