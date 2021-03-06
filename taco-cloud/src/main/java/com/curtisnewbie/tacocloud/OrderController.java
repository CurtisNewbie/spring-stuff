package com.curtisnewbie.tacocloud;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final OrderProperties orderProperties;

    public OrderController(OrderRepository orderRepo, OrderProperties orderProperties) {
        this.orderRepo = orderRepo;
        this.orderProperties = orderProperties;
    }

    @GetMapping("/current")
    public String orderForm(Model model) {
        log.info("Navigating to orderForm.html");
        return "orderForm"; // go to orderForm.html template
    }

    @GetMapping("/history") // only the first 10 order histories
    public String ordersHistory(@AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(0, orderProperties.getPageSize());
        model.addAttribute("orders", orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));
        return "orderList"; // orderList.html
    }

    // @Valid, means using the declared annotations to validate a bean, and an Errors object is
    // injected for validation result. @AuthenticationPrincipal injects the User object, which is
    // also a UserDetail object for security
    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus session,
            @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            log.error("Order invalid: {}", order.toString());
            return "orderForm"; // if bean validation fails, redirect to orderForm.html
        }
        log.info("Processing Order: {}\nOrder belongs to: {}", order.toString(), user.toString());
        order.setUser(user);
        orderRepo.save(order);
        // this is the end of the session, this also means that all sessionAttributes are dumped.
        session.setComplete();
        return "redirect:/";
    }
}
