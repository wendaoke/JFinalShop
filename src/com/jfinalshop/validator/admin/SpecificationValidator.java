package com.jfinalshop.validator.admin;

import java.util.ArrayList;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.jfinalshop.model.ProductCategory;
import com.jfinalshop.model.Specification.Type;

public class SpecificationValidator extends Validator{

	@Override
	protected void validate(Controller c) {
		validateRequiredString("specification.category", "productTypeIdMessages", "商品类型不允许为空!");
		validateRequiredString("specification.name", "nameMessages", "规格名称不允许为空!");
		validateRequiredString("specification.memo", "memoMessages", "规格描述不允许为空!");
		validateRequiredString("specificationType", "typeMessages", "规格属性类型不允许为空!");

		validateRequiredString("specification.orders", "orderListMessages", "排序不允许为空!");
		validateInteger("specification.orders", 1, 1000, "orderListMessages", "排序必须为零或正整数，小于1000!");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("allProductCatetory", ProductCategory.dao.getAll());
		c.setAttr("allType", getAllType());		
		c.render("/admin/specification_input.html");
	}
	
	// 获取所有商品规格属性类型
	public List<Type> getAllType() {
		List<Type> allType = new ArrayList<Type>();
		for (Type attributeType : Type.values()) {
			allType.add(attributeType);
		}
		return allType;
	}
}
