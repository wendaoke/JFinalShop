package com.jfinalshop.model;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.Model;
import com.jfinalshop.util.CommonUtil;
import com.jfinalshop.util.SystemConfigUtil;

/**
 * 实体类 - 购物车项
 * 
 */

public class CartItem extends Model<CartItem>{

	private static final long serialVersionUID = -2752867902533206652L;
	public static final CartItem dao = new CartItem();
	//商品規格
	private List<SpecificationValue> specificationValueList;
	// 商品
	public Product getProduct() {
		return Product.dao.findById(getStr("product_id"));
	}
	
	// 用户
	public Member getMember() {
		return Member.dao.findById(getStr("member_id"));
	}
	
	// 商品規格
	public List<SpecificationValue> getSpecificationValueList() {
		return specificationValueList;
	}
	
	
	//重写save
	public boolean save(CartItem cartItem){
		cartItem.set("id", CommonUtil.getUUID());
		cartItem.set("createDate", new Date());
		return cartItem.save();
	}
	
	//重寫DELETE
	public boolean delete() {
		boolean succeed = Db.tx(new IAtom(){
			public boolean run() throws SQLException {
			int count = Db.update("delete from  cart_specification where carts = ?", getStr("id"));
			int count2 = Db.update("delete from  cartitem where id = ?", getStr("id"));
			return count2 > 0;
			}});
		return succeed;
		
	}
		
	// 获取优惠价格，若member对象为null则返回原价格
	public BigDecimal getPreferentialPrice() {
		Member member = getMember();
		Product product = getProduct();
		if (member != null) {
			BigDecimal preferentialPrice = product.getBigDecimal("price").multiply(new BigDecimal(member.getMemberRank().getDouble("preferentialScale").toString()).divide(new BigDecimal("100")));
			return SystemConfigUtil.getPriceScaleBigDecimal(preferentialPrice);
		} else {
			return product.getBigDecimal("price");
		}
	}
	
	// 获取小计价格
	public BigDecimal getSubtotalPrice() {
		BigDecimal subtotalPrice = getPreferentialPrice().multiply(new BigDecimal(getInt("quantity").toString()));
		return SystemConfigUtil.getOrderScaleBigDecimal(subtotalPrice);
	}

	public void setSpecificationValueList(List<SpecificationValue> specificationValueList) {
		this.specificationValueList = specificationValueList;
	}
}
