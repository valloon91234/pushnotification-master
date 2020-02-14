package com.valloon.pushnotification.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.valloon.pushnotification.Config;
import com.valloon.pushnotification.model.ResultHistory;

/**
 * @author Valloon Project
 * @version 1.0 @2019-10-06
 */
@RestController
@RequestMapping("/")
public class ResultController extends BaseController {

	private Object result(HttpServletRequest request) {
		String deviceId = getString(request, "id", true);
		String message = getString(request, "message", true);
		resultHistoryService.save(new ResultHistory(deviceId, message));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		return result;
	}

	@GetMapping("/result")
	public Object getReply(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (Config.DEV_MODE)
			return result(request);
		response.sendError(HttpURLConnection.HTTP_BAD_METHOD);
		return null;
	}

	@PostMapping("/result")
	public Object postReply(HttpServletRequest request, Model model) throws IOException {
		return result(request);
	}

}
