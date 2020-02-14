package com.valloon.pushnotification.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * @author Valloon Project
 * @version 1.0 @2019-10-06
 */
public class PushNotificationOptions {

	public static final String REST_API_KEY = "OTE0ZmYwNzQtZWM4Yy00NmVmLThmMmUtNTczZDkxMGY0Njc4";
	public static final String APP_ID = "308fa56a-c0fb-41d1-bcaf-816656cfdab5";

	public static void sendMessageToAllUsers(String message) {
		try {
			String jsonResponse;

			URL url = new URL("https://onesignal.com/api/v1/notifications");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);

			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestProperty("Authorization", "Basic " + REST_API_KEY);// REST API
			con.setRequestMethod("POST");

			String strJsonBody = "{" + "\"app_id\": \"" + APP_ID + "\"," + "\"included_segments\": [\"All\"],"
					+ "\"data\": {\"foo\": \"bar\"}," + "\"contents\": {\"en\": \"" + message + "\"}" + "}";

			byte[] sendBytes = strJsonBody.getBytes("UTF-8");
			con.setFixedLengthStreamingMode(sendBytes.length);

			OutputStream outputStream = con.getOutputStream();
			outputStream.write(sendBytes);

			int httpResponse = con.getResponseCode();

			jsonResponse = mountResponseRequest(con, httpResponse);
			System.out.println("jsonResponse:\n" + jsonResponse);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static String mountResponseRequest(HttpURLConnection con, int httpResponse) throws IOException {
		String jsonResponse;
		if (httpResponse >= HttpURLConnection.HTTP_OK && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
			Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
			jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			scanner.close();
		} else {
			Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
			jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
			scanner.close();
		}
		return jsonResponse;
	}

	public static void sendMessageToUser(String message, String userId, boolean buttonOkCancel) {
		try {
			String jsonResponse;

			URL url = new URL("https://onesignal.com/api/v1/notifications");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setUseCaches(false);
			con.setDoOutput(true);
			con.setDoInput(true);

			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestProperty("Authorization", REST_API_KEY);
			con.setRequestMethod("POST");
			String strJsonBody;
			if (buttonOkCancel) {
				strJsonBody = "{" + "\"app_id\": \"" + APP_ID + "\"," + "\"include_player_ids\": [\"" + userId + "\"],"
						+ "\"data\": {\"foo\": \"bar\"}," + "\"contents\": {\"en\": \"" + message + "\"},"
						+ "\"buttons\": [{\"id\": \"cancel\", \"text\": \"Cancel\"}]" + "}";
			} else {
				strJsonBody = "{" + "\"app_id\": \"" + APP_ID + "\"," + "\"include_player_ids\": [\"" + userId + "\"],"
						+ "\"data\": {\"foo\": \"bar\"}," + "\"contents\": {\"en\": \"" + message + "\"}" + "}";
			}

			byte[] sendBytes = strJsonBody.getBytes("UTF-8");
			con.setFixedLengthStreamingMode(sendBytes.length);

			OutputStream outputStream = con.getOutputStream();
			outputStream.write(sendBytes);

			int httpResponse = con.getResponseCode();

			jsonResponse = mountResponseRequest(con, httpResponse);
			System.out.println("jsonResponse:\n" + jsonResponse);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static String viewDevices(int offset, int limit) throws IOException {
		String jsonResponse;

		URL url = new URL(
				"https://onesignal.com/api/v1/players?app_id=" + APP_ID + "&limit=" + limit + "&offset=" + offset);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setUseCaches(false);
		con.setDoOutput(true);
		con.setDoInput(true);

		con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		con.setRequestProperty("Authorization", "Basic " + REST_API_KEY);
		con.setRequestMethod("GET");

		int httpResponse = con.getResponseCode();

		jsonResponse = mountResponseRequest(con, httpResponse);
		return jsonResponse;
	}
}
