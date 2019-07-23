package com.jason.exam.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jason.exam.model.Products;
import com.jason.exam.model.common.R;
import com.jason.exam.service.api.IProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	IProductService productService;

	// 获取产品列表
	@RequestMapping("productList")
	@ResponseBody
	public R productList(@RequestParam Map<String, String> params) {
		try {
			String planId = params.get("planUuid");
			List<Products> products = productService.getProductList(planId);
			return R.isOk().data(products);
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
	}

	// 初始化产品
	@RequestMapping("initProducts")
	@ResponseBody
	public R initProducts(@RequestParam Map<String, String> params) {
		try {
			String planId = params.get("planUuid");
			productService.initProducts(planId);
			return R.isOk();
		} catch (Exception e) {
			e.printStackTrace();
			return R.isFail(e);
		}
	}

}
