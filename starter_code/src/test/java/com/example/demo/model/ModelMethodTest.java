package com.example.demo.model;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ModelMethodTest {

    @Test
    public void test_cart() {
        Cart cart = new Cart();
        User user = new User();
        user.setUsername("test");
        user.setId(1);
        cart.setId(1L);
        cart.setUser(user);
        Item item = new Item();
        item.setId(1L);
        item.setName("Round widget");
        BigDecimal price = new BigDecimal("1.00");
        item.setPrice(price);
        List<Item> itemList = new ArrayList<>();
        cart.setItems(itemList);
        cart.setTotal(price);
        assertEquals(1,user.getId());
        assertEquals("test",cart.getUser().getUsername());
        assertNotNull(cart.getId());
        assertNotNull(cart.getItems());
        assertNotNull(cart.getTotal());
    }

    @Test
    public void test_item() {
        Item item = new Item();
        item.setId(1L);
        item.setName("Round widget");
        BigDecimal price = new BigDecimal("1.00");
        item.setPrice(price);
        item.setDescription("test item");
        assertNotNull(item.hashCode());
        assertEquals("test item",item.getDescription());
        assertNotNull(item.getId());
        assertNotNull(item.getName());
        assertNotNull(item.getPrice());
    }

    @Test
    public void test_user_order() {
        UserOrder userOrder = new UserOrder();
        userOrder.setId(1L);
        userOrder.setTotal(new BigDecimal("1.00"));
        Item item = mock(Item.class);
        List<Item> items = new ArrayList<>();
        items.add(item);
        User user = mock(User.class);
        userOrder.setUser(user);
        userOrder.setItems(items);
        assertNotNull(userOrder.getUser());
        assertNotNull(userOrder.getItems());
        assertNotNull(userOrder.getId());
        assertNotNull(userOrder.getTotal());
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("test");
        createUserRequest.setPassword("abc123456");
        createUserRequest.setConfirmPassword("abc123456");
        assertEquals("test",createUserRequest.getUsername());
        assertEquals("abc123456",createUserRequest.getPassword());
        assertEquals("abc123456",createUserRequest.getConfirmPassword());
    }
}