package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import junitx.util.PrivateAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    UserController userController = new UserController();
    UserRepository userRepository = mock(UserRepository.class);
    CartRepository cartRepository = mock(CartRepository.class);
    BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @BeforeEach
    public void setUp() throws NoSuchFieldException {
        PrivateAccessor.setField(userController,"userRepository",userRepository);
        PrivateAccessor.setField(userController,"cartRepository",cartRepository);
        PrivateAccessor.setField(userController,"bCryptPasswordEncoder",bCryptPasswordEncoder);
    }

    @Test
    public void test_create_user() {
        CreateUserRequest createUserRequest = mock(CreateUserRequest.class);
        when(createUserRequest.getUsername()).thenReturn("testUser");
        when(createUserRequest.getPassword()).thenReturn("abc123456");
        when(createUserRequest.getConfirmPassword()).thenReturn("abc123456");
        Cart cart = mock(Cart.class);
        User user = mock(User.class);
        when(cartRepository.save(any())).thenReturn(cart);
        when(userRepository.save(any())).thenReturn(user);
        assertEquals(HttpStatus.OK,userController.createUser(createUserRequest).getStatusCode());
    }

    @Test
    public void test_find_by_id() {
        User user = new User();
        user.setId(5L);
        user.setUsername("testUser");
        Optional<User> user1 = Optional.of(user);
        when(userRepository.findById(5L)).thenReturn(user1);
        assertNotNull(userController.findById(5L));
    }

    @Test
    public void test_find_by_username_exists() {
        User user = new User();
        user.setUsername("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        assertEquals(HttpStatus.OK,userController.findByUserName("testUser").getStatusCode());
    }

    @Test
    public void test_find_by_username_not_exists() {
        User user = new User();
        user.setUsername("test");
        when(userRepository.findByUsername("test")).thenReturn(null);
        assertEquals(HttpStatus.NOT_FOUND,userController.findByUserName("test").getStatusCode());
    }
}