package com.jason.exam.service.api.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jason.exam.common.util.UUIDTool;
import com.jason.exam.dao.repository.api.ProductsRepository;
import com.jason.exam.model.Products;
import com.jason.exam.service.api.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

	@Resource
	ProductsRepository productsRepository;
	
	@Override
	public List<Products> getProductList(String planId) {
		
		List<Products> products = productsRepository.findProductsAll(planId);
		return products;
	}

	@Override
	public void initProducts(String planId) {
		List<Products> productsList = new ArrayList<Products>();
		Products p0 = new Products();
        p0.setUuid(UUIDTool.getUUID());
        p0.setName("去广告");
        p0.setDiscount_price("6.99");
        p0.setPurchase_price("9.99");
        p0.setVipmonths(0);
        p0.setStatus(1);
        p0.setPlan_uuid(planId);
        p0.setDesc("去除应用中所有广告。");
        p0.setVip_level("0");
        p0.setIos_product_id("CJHS00000001");
        productsList.add(p0);
        
        Products p1 = new Products();
        p1.setUuid(UUIDTool.getUUID());
        p1.setName("月会员");
        p1.setDiscount_price("19.99");
        p1.setPurchase_price("29.99");
        p1.setVipmonths(1);
        p1.setStatus(1);
        p1.setPlan_uuid(planId);
        p1.setDesc("月会员");
        p1.setVip_level("1");
        p1.setIos_product_id("CJHS00000002");
        productsList.add(p1);
        
        Products p2 = new Products();
        p2.setUuid(UUIDTool.getUUID());
        p2.setName("季会员");
        p2.setDiscount_price("59.99");
        p2.setPurchase_price("69.99");
        p2.setVipmonths(3);
        p2.setStatus(1);
        p2.setPlan_uuid(planId);
        p2.setDesc("季会员");
        p2.setVip_level("1");
        p2.setIos_product_id("CJHS00000003");
        productsList.add(p2);
        
        Products p4 = new Products();
        p4.setUuid(UUIDTool.getUUID());
        p4.setName("半年会员");
        p4.setDiscount_price("88.99");
        p4.setPurchase_price("98.99");
        p4.setVipmonths(6);
        p4.setStatus(1);
        p4.setPlan_uuid(planId);
        p4.setDesc("半年会员");
        p4.setVip_level("1");
        p4.setIos_product_id("CJHS00000004");
        productsList.add(p4);
        
        Products p3 = new Products();
        p3.setUuid(UUIDTool.getUUID());
        p3.setName("年会员");
        p3.setDiscount_price("158.99");
        p3.setPurchase_price("168.99");
        p3.setVipmonths(12);
        p3.setStatus(1);
        p3.setPlan_uuid(planId);
        p3.setDesc("年会员");
        p3.setVip_level("1");
        p3.setIos_product_id("CJHS00000005");
        productsList.add(p3);
        
        for (Products product : productsList)
        {
        	productsRepository.save(product);
        }
		
	}
	

	

}
