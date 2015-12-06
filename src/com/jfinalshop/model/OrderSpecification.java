package com.jfinalshop.model;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;
import com.jfinalshop.util.CommonUtil;

/**
 * 实体类 - 购物车项
 * 
 */
@TableBind(tableName = "order_specification")
public class OrderSpecification extends Model<OrderSpecification>{

	private static final long serialVersionUID = -2752867982539206652L;
	public static final OrderSpecification dao = new OrderSpecification();

	
	//重写save
	public boolean save(OrderSpecification orderSpecification){
		orderSpecification.set("id", CommonUtil.getUUID());
		return orderSpecification.save();
	}
		

}
