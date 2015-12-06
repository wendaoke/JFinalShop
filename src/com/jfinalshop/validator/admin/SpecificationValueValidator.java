package com.jfinalshop.validator.admin;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.jfinalshop.model.Specification;

public class SpecificationValueValidator extends Validator{

	@Override
	protected void validate(Controller c) {
		validateRequiredString("specificationValue.specification", "specificationMessages", "规格不允许为空!");
		validateRequiredString("specificationValue.name", "memoMessages", "属性名称不允许为空!");

		validateRequiredString("specificationValue.orders", "orderListMessages", "排序不允许为空!");
		validateInteger("specificationValue.orders", 1, 1000, "orderListMessages", "排序必须为零或正整数，小于1000!");
	}

	@Override
	protected void handleError(Controller c) {
		c.setAttr("allSpecification", Specification.dao.getAll());
		c.render("/admin/specificationvalue_input.html");
	}
	

}
