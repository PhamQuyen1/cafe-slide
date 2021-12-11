package com.phamquyen.furniticaShop.service;

import java.util.List;

import com.phamquyen.furniticaShop.domain.Item;

public interface ItemService {

	<S extends Item> List<S> saveAll(Iterable<S> entities);

	<S extends Item> S save(S entity);

}
