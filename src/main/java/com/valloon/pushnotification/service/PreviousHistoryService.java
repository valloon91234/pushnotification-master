package com.valloon.pushnotification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.valloon.pushnotification.model.PreviousHistory;
import com.valloon.pushnotification.repository.PreviousHistoryRepository;

/**
 * @author Valloon Project
 * @version 1.0 @2019-10-06
 */
@Service
public class PreviousHistoryService {

	@Autowired
	private PreviousHistoryRepository repository;

	public long getCount() {
		return repository.count();
	}

	public Page<PreviousHistory> getList(int offset, int limit, String qOrderBy, boolean desc) {
		Sort sort = new Sort(desc ? Sort.Direction.DESC : Sort.Direction.ASC, qOrderBy);
		Pageable pageable = PageRequest.of(offset / limit, limit, sort);
		Page<PreviousHistory> page = repository.findAll(pageable);
		return page;
	}

	public PreviousHistory save(PreviousHistory notificationHistory) {
		return repository.save(notificationHistory);
	}
	
	public void clear() {
		repository.deleteAll();
		repository.resetAutoIncrement();
	}

}
