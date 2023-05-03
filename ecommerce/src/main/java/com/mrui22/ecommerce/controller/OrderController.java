package com.mrui22.ecommerce.controller;

import com.mrui22.ecommerce.common.APIResponse;
import com.mrui22.ecommerce.dto.checkout.CheckoutItemDTO;
import com.mrui22.ecommerce.exceptions.AuthenticationFailException;
import com.mrui22.ecommerce.exceptions.OrderNotFoundException;
import com.mrui22.ecommerce.model.Order;
import com.mrui22.ecommerce.model.User;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.mrui22.ecommerce.services.AuthenticationServices;
import com.mrui22.ecommerce.services.OrderServices;
import com.mrui22.ecommerce.common.StripeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServices orderServices;

    @Autowired
    private AuthenticationServices authenticationServices;


    // stripe create session API
    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDTO> checkoutItemDtoList) throws StripeException {
        // create the stripe session
        Session session = orderServices.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        // send the stripe session id in response
        return new ResponseEntity<StripeResponse>(stripeResponse, HttpStatus.OK);
    }

    // place order after checkout
    @PostMapping("/add")
    public ResponseEntity<APIResponse> placeOrder(@RequestParam("token") String token, @RequestParam("sessionId") String sessionId)
            throws AuthenticationFailException {
        // validate token
        authenticationServices.authenticate(token);
        // retrieve user
        User user = authenticationServices.getUser(token);
        // place the order
        orderServices.placeOrder(user, sessionId);
        return new ResponseEntity<>(new APIResponse(true, "Order has been placed"), HttpStatus.CREATED);
    }

    // get all orders
    @GetMapping("/")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam("token") String token) throws AuthenticationFailException {
        // validate token
        authenticationServices.authenticate(token);
        // retrieve user
        User user = authenticationServices.getUser(token);
        // get orders
        List<Order> orderDtoList = orderServices.listOrders(user);

        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }


    // get orderitems for an order
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Integer id, @RequestParam("token") String token)
            throws AuthenticationFailException {
        // validate token
        authenticationServices.authenticate(token);
        try {
            Order order = orderServices.getOrder(id);
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }
}
