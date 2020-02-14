package com.valloon.pushnotification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.valloon.pushnotification.model.ResultHistory;

/**
 * @author Valloon Project
 * @version 1.0 @2019-10-06
 */
public interface ResultHistoryRepository extends JpaRepository<ResultHistory, Long> {
	@Query(value = "ALTER TABLE result_history AUTO_INCREMENT=0", nativeQuery = true)
	public void resetAutoIncrement();
}
