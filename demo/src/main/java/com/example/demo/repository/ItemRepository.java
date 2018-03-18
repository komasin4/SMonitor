package com.example.demo.repository;

import java.util.HashMap;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Item;

//public interface ItemRepository  extends CrudRepository<Item, String>{
public interface ItemRepository  extends CrudRepository<Item, String>{
	
	@Query(nativeQuery = true, value = "SELECT I.* FROM item I, `group` G WHERE I.group_no = G.group_no AND I.type = :type order by G.order, I.order")
	List<Item> getItems(@Param("type") String type);

	@Query(nativeQuery = true, value = "SELECT I.item_no, G.group_name, I.type FROM item I, `group` G WHERE I.group_no = G.group_no order by G.order, I.order")
	List<HashMap<String,String>> getOrder();
	
	@Query(nativeQuery = true, value = "SELECT I.*, G.group_name, null data FROM item I, `group` G WHERE I.group_no = G.group_no order by G.order, I.order")
	List<Item> getAllItems();
}
