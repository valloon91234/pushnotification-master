package com.valloon.pushnotification.service;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.valloon.pushnotification.model.PreviousHistory;
import com.valloon.pushnotification.model.ResultHistory;
import com.valloon.pushnotification.repository.ResultHistoryRepository;

/**
 * @author Valloon Project
 * @version 1.0 @2019-10-06
 */
@Service
public class ResultHistoryService {

	@Autowired
	private ResultHistoryRepository repository;

	private class OneSignalTimeTask extends TimerTask {

		private String sendTo;
		private String message;

		public OneSignalTimeTask(String sendTo, String message) {
			this.sendTo = sendTo;
			this.message = message;
		}

		@Override
		public void run() {
			if (sendTo == null)
				PushNotificationOptions.sendMessageToAllUsers(message);
			else
				PushNotificationOptions.sendMessageToUser(message, sendTo, true);
		}
	}

	public void reserve(PreviousHistory one) {
		Date date = one.getTimeReserved();
		Timer timer = new Timer();
		timer.schedule(new OneSignalTimeTask(one.getSendTo(), one.getMessage()), date);
	}

	public long getCount() {
		return repository.count();
	}

	public Page<ResultHistory> getList(int offset, int limit, String qOrderBy, boolean desc) {
		Sort sort = new Sort(desc ? Sort.Direction.DESC : Sort.Direction.ASC, qOrderBy);
		Pageable pageable = PageRequest.of(offset / limit, limit, sort);
		Page<ResultHistory> page = repository.findAll(pageable);
		return page;
	}

	public ResultHistory save(ResultHistory reply) {
		return repository.save(reply);
	}

	public void clear() {
		repository.deleteAll();
		repository.resetAutoIncrement();
	}

}
