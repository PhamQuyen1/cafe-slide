package com.phamquyen.furniticaShop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.phamquyen.furniticaShop.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

}
