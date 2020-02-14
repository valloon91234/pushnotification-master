package com.valloon.pushnotification.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.valloon.pushnotification.Config;
import com.valloon.pushnotification.model.PreviousHistory;

/**
 * @author Valloon Project
 * @version 1.0 @2019-10-06
 */
@RestController
@RequestMapping("/")
public class ConfirmController extends BaseController {

	private static final String CONFIRM_MESSAGE = "Did you confirm?";

	private Object sendNotification(String sendTo, int minutes, String nfcTagId) {
		long millisRegistered = System.currentTimeMillis();
		PreviousHistory one;
		if (minutes == -1) {
			one = new PreviousHistory(sendTo, nfcTagId, CONFIRM_MESSAGE, millisRegistered);
		} else {
			long millisReserved = millisRegistered + 60000L * minutes;
			one = new PreviousHistory(sendTo, nfcTagId, CONFIRM_MESSAGE, millisRegistered, millisReserved);
			resultHistoryService.reserve(one);
		}
		previousHistoryService.save(one);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}

	@GetMapping("/sendTo")
	public Object getSendTo(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String sendTo,
			@RequestParam("nfc") String nfcTagId) throws IOException {
		int minutes = getInt(request, "time", -1);
		if (Config.DEV_MODE)
			return sendNotification(sendTo, minutes, nfcTagId);
		response.sendError(HttpURLConnection.HTTP_BAD_METHOD);
		return null;
	}

	@PostMapping("/sendTo")
	public Object postSendTo(HttpServletRequest request, @RequestParam("id") String sendTo,
			@RequestParam("nfc") String nfcTagId) {
		int minutes = getInt(request, "time", -1);
		return sendNotification(sendTo, minutes, nfcTagId);
	}

//	@GetMapping("/sendToAll")
//	public Object getSendToAll(HttpServletResponse response, @RequestParam("time") int minutes) throws IOException {
//		if (Config.DEV_MODE)
//			return sendNotification(null, CONFIRM_MESSAGE, minutes);
//		response.sendError(HttpURLConnection.HTTP_BAD_METHOD);
//		return null;
//	}
//	
//	@PostMapping("/sendToAll")
//	public Object postSendToAll(@RequestParam("time") int minutes) {
//		return sendNotification(null, CONFIRM_MESSAGE, minutes);
//	}

}
