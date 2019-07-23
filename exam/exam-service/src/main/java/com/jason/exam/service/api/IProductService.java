package com.jason.exam.service.api;

import java.util.List;

import com.jason.exam.model.Products;

public interface IProductService {
	/**
	 * 产品列表
	 * 
	 * @param planId
	 */
	List<Products> getProductList(String planId);
	
	/**
	 * 初始化产品列表
	 * 
	 * @param planId
	 */
	void initProducts(String planId);

	
}
