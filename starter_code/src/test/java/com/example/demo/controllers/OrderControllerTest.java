package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import junitx.util.PrivateAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    OrderController orderController = new OrderController();
    UserRepository userRepository = mock(UserRepository.class);
    OrderRepository orderRepository = mock(OrderRepository.class);

    @BeforeEach
    public void setUp() throws NoSuchFieldException {
        PrivateAccessor.setField(orderController,"userRepository",userRepository);
        PrivateAccessor.setField(orderController,"orderRepository", orderRepository);
    }

    @Test
    public void test_order_submit() {
        User user = new User();
        user.setUsername("testUser");
        Item item = new Item();
        item.setId(1L);
        item.setName("Round widget");
        BigDecimal price = new BigDecimal("1.00");
        item.setPrice(price);
        Cart cart = new Cart();
        cart.addItem(item);
        user.setCart(cart);
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        assertEquals(HttpStatus.OK,orderController.submit("testUser").getStatusCode());
        assertEquals(HttpStatus.OK,orderController.getOrdersForUser("testUser").getStatusCode());
    }
    @Test
    public void test_order_submit_no_user() {
        when(userRepository.findByUsername("testUser")).thenReturn(null);
        assertEquals(HttpStatus.NOT_FOUND,orderController.submit("testUser").getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND,orderController.getOrdersForUser("testUser").getStatusCode());
    }
}