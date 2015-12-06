package com.jfinalshop.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinalshop.util.CommonUtil;

/**
 * 实体类 - 商品规格属性
 * 
 */
@TableBind(tableName = "specification_value")
public class SpecificationValue extends Model<SpecificationValue> {

	private static final long serialVersionUID = 2009717181319370223L;

	public static final SpecificationValue dao = new SpecificationValue();
 
	

	// 若新修改的值与原来值相等则直接返回true
	public boolean isUnique(String specification,String name) {
		return dao.findFirst("select name from specification_value where specification=? and name =? limit 1", specification,name) == null;
	}
	
	/**
	 * 按规格分类查询规格属性
	 * 
	 */
	public Page<SpecificationValue> findByPager(String specification) {
		String select = "SELECT t2.id,t2.createDate,t2.modifyDate,t2.orders,t2.image,t2.name,CONCAT(t1.name,\"[\",t1.memo,\"]\") AS specification ";
		String sqlExceptSelect = " FROM specification t1 , specification_value t2 WHERE t1.id = t2.specification and t2.specification = ? order by t2.createDate desc ";
		
		Page<SpecificationValue> pager = dao.paginate(1, 100, select, sqlExceptSelect,specification);
		return pager;
	}
	
	public Page<SpecificationValue> findByPager() {
		String select = "SELECT t2.id,t2.createDate,t2.modifyDate,t2.orders,t2.image,t2.name,CONCAT(t1.name,\"[\",t1.memo,\"]\") AS specification ";
		String sqlExceptSelect = " FROM specification t1 , specification_value t2 WHERE t1.id = t2.specification   order by t2.createDate desc ";
		
		Page<SpecificationValue> pager = dao.paginate(1, 100, select, sqlExceptSelect);
		return pager;
	}
		    
    // 商品规格属性
  	public SpecificationValue getValues() {
 		return SpecificationValue.dao.findFirst("select * from specification_value where id = ?",getStr("id"));
 	}
  	
	// 获取所有规格
	public List<SpecificationValue> getAll() {
		return dao.find("select * from specification_value order by createDate desc");
	}
	
	public List<SpecificationValue> getSpecificationValueList(String[] svArray) {
		if(null == svArray) 
			return null;
		String tmp= CommonUtil.ArraytoString(svArray);
		
		return dao.find("select * from specification_value where concat_ws('_',specification,id) in "+tmp+" order by createDate desc");
	}
	
	public List<SpecificationValue> getValuesBySpecification() {
		return dao.find("select * from specification_value where specification = ? order by orders asc ",getStr("specification"));
	}
	
	public List<SpecificationValue> getSpecificationValuesByCartItem(String cartId) {
		return dao.find("select t1.* from specification_value t1, cartitem t2, cart_specification t3 "
				+ "where t2.id = t3.carts and t3.specification_values = t1.id and t2.id = ? order by orders asc ",cartId);
	}
	
	public List<SpecificationValue> getSpecificationValuesByOrderItem(String orderId,String cartitemId,String productId) {
		return dao.find("select t1.* from specification_value t1, orderitem t2,  order_specification t3 "
				+ "where t3.specification_values = t1.id  and t3.cartitems = t2.cartitem_id and t2.order_id = ? and t2.cartitem_id=? and t2.product_id = ? order by t3.orders asc ",orderId,cartitemId,productId);
	}
	
	public Specification getSpecification() {
		return Specification.dao.findById(getStr("specification"));
	}
	
	public HashMap<String, String> findHashMap() {
		HashMap<String, String> map = new HashMap<String, String>();
		List<SpecificationValue> lst = getAll();
		for (Iterator<SpecificationValue> iter = lst.iterator(); iter.hasNext();) {
			SpecificationValue tmp = (SpecificationValue) iter.next();
			map.put(tmp.getStr("id"), tmp.getStr("name"));
		}
		return map;
	}


 	
 	
 	
}
