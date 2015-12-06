package com.jfinalshop.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Before;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinalshop.model.ProductCategory;
import com.jfinalshop.model.Specification;
import com.jfinalshop.model.Specification.Type;
import com.jfinalshop.validator.admin.SpecificationValidator;

/**
 * 后台类 - 商品规格
 * 
 */
public class SpecificationController extends BaseAdminController<Specification>{
	
	private Page<Specification> pager;
	private Specification specification;

	// 列表
	public void list() {
		String name = getPara("name", "");
		
		if (StringUtils.isNotEmpty(name)) {
			pager = Specification.dao.findByPager(name);
			setAttr("pager", pager);
		} else {			
			findByPage();
		}
		render("/admin/specification_list.html");
	}
		
	// 添加
	public void add() {
		setAttr("allProductCatetory", ProductCategory.dao.getAll());
		setAttr("allType", getAllType());	
		render("/admin/specification_input.html");
	}

	// 编辑
	public void edit() {
		String id = getPara("id","");
		if(StrKit.notBlank(id)){
			setAttr("specification", Specification.dao.findById(id));
		}
		setAttr("allProductCatetory", ProductCategory.dao.getAll());
		setAttr("allType", getAllType());	
		render("/admin/specification_input.html");
	}
		

		
	// 添加
	@Before(SpecificationValidator.class)
	public void save(){
		specification = getModel(Specification.class);
		
		boolean isExist = Specification.dao.isUnique(specification.getStr("name"), specification.getStr("memo"));
		if (!isExist) {
			addActionError("商品规格名称已存在!");
			return;
		}		
		// 属性类型
		Type type  = Type.valueOf(getPara("specificationType"));	
		specification.set("type", type.ordinal());
		saved(specification);
		redirect("/specification/list");		
	}
	
	// 编辑
	@Before(SpecificationValidator.class)
	public void update() {
		specification = getModel(Specification.class);
		boolean isExist = Specification.dao.isUnique(specification.getStr("name"), specification.getStr("memo"));
		if (!isExist) {
			addActionError("商品规格名称已存在!");
			return;
		}		
		updated(specification);
		redirect("/specification/list");		
	}
	
	// 删除
	public void delete() {
		ids = getParaValues("ids");
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				if(Specification.dao.deleteById(id)){	
					ajaxJsonSuccessMessage("删除成功！");
				}else{
					ajaxJsonErrorMessage("删除失败！");
				}
			}
		} else {
			ajaxJsonErrorMessage("id为空未选中，删除失败！");
		}	
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
