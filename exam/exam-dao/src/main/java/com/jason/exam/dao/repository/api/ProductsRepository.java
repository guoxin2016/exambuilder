package com.jason.exam.dao.repository.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.jason.exam.model.Products;

public interface ProductsRepository extends JpaRepository<Products, Integer>,JpaSpecificationExecutor<Products> {
	
	@Query(value = "select * from tb_products where status = 1 and plan_uuid = ?1", nativeQuery = true)
	List<Products> findProductsAll(String planUuid);
	
	@Query(value = "select * from tb_products where status = 1 and uuid = ?1", nativeQuery = true)
	Products findProductsByUuid(String uuid);
	
	
	
	
}
