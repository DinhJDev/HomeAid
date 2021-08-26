package com.homeaid.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeaid.models.Item;
import com.homeaid.repositories.ItemRepository;

@Service
public class ItemService {
	@Autowired
	ItemRepository itemRepository;
	
	public List<Item> allItem(){
		return this.itemRepository.findAll();
	}
	
	public Item createItem (Item item) {
		return this.itemRepository.save(item);
	}
	
	public Item getOneItem(Long id) {
		return this.itemRepository.findById(id).orElse(null);
	}
	
	public Item updateItem (Item item) {
		return this.itemRepository.save(item);
	}
	
	public void deleteItem(Long id) {
		this.itemRepository.deleteById(id);
	}
	
}
