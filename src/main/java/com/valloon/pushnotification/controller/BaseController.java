package com.valloon.pushnotification.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.valloon.pushnotification.service.PreviousHistoryService;
import com.valloon.pushnotification.service.ResultHistoryService;

/**
 * @author Valloon Project
 * @version 1.0 @2019-10-06
 */
@RequestMapping("/")
public abstract class BaseController {

	@Autowired
	protected PreviousHistoryService previousHistoryService;
	@Autowired
	protected ResultHistoryService resultHistoryService;

	protected static final int getInt(HttpServletRequest request, final String attr, final int defaultValue) {
		try {
			return Integer.parseInt(request.getParameter(attr));
		} catch (Exception e) {
		}
		return defaultValue;
	}

	protected static final long getLong(HttpServletRequest request, final String attr, final long defaultValue) {
		try {
			return Long.parseLong(request.getParameter(attr));
		} catch (Exception e) {
		}
		return defaultValue;
	}

	protected static final String getString(HttpServletRequest request, final String attr, final boolean trim) {
		String s = request.getParameter(attr);
		if (s == null)
			return null;
		if (trim)
			s = s.trim();
		if (s.isEmpty())
			return null;
		return s;
	}

	protected static final String getString(HttpServletRequest request, final String attr, final boolean trim,
			final boolean byteConvert) throws UnsupportedEncodingException {
		String s = request.getParameter(attr);
		if (s == null)
			return null;
		if (trim)
			s = s.trim();
		if (s.isEmpty())
			return null;
		if (byteConvert) {
			int len = s.length();
			byte[] bytes = new byte[len];
			for (int i = 0; i < len; i++)
				bytes[i] = (byte) s.charAt(i);
			s = new String(bytes, "UTF-8");
		}
		return s;
	}

}
