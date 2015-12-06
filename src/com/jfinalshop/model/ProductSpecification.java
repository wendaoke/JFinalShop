package com.jfinalshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;

/**
 * 实体类 - 商品规格属性
 * 
 */
@TableBind(tableName = "product_specification")
public class ProductSpecification extends Model<ProductSpecification> {

	private static final long serialVersionUID = 2009717181819370223L;

	public static final ProductSpecification dao = new ProductSpecification();
	

		    
    // 商品规格属性
  	public List<ProductSpecification> getSpecificationByProduct() {
 		return ProductSpecification.dao.find("SELECT * FROM product_specification t1, specification t2 WHERE t1.specifications = t2.id and t1.products = ?",getStr("products"));
 	}
  	

  	public String getSpecificationNamesByProduct() {
 		return ProductSpecification.dao.findFirst("select * from product_specification where products = ?",getStr("products")).getStr("specification");
 	}



  	    


 	
 	
 	
}
