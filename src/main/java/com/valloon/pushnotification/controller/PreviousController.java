package com.valloon.pushnotification.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.valloon.pushnotification.MESSAGE;
import com.valloon.pushnotification.model.PreviousHistory;
import com.valloon.pushnotification.service.PushNotificationOptions;

/**
 * @author Valloon Project
 * @version 1.0 @2019-10-06
 */
@Controller
@RequestMapping("/")
public class PreviousController extends BaseController {

	@GetMapping("/post")
	public String getSend() throws IOException {
		return "post";
	}

	@PostMapping("/post")
	public String postSend(HttpServletRequest request, Model model) throws IOException {
		String sendTo = getString(request, "id", true);
		String message = getString(request, "message", true);
		long millisRegistered = System.currentTimeMillis();
		PreviousHistory one = new PreviousHistory(sendTo, null, message, millisRegistered, millisRegistered);
		one.setStatus(PreviousHistory.STATUS_SENT);
		PushNotificationOptions.sendMessageToUser(message, sendTo, false);
		previousHistoryService.save(one);
		model.addAttribute("msg", MESSAGE.QUESTION_SENT);
		return "post";
	}

}
