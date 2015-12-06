package com.paypal.api.payments;

import com.paypal.base.rest.PayPalModel;

public class BaseAddress extends PayPalModel {

	/**
	 * Line 1 of the Address (eg. number, street, etc).
	 */
	private String line1;

	/**
	 * Optional line 2 of the Address (eg. suite, apt #, etc.).
	 */
	private String line2;

	/**
	 * City name.
	 */
	private String city;

	/**
	 * 2 letter country code.
	 */
	private String countryCode;

	/**
	 * Zip code or equivalent is usually required for countries that have them.
	 * For list of countries that do not have postal codes please refer to
	 * http://en.wikipedia.org/wiki/Postal_code.
	 */
	private String postalCode;

	/**
	 * 2 letter code for US states, and the equivalent for other countries.
	 */
	private String state;

	private String normalizationStatus;
	
	/**
	 * Address status
	 */
	private String status;

	/**
	 * Setter for line1
	 */
	public BaseAddress setLine1(String line1) {
		this.line1 = line1;
		return this;
	}

	/**
	 * Getter for line1
	 */
	public String getLine1() {
		return this.line1;
	}

	/**
	 * Setter for line2
	 */
	public BaseAddress setLine2(String line2) {
		this.line2 = line2;
		return this;
	}

	/**
	 * Getter for line2
	 */
	public String getLine2() {
		return this.line2;
	}

	/**
	 * Setter for city
	 */
	public BaseAddress setCity(String city) {
		this.city = city;
		return this;
	}

	/**
	 * Getter for city
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * Setter for countryCode
	 */
	public BaseAddress setCountryCode(String countryCode) {
		this.countryCode = countryCode;
		return this;
	}

	/**
	 * Getter for countryCode
	 */
	public String getCountryCode() {
		return this.countryCode;
	}

	/**
	 * Setter for postalCode
	 */
	public BaseAddress setPostalCode(String postalCode) {
		this.postalCode = postalCode;
		return this;
	}

	/**
	 * Getter for postalCode
	 */
	public String getPostalCode() {
		return this.postalCode;
	}

	/**
	 * Setter for state
	 */
	public BaseAddress setState(String state) {
		this.state = state;
		return this;
	}

	/**
	 * Getter for state
	 */
	public String getState() {
		return this.state;
	}
	
	public BaseAddress setNormalizationStatus(String normalizationStatus) {
		this.normalizationStatus = normalizationStatus;
		return this;
	}
	
	public String getNormalizationStatus() {
		return this.normalizationStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
