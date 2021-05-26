package com.example.demo.security;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import junitx.util.PrivateAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserDetailsTest {

    UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl();
    UserRepository userRepository = mock(UserRepository.class);

    @BeforeEach
    public void setUp() throws NoSuchFieldException {
        PrivateAccessor.setField(userDetailsService,"userRepository",userRepository);
    }

    @Test
    public void test_user_methods() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("abc123456");
        when(userRepository.findByUsername("test")).thenReturn(user);
        assertNotNull(userDetailsService.loadUserByUsername("test"));
    }

    @Test
    public void test_user_methods_null() {
        when(userRepository.findByUsername("test")).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("test"));
    }
}
