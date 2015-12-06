package com.jfinalshop.bean;

import com.jfinalshop.bean.SystemConfig.CurrencyType;

/**
 * Bean类 -贝宝配置
 *
 */
public class PaypalConfig {


	// 支持货币种类
	public static final CurrencyType[] currencyType = {CurrencyType.CNY,CurrencyType.USD};
	private String accessSite;
	private String bargainorId;// 商户号
	private String key;// 密钥
	private String sign;//签名
	public String getBargainorId() {
		return bargainorId;
	}
	public void setBargainorId(String bargainorId) {
		this.bargainorId = bargainorId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getAccessSite() {
		return accessSite;
	}
	public void setAccessSite(String accessSite) {
		this.accessSite = accessSite;
	}
		
}
