package com.valloon.pushnotification.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * @author Valloon Project
 * @version 1.0 @2019-10-06
 */
@Entity
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	@NotNull
	public String ip;
	@NotNull
	public Timestamp time;
	@NotNull
	@Column(columnDefinition = "TEXT")
	public String text;

	public Note() {
		super();
	}

	public Note(String ip, String text) {
		this.ip = ip;
		this.time = new Timestamp(System.currentTimeMillis());
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
