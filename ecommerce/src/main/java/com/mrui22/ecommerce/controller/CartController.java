package com.mrui22.ecommerce.controller;

import com.mrui22.ecommerce.common.APIResponse;
import com.mrui22.ecommerce.dto.cart.AddToCartDTO;
import com.mrui22.ecommerce.dto.cart.CartDTO;
import com.mrui22.ecommerce.exceptions.AuthenticationFailException;
import com.mrui22.ecommerce.exceptions.CartItemNotExistException;
import com.mrui22.ecommerce.exceptions.ProductNotExistException;
import com.mrui22.ecommerce.model.AuthenticationToken;
import com.mrui22.ecommerce.model.Product;
import com.mrui22.ecommerce.model.User;
import com.mrui22.ecommerce.services.AuthenticationServices;
import com.mrui22.ecommerce.services.CartServices;
import com.mrui22.ecommerce.services.AuthenticationServices;
import com.mrui22.ecommerce.services.CartServices;
import com.mrui22.ecommerce.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartServices cartServices;

    @Autowired
    AuthenticationServices authenticationServices;

    @Autowired
    ProductServices productServices;

    @PostMapping("/add")
    public ResponseEntity<APIResponse> addToCart(@RequestBody AddToCartDTO addToCartDTO,
                                                 @RequestParam("token") String token) throws AuthenticationFailException, ProductNotExistException {
        authenticationServices.authenticate(token);
        User user = authenticationServices.getUser(token);
        Product product = productServices.getProductById(addToCartDTO.getProductId());
        System.out.println("product to add"+  product.getName());
        cartServices.addToCart(addToCartDTO, product, user);
        return new ResponseEntity<APIResponse>(new APIResponse(true, "Added to cart"), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<CartDTO> getCartItems(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationServices.authenticate(token);
        User user = authenticationServices.getUser(token);
        CartDTO CartDTO = cartServices.listCartItems(user);
        return new ResponseEntity<CartDTO>(CartDTO,HttpStatus.OK);
    }
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<APIResponse> updateCartItem(@RequestBody AddToCartDTO CartDTO,
                                                      @RequestParam("token") String token) throws AuthenticationFailException,ProductNotExistException {
        authenticationServices.authenticate(token);
        User user = authenticationServices.getUser(token);
        Product product = productServices.getProductById(CartDTO.getProductId());
        cartServices.updateCartItem(CartDTO, user,product);
        return new ResponseEntity<APIResponse>(new APIResponse(true, "Product has been updated"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<APIResponse> deleteCartItem(@PathVariable("cartItemId") int itemID,@RequestParam("token") String token) throws AuthenticationFailException, CartItemNotExistException {
        authenticationServices.authenticate(token);
        int userId = authenticationServices.getUser(token).getId();
        cartServices.deleteCartItem(itemID, userId);
        return new ResponseEntity<APIResponse>(new APIResponse(true, "Item has been removed"), HttpStatus.OK);
    }
}
