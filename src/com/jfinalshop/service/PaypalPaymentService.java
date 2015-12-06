package com.jfinalshop.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jfinalshop.bean.PaypalConfig;
import com.jfinalshop.model.OrderItem;
import com.jfinalshop.model.Orders;
import com.jfinalshop.model.Product;
import com.jfinalshop.util.PaypalResultPrinter;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.api.payments.util.GenerateAccessToken;
import com.paypal.api.payments.util.ResultPrinter;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

public class PaypalPaymentService {
	public static final PaypalPaymentService service = new PaypalPaymentService();

	private static final Logger LOGGER = Logger.getLogger(PaypalPaymentService.class);
	Map<String, String> map = new HashMap<String, String>();

	public Payment createPayment(HttpServletRequest req, HttpServletResponse resp, PaypalConfig paypalConfig,String guid, Orders order) {
		String redirectURL = null;
		Payment createdPayment = null;
		// ###AccessToken
		// Retrieve the access token from
		// OAuthTokenCredential by passing in
		// ClientID and ClientSecret
		APIContext apiContext = null;
		String accessToken = null;
		try {
			accessToken = GenerateAccessToken.getAccessToken(paypalConfig.getBargainorId(), paypalConfig.getKey());
			// accessToken = GenerateAccessToken.getAccessToken();
			// ### Api Context
			// Pass in a `ApiContext` object to authenticate
			// the call and to send a unique request id
			// (that ensures idempotency). The SDK generates
			// a request id if you do not pass one explicitly.
			apiContext = new APIContext(accessToken);
			// Use this variant if you want to pass in a request id
			// that is meaningful in your application, ideally
			// a order id.
			/*
			 * String requestId = Long.toString(System.nanoTime(); APIContext
			 * apiContext = new APIContext(accessToken, requestId ));
			 */
		} catch (PayPalRESTException e) {
			req.setAttribute("error", e.getMessage());
		}
		if (req.getParameter("PayerID") != null) {
			Payment payment = new Payment();
			if (req.getParameter("guid") != null) {
				payment.setId(map.get(req.getParameter("guid")));
			}

			PaymentExecution paymentExecution = new PaymentExecution();
			paymentExecution.setPayerId(req.getParameter("PayerID"));
			try {
				createdPayment = payment.execute(apiContext, paymentExecution);
			//	ResultPrinter.addResult(req, resp, "Executed The Payment", Payment.getLastRequest(), Payment.getLastResponse(), null);
			} catch (PayPalRESTException e) {
				// ResultPrinter.addResult(req, resp, "Executed The Payment",
				// Payment.getLastRequest(), null, e.getMessage());
				LOGGER.error("Executed The Payment" + Payment.getLastRequest() + ":" + e.getMessage());
			}
		} else {

			// ###Details
			// Let's you specify details of a payment amount.
			String subTotal = String.valueOf(order.getBigDecimal("productTotalPrice").intValue());
			Details details = new Details();
			details.setShipping("0");
			details.setSubtotal(subTotal);
			details.setTax("0");

			// ###Amount
			// Let's you specify a payment amount.
			Amount amount = new Amount();
			amount.setCurrency("USD");
			// Total must be equal to sum of shipping, tax and subtotal.
			amount.setTotal(subTotal);
			amount.setDetails(details);

			// ###Transaction
			// A transaction defines the contract of a
			// payment - what is the payment for and who
			// is fulfilling it. Transaction is created with
			// a `Payee` and `Amount` types
			Transaction transaction = new Transaction();
			transaction.setAmount(amount);
			transaction.setDescription("This is the payment transaction description.");

			// ### Items
			ItemList itemList = new ItemList();
			List<Item> items = new ArrayList<Item>();
			Item item = null;
			// item.setName("Ground Coffee 40 oz").setQuantity("1").setCurrency("USD").setPrice("5");
			for (OrderItem orderItem : order.getOrderItemList()) {
				item = new Item();
				Product product = orderItem.getProduct();
				BigDecimal price = product.getBigDecimal("price");
				item.setName(product.getStr("name")).setQuantity("1").setCurrency("USD").setPrice(String.valueOf(price.intValue()));
				items.add(item);
			}
			itemList.setItems(items);
			transaction.setItemList(itemList);

			// The Payment creation API requires a list of
			// Transaction; add the created `Transaction`
			// to a List
			List<Transaction> transactions = new ArrayList<Transaction>();
			transactions.add(transaction);

			// ###Payer
			// A resource representing a Payer that funds a payment
			// Payment Method
			// as 'paypal'
			Payer payer = new Payer();
			payer.setPaymentMethod("paypal");

			// ###Payment
			// A Payment Resource; create one using
			// the above types and intent as 'sale'
			Payment payment = new Payment();
			payment.setIntent("sale");
			payment.setPayer(payer);
			payment.setTransactions(transactions);

			// ###Redirect URLs
			RedirectUrls redirectUrls = new RedirectUrls();
		//	String guid = UUID.randomUUID().toString().replaceAll("-", "");
			redirectUrls.setCancelUrl(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/shop/payment/paypalReturn?guid=" + guid);
			redirectUrls.setReturnUrl(req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/shop/payment/paypalReturn?guid=" + guid);
			payment.setRedirectUrls(redirectUrls);

			// Create a payment by posting to the APIService
			// using a valid AccessToken
			// The return object contains the status;
			try {
				createdPayment = payment.create(apiContext);
				LOGGER.info("Created payment with id = " + createdPayment.getId() + " and status = " + createdPayment.getState());

			//	ResultPrinter.addResult(req, resp, "Payment with PayPal", Payment.getLastRequest(), Payment.getLastResponse(), null);
				LOGGER.info("Payment.getLastRequest:"+Payment.getLastRequest());
				LOGGER.info("Payment.getLastResponse:"+Payment.getLastResponse());
				map.put(guid, createdPayment.getId());
			} catch (PayPalRESTException e) {
			//	ResultPrinter.addResult(req, resp, "Payment with PayPal", Payment.getLastRequest(), null, e.getMessage());
				LOGGER.error("Executed The Payment" + Payment.getLastRequest() + ":" + e.getMessage());
			}
		}
		return createdPayment;
	}

	public boolean executePayment(HttpServletRequest req, HttpServletResponse resp, PaypalConfig paypalConfig) {
		// ###AccessToken
		// Retrieve the access token from
		// OAuthTokenCredential by passing in
		// ClientID and ClientSecret
		APIContext apiContext = null;
		String accessToken = null;
		try {
			accessToken = GenerateAccessToken.getAccessToken(paypalConfig.getBargainorId(), paypalConfig.getKey());
			// accessToken = GenerateAccessToken.getAccessToken();
			// ### Api Context
			// Pass in a `ApiContext` object to authenticate
			// the call and to send a unique request id
			// (that ensures idempotency). The SDK generates
			// a request id if you do not pass one explicitly.
			apiContext = new APIContext(accessToken);
			// Use this variant if you want to pass in a request id
			// that is meaningful in your application, ideally
			// a order id.
			/*
			 * String requestId = Long.toString(System.nanoTime(); APIContext
			 * apiContext = new APIContext(accessToken, requestId ));
			 */
		} catch (PayPalRESTException e) {
			LOGGER.error(e.getMessage());
			return false;
		}
		if (req.getParameter("PayerID") != null) {
			Payment payment = new Payment();
			if (req.getParameter("guid") != null) {
				payment.setId(map.get(req.getParameter("guid")));
			}
			PaymentExecution paymentExecution = new PaymentExecution();
			paymentExecution.setPayerId(req.getParameter("PayerID"));
			try {
				Payment createdPayment = payment.execute(apiContext, paymentExecution);
				LOGGER.info("Payment.getLastRequest:"+Payment.getLastRequest());
				LOGGER.info("Payment.getLastResponse:"+Payment.getLastResponse());
			} catch (PayPalRESTException e) {
				LOGGER.error("Executed The Payment" + Payment.getLastRequest() + ":" + e.getMessage());
				return false;
			}
		}else{
			LOGGER.error("PayerID is null!");
			return false;
		}
		return true;
	}
}
