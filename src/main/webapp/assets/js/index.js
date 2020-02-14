"use strict";

$("#menu-user-list").addClass("active");

var appResult = new VueTable({
	el: '#app-result',
	data: {
		URL: "results.json",
		orderByDesc: true
	},
	methods:{
	}
});

var appPrevious = new VueTable({
	el: '#app-previous',
	data: {
		URL: "previous.json",
		orderByDesc: true
	},
	methods:{
	}
});

var appDevices = new VueTable({
	el: '#app-devices',
	data: {
		URL: "devices.json"
	},
	methods:{
	}
});
