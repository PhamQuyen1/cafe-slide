package com.phamquyen.cafeslide.service;

import java.util.List;

import com.phamquyen.cafeslide.domain.Item;

public interface ItemService {

	<S extends Item> List<S> saveAll(Iterable<S> entities);

	<S extends Item> S save(S entity);

}
