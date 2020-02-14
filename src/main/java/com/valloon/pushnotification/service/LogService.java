package com.valloon.pushnotification.service;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.valloon.pushnotification.Config;

/**
 * @author Valloon Project
 * @version 1.0 @2019-10-06
 */
@Transactional
public class LogService {

	public static final int LEVEL_NONE = 0;
	public static final int LEVEL_ERROR = 1;
	public static final int LEVEL_WARN = 2;
	public static final int LEVEL_INFO = 3;
	public static final int LEVEL_DEBUG = 4;

	private LogService() {
	}

	public static String getTimeString() {
		SimpleDateFormat formatter = new SimpleDateFormat("[yyyy/MM/dd HH:mm:ss]   ");
		return formatter.format(new Date());
	}

	public static void printConsole(String log, int level) {
		if (Config.DEBUG >= level)
			System.out.println(log);
	}

	public static void printConsole(HttpServletRequest request, String msg, int level) {
		String clientIP = request.getRemoteAddr();
		String contentPath = request.getContextPath();
		String servletPath = request.getServletPath();
		String query = request.getQueryString();
		if (query == null)
			query = "";
		else
			try {
				query = "?" + URLDecoder.decode(query, "UTF-8");
			} catch (Exception e) {
				query = "?" + query;
			}
		String text = getTimeString() + clientIP + "   " + contentPath + servletPath + query;
		if (msg != null)
			text += "   " + msg;
		printConsole(text, level);
	}

	public static void log(HttpServletRequest request, Exception e) {
		if (Config.DEBUG >= LEVEL_ERROR) {
			printConsole(request, null, LEVEL_ERROR);
			e.printStackTrace();
		}
//		String stackTraceString = null;
//		if (e != null) {
//			stackTraceString = e.toString() + "\r\n";
//			StackTraceElement[] stacks = e.getStackTrace();
//			for (StackTraceElement stack : stacks)
//				stackTraceString += "\tat " + stack.toString() + "\r\n";
//			if (stackTraceString.length() > 2000)
//				stackTraceString = stackTraceString.substring(0, 1990) + " ...";
//		}
//		log(request, user, type, request_detail, message, stackTraceString);
	}

//	private LogDao logDao = new LogDao();
//
//	public static BBSLogger getInstance() {
//		return instance;
//	}
//
//	public static String getUrl(HttpServletRequest request) {
//		String content = request.getContextPath();
//		String servlet = request.getServletPath();
//		String query = request.getQueryString();
//		String result = request.getServerName() + ":" + request.getServerPort() + content + servlet;
//		if (query != null)
//			result += "?" + query;
//		return result;
//	}
//
//
//	public void log(HttpServletRequest request, UserModel user, int type, String request_detail, String message,
//			String note) {
//		if (Setting.DEVELOP_MODE)
//			if (note.length() < 100)
//				System.out.println("type = " + type + " , request_detail = " + request_detail + " , message = "
//						+ message + " , note = " + note);
//		String ip = null;
//		String url = null;
//		String request_header = null;
//		if (request != null) {
//			ip = request.getRemoteAddr();
//			url = getUrl(request);
//			request_header = request.getHeader("User-Agent");
//		}
//		try {
//			if (url != null && url.length() > 2000)
//				url = url.substring(0, 1990) + " ...";
//			if (request_header != null && request_header.length() > 2000)
//				request_header = request_header.substring(0, 1990) + " ...";
//			if (request_detail == null) {
//				if (url != null)
//					request_detail = URLDecoder.decode(url, "UTF-8");
//			} else if (request_detail.length() > 2000)
//				request_detail = request_detail.substring(0, 1990) + " ...";
//			if (message != null && message.length() > 2000)
//				message = message.substring(0, 1990) + " ...";
//			if (note != null && note.length() > 2000)
//				note = note.substring(0, 1990) + " ...";
//			LogModel model;
//			if (user == null)
//				model = new LogModel(0, null, ip, url, type, request_header, request_detail, message, note);
//			else
//				model = new LogModel(user.userID, user.userString, user.userIP, url, type, request_header,
//						request_detail, message, note);
//			logDao.insert(model);
//		} catch (Exception e) {
//			if (Setting.DEVELOP_MODE)
//				e.printStackTrace();
//			else
//				System.out.println(">>> bbs - log failed ! >>> " + e.toString());
//		}
//	}
//
//	public int countAll() throws PersistenceException {
//		return logDao.countAll();
//	}
//
//	public List<LogModel> list(int offset, int limit, int type) throws PersistenceException {
//		LogModel model = new LogModel();
//		model.offset = offset;
//		model.limit = limit;
//		model.type = type;
//		return logDao.list(model);
//	}
//
//	public int delete(int id) throws PersistenceException {
//		return logDao.delete(id);
//	}
//
//	public int deleteByType(int type) throws PersistenceException {
//		return logDao.deleteByType(type);
//	}
//
//	public int empty() throws PersistenceException {
//		return logDao.empty();
//	}

}
