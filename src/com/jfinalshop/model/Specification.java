package com.jfinalshop.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;

/**
 * 实体类 - 商品规格
 * 
 */
public class Specification extends Model<Specification> {

	private static final long serialVersionUID = 6055737431593176334L;

	public static final Specification dao = new Specification();

	private List<SpecificationValue> specificationValueList;// 商品规格

	public enum Type {
		text, image
	}

	// 若新修改的值与原来值相等则直接返回true
	public boolean isUnique(String name, String memo) {
		return dao.findFirst("select name from specification where name=? and memo =?  limit 1", name, memo) == null;
	}
	
	public boolean isUpdateUnique(String name, String memo, String category , int orders) {
		return dao.findFirst("select name from specification where name=? and memo =? and category=? and orders = ? limit 1", name, memo,category,orders) == null;
	}

	// 获取所有规格
	public List<Specification> getAll() {
		return dao.find("select * from specification order by orders, createDate desc");
	}

	// 根据名称查找规格
	public Page<Specification> findByPager(String name) {
		String select = "select * ";
		String sqlExceptSelect = " from specification where name = ? order by orders, createDate desc ";

		Page<Specification> pager = dao.paginate(1, 100, select, sqlExceptSelect, name);
		return pager;
	}

	// 获取该规格下的规格属性
	public List<SpecificationValue> getSpecificationValueList() {
		String sql = "select * from specification_value where specification = ?";
		specificationValueList = SpecificationValue.dao.find(sql, getStr("id"));

		if (specificationValueList == null) {
			return null;
		}
		return specificationValueList;
	}

	public HashMap<String, String> findHashMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		List<Specification> specificationlst = getAll();
		for (Iterator<Specification> iter = specificationlst.iterator(); iter.hasNext();) {
			Specification tmp = (Specification) iter.next();
			map.put(tmp.getStr("id"), tmp.getStr("name"));
		}
		return map;
	}

	public Type getType() {
		return Type.values()[this.getInt("type")];
	}
}
