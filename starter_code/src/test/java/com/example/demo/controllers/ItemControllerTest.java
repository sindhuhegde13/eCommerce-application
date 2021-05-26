package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;

import junitx.util.PrivateAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    ItemController itemController = new ItemController();
    ItemRepository itemRepository = mock(ItemRepository.class);

    @BeforeEach
    public void setUp() throws NoSuchFieldException {
        PrivateAccessor.setField(itemController,"itemRepository",itemRepository);
    }

    @Test
    public void test_items_by_id() {
        Item item = new Item();
        item.setId(7L);
        when(itemRepository.findById(7L)).thenReturn(Optional.of(item));
        assertEquals(HttpStatus.OK,itemController.getItemById(7L).getStatusCode());
    }

    @Test
    public void test_items_by_name() {
        Item item = new Item();
        item.setName("Round widget");
        List<Item> items = new ArrayList<>();
        items.add(item);
        when(itemRepository.findByName("Round widget")).thenReturn(items);
        assertEquals(HttpStatus.OK,itemController.getItemsByName("Round widget").getStatusCode());
    }

    @Test
    public void test_all_items() {
        Item item = new Item();
        item.setName("Round widget");
        Item item1 = new Item();
        item1.setName("Round toy");
        List<Item> items = new ArrayList<>();
        items.add(item);
        items.add(item1);
        when(itemRepository.findAll()).thenReturn(items);
        assertEquals(HttpStatus.OK,itemController.getItems().getStatusCode());
    }
}
