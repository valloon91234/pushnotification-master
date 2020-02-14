package com.valloon.pushnotification.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valloon.pushnotification.model.Note;
import com.valloon.pushnotification.repository.NoteRepository;

/**
 * @author Valloon Project
 * @version 1.0 @2019-10-06
 */
@Service
public class NoteService {

	@Autowired
	private NoteRepository repository;

	public long getCount() {
		return repository.count();
	}

	public List<Note> getAll() {
		List<Note> page = repository.findAll();
		return page;
	}

	public Note save(Note note) {
		if (repository.findByIpAndText(note.ip, note.text).size() > 0)
			return null;
		return repository.save(note);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}

	public void clear() {
		repository.deleteAll();
		repository.resetAutoIncrement();
	}

}
