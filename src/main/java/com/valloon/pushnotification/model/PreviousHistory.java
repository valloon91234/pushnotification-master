package com.valloon.pushnotification.model;

import java.sql.Timestamp;

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
public class PreviousHistory {

	public static final String STATUS_RESERVED = "RESERVED";
	public static final String STATUS_SENT = "SENT";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String sendTo;
	private String nfcTagId;
	@NotNull
	private String message;
	@NotNull
	private Timestamp timeRegistered;
	@NotNull
	private Timestamp timeReserved;
	@NotNull
	private String status;

	public PreviousHistory() {
		super();
	}

	public PreviousHistory(String sendTo, String nfcTagId, String message, long millisRegistered, long millisReserved) {
		super();
		this.sendTo = sendTo;
		this.nfcTagId = nfcTagId;
		this.message = message;
		this.timeRegistered = new Timestamp(millisRegistered);
		this.timeReserved = new Timestamp(millisReserved);
		this.status = STATUS_RESERVED;
	}

	public PreviousHistory(String sendTo, String nfcTagId, String message, long millisRegistered) {
		super();
		this.sendTo = sendTo;
		this.nfcTagId = nfcTagId;
		this.message = message;
		this.timeRegistered = new Timestamp(millisRegistered);
		this.timeReserved = new Timestamp(millisRegistered);
		this.status = "";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSendTo() {
		return sendTo;
	}

	public void setSendTo(String sendTo) {
		this.sendTo = sendTo;
	}

	public String getNfcTagId() {
		return nfcTagId;
	}

	public void setNfcTagId(String nfcTagId) {
		this.nfcTagId = nfcTagId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Timestamp getTimeRegistered() {
		return timeRegistered;
	}

	public void setTimeRegistered(Timestamp timeRegistered) {
		this.timeRegistered = timeRegistered;
	}

	public Timestamp getTimeReserved() {
		return timeReserved;
	}

	public void setTimeReserved(Timestamp timeReserved) {
		this.timeReserved = timeReserved;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
