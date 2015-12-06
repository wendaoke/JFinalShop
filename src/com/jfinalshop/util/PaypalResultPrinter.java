package com.jfinalshop.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class PaypalResultPrinter {
	private static final Logger LOGGER = Logger.getLogger(PaypalResultPrinter.class);

	public static void addResult(HttpServletRequest req, HttpServletResponse resp, String message, String request, String response) {
        String error = null;
		addDataToAttributeList(req, "messages", message);
		addDataToAttributeList(req, "requests", request);
		response = (response != null) ? response : error;
		addDataToAttributeList(req, "responses", response);
		addDataToAttributeList(req, "errors", error);

	}

	@SuppressWarnings("unchecked")
	private static void addDataToAttributeList(HttpServletRequest req, String listName, String data) {
		// Add Messages
		List<String> list;
		if ((list = (List<String>) req.getAttribute(listName)) == null) {
			list = new ArrayList<String>();
		}
		list.add(data);
		LOGGER.info("name:"+listName+";list:"+list);
		//req.setAttribute(listName, list);
	}
}
