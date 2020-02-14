"use strict";

const USE_UTC = false;

function getNowMilliseconds() {
	var date = new Date();
	return USE_UTC ? date.getTime() : date.getTime() + date.getTimezoneOffset()
			* 60000;
}

function printNotification(msg) {
	alert(msg);
}