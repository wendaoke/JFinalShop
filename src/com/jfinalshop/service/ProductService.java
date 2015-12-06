package com.jfinalshop.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.jfinalshop.model.ProductSpecification;
import com.jfinalshop.model.SpecificationValue;



/**
 * Service实现类 - 商品
 * 
 */

public class ProductService {
	
	public static final ProductService service = new ProductService();
	
	public HashMap<String,List<SpecificationValue>> getSpecificationList(String productId){
		HashMap<String,List<SpecificationValue>> map = new HashMap<String,List<SpecificationValue>>();
		List<ProductSpecification> pslist =  new ProductSpecification().set("products", productId).getSpecificationByProduct();
		for (Iterator<ProductSpecification> it = pslist.iterator(); it.hasNext();)
		  {
			ProductSpecification temp = (ProductSpecification) it.next();
			List<SpecificationValue> svlst = new SpecificationValue().set("specification", temp.get("specifications")).getValuesBySpecification();
			map.put(temp.getStr("name"), svlst);
		  }
		return map;
	}

}