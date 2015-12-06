package com.jfinalshop.controller.admin;

import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinalshop.model.Specification;
import com.jfinalshop.model.SpecificationValue;
import com.jfinalshop.validator.admin.SpecificationValueValidator;

/**
 * 后台类 - 商品规格
 * 
 */
public class SpecificationValueController extends BaseAdminController<SpecificationValue>{
	
	private Page<SpecificationValue> pager;
	private SpecificationValue specificationValue;

	// 列表
	public void list() {
		String name = getPara("name", "");
		
		if (StringUtils.isNotEmpty(name)) {
			pager = SpecificationValue.dao.findByPager(name);
			
		} else {			
			pager = SpecificationValue.dao.findByPager();
		}
		setAttr("pager", pager);
		render("/admin/specificationvalue_list.html");
	}
		
	// 添加
	public void add() {
		setAttr("allSpecification", Specification.dao.getAll());
		render("/admin/specificationvalue_input.html");
	}

	// 编辑
	public void edit() {
		String id = getPara("id","");
		if(StrKit.notBlank(id)){
			setAttr("specificationValue", SpecificationValue.dao.findById(id));
		}
		setAttr("allSpecification", Specification.dao.getAll());
		render("/admin/specificationvalue_input.html");
	}
		

		
	// 添加
	@Before(SpecificationValueValidator.class)
	public void save(){
		specificationValue = getModel(SpecificationValue.class);
		
		boolean isExist = SpecificationValue.dao.isUnique(specificationValue.getStr("specification"), specificationValue.getStr("name"));
		if (!isExist) {
			addActionError("商品规格属性已存在!");
			return;
		}		
		saved(specificationValue);
		redirect("/specificationValue/list");		
	}
	
	// 编辑
	@Before(SpecificationValueValidator.class)
	public void update() {
		specificationValue = getModel(SpecificationValue.class);
		boolean isExist = SpecificationValue.dao.isUnique(specificationValue.getStr("name"), specificationValue.getStr("memo"));
		if (!isExist) {
			addActionError("商品规格属性已存在!");
			return;
		}		
		updated(specificationValue);
		redirect("/specificationValue/list");		
	}
	
	// 删除
	public void delete() {
		ids = getParaValues("ids");
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				if(SpecificationValue.dao.deleteById(id)){	
					ajaxJsonSuccessMessage("删除成功！");
				}else{
					ajaxJsonErrorMessage("删除失败！");
				}
			}
		} else {
			ajaxJsonErrorMessage("id为空未选中，删除失败！");
		}	
	}
	
 
	

}
