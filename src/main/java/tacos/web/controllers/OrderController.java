package tacos.web.controllers;

import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.support.SessionStatus;
import tacos.data.OrderRepository;
import tacos.models.Taco;
import tacos.models.TacoOrder;
import tacos.models.User;
import tacos.web.OrderProps;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    private OrderRepository orderRepo;
    private OrderProps props;

    public OrderController(OrderRepository orderRepo, OrderProps props) {
        this.orderRepo = orderRepo;
        this.props = props;
    }
    @GetMapping("/current")
    public String orderForm(Model model, @ModelAttribute("taco") Taco taco) {

        log.info("orderForm invoked");
        TacoOrder tacoOrder = new TacoOrder();
        tacoOrder.addTaco(taco);
        model.addAttribute("tacoOrder", tacoOrder);
        return "orderForm";
    }

    @PostMapping
    public String processOrder
            (@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus,
             @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            return "orderForm";
        }

        order.setUser(user);

        orderRepo.save(order);
        sessionStatus.setComplete();

        log.info("Order submitted: " + order);
        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model) {

        Pageable pageable = PageRequest.of(0,  props.getPageSize());
        model.addAttribute("orders",
                orderRepo.findByUserOrderByPlacedAtDesc(user, pageable));

        return "orderList";
    }
}
