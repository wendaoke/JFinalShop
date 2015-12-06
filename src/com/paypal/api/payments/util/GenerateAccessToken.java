package com.paypal.api.payments.util;

import org.apache.log4j.Logger;

import com.paypal.base.ConfigManager;
import com.paypal.base.Constants;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;

public class GenerateAccessToken { 
	private static final Logger LOGGER = Logger.getLogger(GenerateAccessToken.class);
	public static String getAccessToken() throws PayPalRESTException {

		// ###AccessToken
		// Retrieve the access token from
		// OAuthTokenCredential by passing in
		// ClientID and ClientSecret
		@SuppressWarnings("deprecation")
		String clientID = ConfigManager.getInstance().getValue(Constants.CLIENT_ID);
		@SuppressWarnings("deprecation")
		String clientSecret = ConfigManager.getInstance().getValue(
				Constants.CLIENT_SECRET);
		LOGGER.info("clientID:"+clientID+";clientSecret:"+clientSecret);
		return new OAuthTokenCredential(clientID, clientSecret)
				.getAccessToken();
	}
	
	public static String getAccessToken(String clientID,String clientSecret) throws PayPalRESTException {
		LOGGER.info("clientID:"+clientID+";clientSecret:"+clientSecret);
		return new OAuthTokenCredential(clientID, clientSecret)
				.getAccessToken();
	}
}
