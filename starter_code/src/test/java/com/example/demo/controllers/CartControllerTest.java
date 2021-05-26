package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import junitx.util.PrivateAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    CartController cartController = new CartController();
    ItemRepository itemRepository = mock(ItemRepository.class);
    UserRepository userRepository = mock(UserRepository.class);
    CartRepository cartRepository = mock(CartRepository.class);

    @BeforeEach
    public void setUp() throws NoSuchFieldException {
        PrivateAccessor.setField(cartController,"userRepository",userRepository);
        PrivateAccessor.setField(cartController,"cartRepository",cartRepository);
        PrivateAccessor.setField(cartController,"itemRepository",itemRepository);
    }

    @Test
    public void test_add_or_remove_to_cart() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("testUser");
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(1);
        User user = new User();
        user.setUsername("testUser");
        Item item = new Item();
        item.setId(1L);
        item.setName("Round widget");
        BigDecimal price = new BigDecimal("1.00");
        item.setPrice(price);
        Cart cart = new Cart();
        user.setCart(cart);
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(itemRepository.findById(modifyCartRequest.getItemId())).thenReturn(Optional.of(item));
        assertEquals(HttpStatus.OK,cartController.addTocart(modifyCartRequest).getStatusCode());
        assertEquals(HttpStatus.OK,cartController.removeFromcart(modifyCartRequest).getStatusCode());
    }

    @Test
    public void test_add_or_remove_to_cart_no_user() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("testUser");
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(1);
        when(userRepository.findByUsername("testUser")).thenReturn(null);
        assertEquals(HttpStatus.NOT_FOUND,cartController.addTocart(modifyCartRequest).getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND,cartController.removeFromcart(modifyCartRequest).getStatusCode());
    }

    @Test
    public void test_add_or_remove_to_cart_no_item() {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setUsername("testUser");
        modifyCartRequest.setItemId(2L);
        modifyCartRequest.setQuantity(1);
        User user = new User();
        user.setUsername("testUser");
        Item item = new Item();
        item.setId(1L);
        item.setName("Round widget");
        BigDecimal price = new BigDecimal("1.00");
        item.setPrice(price);
        Cart cart = new Cart();
        user.setCart(cart);
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(itemRepository.findById(modifyCartRequest.getItemId())).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND,cartController.addTocart(modifyCartRequest).getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND,cartController.removeFromcart(modifyCartRequest).getStatusCode());
    }
}
