package com.phamquyen.cafeslide.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.phamquyen.cafeslide.domain.Item;
import com.phamquyen.cafeslide.repository.ItemRepository;
import com.phamquyen.cafeslide.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	ItemRepository itemRepository;

	@Override
	public <S extends Item> List<S> saveAll(Iterable<S> entities) {
		return itemRepository.saveAll(entities);
	}

	@Override
	public <S extends Item> S save(S entity) {
		return itemRepository.save(entity);
	}
	
	
}
