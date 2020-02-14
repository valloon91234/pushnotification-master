package com.valloon.pushnotification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.valloon.pushnotification.model.Note;

/**
 * @author Valloon Project
 * @version 1.0 @2019-10-06
 */
public interface NoteRepository extends JpaRepository<Note, Long> {
	@Query(value = "ALTER TABLE note AUTO_INCREMENT=0", nativeQuery = true)
	public void resetAutoIncrement();

	public List<Note> findByIpAndText(String ip, String text);

}
