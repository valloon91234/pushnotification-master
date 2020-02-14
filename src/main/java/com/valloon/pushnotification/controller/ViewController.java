package com.valloon.pushnotification.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.valloon.pushnotification.MESSAGE;
import com.valloon.pushnotification.model.PreviousHistory;
import com.valloon.pushnotification.model.ResultHistory;
import com.valloon.pushnotification.service.PushNotificationOptions;

/**
 * @author Valloon Project
 * @version 1.0 @2019-10-06
 */
@Controller
@RequestMapping("/")
public class ViewController extends BaseController {

	@GetMapping("/")
	public String getList() throws IOException {
		return "index";
	}

	@GetMapping(path = "/results.json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getResultListJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean success = false;
		String msg = null;
		long count = 0;
		JsonArray result = null;
		int offset = getInt(request, "offset", 0);
		int limit = getInt(request, "limit", 10);
		String orderby = getString(request, "orderby", true, false);
		boolean desc = 1 == getInt(request, "desc", 0);
		try {
			String qOrderBy;
			count = resultHistoryService.getCount();
			if (offset < 0)
				offset = 0;
			if (limit < 0)
				limit = 10;
			if (orderby == null) {
				orderby = "id";
				qOrderBy = "id";
			} else if (orderby.equals("id")) {
				qOrderBy = "id";
			} else if (orderby.equals("device_id")) {
				qOrderBy = "deviceId";
			} else if (orderby.equals("status")) {
				qOrderBy = "status";
			} else if (orderby.equals("time")) {
				qOrderBy = "time";
			} else {
				orderby = "id";
				qOrderBy = "id";
			}
			Page<ResultHistory> list = resultHistoryService.getList(offset, limit, qOrderBy, desc);
			result = new JsonArray();
			int i = offset;
			for (ResultHistory item : list) {
				JsonObject obj = new JsonObject();
				obj.addProperty("no", ++i);
				obj.addProperty("id", item.getId());
				obj.addProperty("device_id", item.getDeviceId());
				obj.addProperty("status", item.getStatus());
				obj.addProperty("time", item.getTime().getTime());
				result.add(obj);
			}
			success = true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			msg = MESSAGE.DB_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			msg = MESSAGE.UNKNOWN_ERROR;
		}
		JsonObject json = new JsonObject();
		json.addProperty("success", success);
		json.addProperty("msg", msg);
		json.addProperty("count", count);
		json.addProperty("offset", offset);
		json.addProperty("limit", limit);
		json.addProperty("orderBy", orderby);
		json.addProperty("orderByDesc", desc);
		json.add("result", result);
		return json.toString();
	}

	@GetMapping(path = "/previous.json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getPreviousListJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean success = false;
		String msg = null;
		long count = 0;
		JsonArray result = null;
		int offset = getInt(request, "offset", 0);
		int limit = getInt(request, "limit", 10);
		String orderby = getString(request, "orderby", true, false);
		boolean desc = 1 == getInt(request, "desc", 0);
		try {
			String qOrderBy;
			count = previousHistoryService.getCount();
			if (offset < 0)
				offset = 0;
			if (limit < 0)
				limit = 10;
			if (orderby == null) {
				orderby = "id";
				qOrderBy = "id";
			} else if (orderby.equals("id")) {
				qOrderBy = "id";
			} else if (orderby.equals("message")) {
				qOrderBy = "message";
			} else if (orderby.equals("send_to")) {
				qOrderBy = "sendTo";
			} else if (orderby.equals("nfc_tag_id")) {
				qOrderBy = "nfcTagId";
			} else if (orderby.equals("time_registered")) {
				qOrderBy = "timeRegistered";
			} else if (orderby.equals("time_reserved")) {
				qOrderBy = "timeReserved";
			} else if (orderby.equals("status")) {
				qOrderBy = "status";
			} else {
				orderby = "id";
				qOrderBy = "id";
			}
			Page<PreviousHistory> list = previousHistoryService.getList(offset, limit, qOrderBy, desc);
			result = new JsonArray();
			int i = offset;
			for (PreviousHistory item : list) {
				JsonObject obj = new JsonObject();
				obj.addProperty("no", ++i);
				obj.addProperty("id", item.getId());
				obj.addProperty("nfc_tag_id", item.getNfcTagId());
				obj.addProperty("message", item.getMessage());
				obj.addProperty("send_to", item.getSendTo());
				String status=item.getStatus();
				obj.addProperty("status", status);
				obj.addProperty("time_registered", item.getTimeRegistered().getTime());
				if(!status.isEmpty())
				obj.addProperty("time_reserved", item.getTimeReserved().getTime());
				result.add(obj);
			}
			success = true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			msg = MESSAGE.DB_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			msg = MESSAGE.UNKNOWN_ERROR;
		}
		JsonObject json = new JsonObject();
		json.addProperty("success", success);
		json.addProperty("msg", msg);
		json.addProperty("count", count);
		json.addProperty("offset", offset);
		json.addProperty("limit", limit);
		json.addProperty("orderBy", orderby);
		json.addProperty("orderByDesc", desc);
		json.add("result", result);
		return json.toString();
	}

	@GetMapping(path = "/devices.json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getDeviceListJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		boolean success = false;
		String msg = null;
		long count = 0;
		JsonArray result = null;
		int offset = getInt(request, "offset", 0);
		int limit = getInt(request, "limit", 10);
		try {
			JsonParser parser = new JsonParser();
			String apiResultString = PushNotificationOptions.viewDevices(offset, limit);
			JsonObject apiResultJson = parser.parse(apiResultString).getAsJsonObject();
			JsonArray apiResultJsonArray = apiResultJson.get("players").getAsJsonArray();
			count = apiResultJson.get("total_count").getAsLong();
			offset = apiResultJson.get("offset").getAsInt();
			limit = apiResultJson.get("limit").getAsInt();
			result = new JsonArray();
			int i = offset;
			for (JsonElement item : apiResultJsonArray) {
				JsonObject obj = new JsonObject();
				JsonObject itemObj = item.getAsJsonObject();
				obj.addProperty("no", ++i);
				obj.addProperty("device_id", itemObj.get("id").getAsString());
				obj.addProperty("time_registered", itemObj.get("created_at").getAsLong() * 1000);
				obj.addProperty("time_last", itemObj.get("last_active").getAsLong() * 1000);
				result.add(obj);
			}
			success = true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			msg = MESSAGE.DB_ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			msg = MESSAGE.UNKNOWN_ERROR;
		}
		JsonObject json = new JsonObject();
		json.addProperty("success", success);
		json.addProperty("msg", msg);
		json.addProperty("count", count);
		json.addProperty("offset", offset);
		json.addProperty("limit", limit);
		json.add("result", result);
		return json.toString();
	}

	@GetMapping("/clear")
	public String getClear() throws IOException {
		resultHistoryService.clear();
		previousHistoryService.clear();
		return "redirect:/";
	}

}
