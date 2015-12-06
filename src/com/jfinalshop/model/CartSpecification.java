package com.jfinalshop.model;

import java.util.List;

import com.jfinal.ext.plugin.tablebind.TableBind;
import com.jfinal.plugin.activerecord.Model;
import com.jfinalshop.util.CommonUtil;

/**
 * 实体类 - 购物车项
 * 
 */
@TableBind(tableName = "cart_specification")
public class CartSpecification extends Model<CartSpecification>{

	private static final long serialVersionUID = -2752867982533206652L;
	public static final CartSpecification dao = new CartSpecification();
    //商品規格
	public Specification getSpecification() {
		return Specification.dao.findById(getStr("specifications"));
	}
	
    //商品規格屬性
	public SpecificationValue getSpecificationValue() {
		return SpecificationValue.dao.findById(getStr("specification_values"));
	}
	
	
	public List<CartSpecification> getCartSpecificationListByCart(String cartId) {
		return CartSpecification.dao.find("SELECT * FROM cart_specification where carts = ? ",cartId);
	}
	
	//重写save
	public boolean save(CartSpecification cartSpecification){
		cartSpecification.set("id", CommonUtil.getUUID());
		return cartSpecification.save();
	}
		

}
