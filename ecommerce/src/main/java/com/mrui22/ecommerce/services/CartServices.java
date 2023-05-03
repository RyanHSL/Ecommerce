package com.mrui22.ecommerce.services;

import com.mrui22.ecommerce.dto.cart.AddToCartDTO;
import com.mrui22.ecommerce.dto.cart.CartDTO;
import com.mrui22.ecommerce.dto.cart.CartItemDTO;
import com.mrui22.ecommerce.exceptions.CartItemNotExistException;
import com.mrui22.ecommerce.model.Cart;
import com.mrui22.ecommerce.model.Product;
import com.mrui22.ecommerce.model.User;
import com.mrui22.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartServices {

    @Autowired
    private CartRepository cartRepository;

    public CartServices(){}

    public CartServices(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addToCart(AddToCartDTO addToCartDto, Product product, User user){
        Cart cart = new Cart(product, addToCartDto.getQuantity(), user);
        cartRepository.save(cart);
    }


    public CartDTO listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreateDateDesc(user);
        List<CartItemDTO> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDTO CartItemDTO = getDtoFromCart(cart);
            cartItems.add(CartItemDTO);
        }
        double totalCost = 0;
        for (CartItemDTO CartItemDTO :cartItems){
            totalCost += (CartItemDTO.getProduct().getPrice()* CartItemDTO.getQuantity());
        }
        return new CartDTO(cartItems,totalCost);
    }


    public static CartItemDTO getDtoFromCart(Cart cart) {
        return new CartItemDTO(cart);
    }


    public void updateCartItem(AddToCartDTO cartDto, User user,Product product){
        Cart cart = cartRepository.getOne(cartDto.getId());
        cart.setQuantity(cartDto.getQuantity());
        cart.setCreateDate(new Date());
        cartRepository.save(cart);
    }

    public void deleteCartItem(int id,int userId) throws CartItemNotExistException {
        if (!cartRepository.existsById(id))
            throw new CartItemNotExistException("Cart id is invalid : " + id);
        cartRepository.deleteById(id);

    }

    public void deleteCartItems(int userId) {
        cartRepository.deleteAll();
    }


    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }
}
