"use strict";

var VueTable = Vue.extend({
	data: function () {
		return {
			PAGEBAR: 10,
			URL: "list.json",
			loading: true,
			count: 0,
			offset: 0,
			limit: 10,
			orderBy: "id",
			orderByDesc: false,
			msg: false,
			list: []
		}
	},
	computed: {
		countShow: function () {
			return this.list.length;
		},
		pageNow: function () {
			return Math.floor(this.offset / this.limit) + 1;
		},
		pageCount: function () {
			return Math.floor((this.count - 1) / this.limit) + 1;
		},
		pageFirst: function () {
			return Math.floor((this.pageNow - 1) / this.PAGEBAR) * this.PAGEBAR + 1;
		},
		pageLast: function () {
			var pageLast = this.pageFirst + this.PAGEBAR - 1;
			if (pageLast > this.pageCount) return this.pageCount;
			return pageLast;
		},
		pageArray: function () {
			var array = [];
			for (var i = this.pageFirst; i <= this.pageLast; i++)
				array.push(i);
			return array;
		}
	},
	filters: {
		filterComma: function (value) {
			if (!value) return "0";
			var result = "";
			var s = parseInt(value);
			var isNegative = s < 0;
			s = (Math.abs(s)).toString();
			var len = s.length;
			var k = 0;
			for (var i = 0; i < len; i++) {
				k++;
				if (k > 3) {
					result = "," + result;
					k = 1;
				}
				result = s.charAt(len - i - 1) + result;
			}
			return isNegative ? '-' + result : result;
		},
		YYYYMMDD: function(milliseconds){
			if(!milliseconds>0)return "";
			var date = new Date(milliseconds);
			if(USE_UTC){
				var year = date.getUTCFullYear();
				var month = (1 + date.getUTCMonth()).toString();
				month = month.length > 1 ? month : '0' + month;
				var day = date.getUTCDate().toString();
				day = day.length > 1 ? day : '0' + day;
				return year + '-' + month + '-' + day;
			}else{
				var year = date.getFullYear();
				var month = (1 + date.getMonth()).toString();
				month = month.length > 1 ? month : '0' + month;
				var day = date.getDate().toString();
				day = day.length > 1 ? day : '0' + day;
				return year + '-' + month + '-' + day;
			}
		},
		YYYYMMDD_HHMM: function(milliseconds){
			if(!milliseconds>0)return "";
			var date = new Date(milliseconds);
			if(USE_UTC){
				var year = date.getUTCFullYear();
				var month = (1 + date.getUTCMonth()).toString();
				month = month.length > 1 ? month : '0' + month;
				var day = date.getUTCDate().toString();
				day = day.length > 1 ? day : '0' + day;
				var hour = date.getUTCHours().toString();
				hour = hour.length > 1 ? hour : '0' + hour;
				var min = date.getUTCMinutes().toString();
				min = min.length > 1 ? min : '0' + min;
				return year + '-' + month + '-' + day+' '+hour+':'+min;
			}else{
				var year = date.getFullYear();
				var month = (1 + date.getMonth()).toString();
				month = month.length > 1 ? month : '0' + month;
				var day = date.getDate().toString();
				day = day.length > 1 ? day : '0' + day;
				var hour = date.getHours().toString();
				hour = hour.length > 1 ? hour : '0' + hour;
				var min = date.getMinutes().toString();
				min = min.length > 1 ? min : '0' + min;
				return year + '-' + month + '-' + day+' '+hour+':'+min;
			}
		},
		YYYYMMDD_HHMMSS: function(milliseconds){
			if(!milliseconds>0)return "";
			var date = new Date(milliseconds);
			if(USE_UTC){
				var year = date.getUTCFullYear();
				var month = (1 + date.getUTCMonth()).toString();
				month = month.length > 1 ? month : '0' + month;
				var day = date.getUTCDate().toString();
				day = day.length > 1 ? day : '0' + day;
				var hour = date.getUTCHours().toString();
				hour = hour.length > 1 ? hour : '0' + hour;
				var min = date.getUTCMinutes().toString();
				min = min.length > 1 ? min : '0' + min;
				var sec = date.getUTCSeconds().toString();
				sec = sec.length > 1 ? sec : '0' + sec;
				return year + '-' + month + '-' + day+' '+hour+':'+min+':'+sec;
			}else{
				var year = date.getFullYear();
				var month = (1 + date.getMonth()).toString();
				month = month.length > 1 ? month : '0' + month;
				var day = date.getDate().toString();
				day = day.length > 1 ? day : '0' + day;
				var hour = date.getHours().toString();
				hour = hour.length > 1 ? hour : '0' + hour;
				var min = date.getMinutes().toString();
				min = min.length > 1 ? min : '0' + min;
				var sec = date.getUTCSeconds().toString();
				sec = sec.length > 1 ? sec : '0' + sec;
				return year + '-' + month + '-' + day+' '+hour+':'+min+':'+sec;
			}
		}
	},
	components: {
		"th-order" : {
			props: ["orderBy","orderby","desc","sort"],
			template :
				'<th class="th-order" @click="sort(orderby)">\
					<slot></slot>&ensp;<i v-if="orderBy===orderby" :class="[\'fa fa-sort-amount-up\',!desc&&\'fa-flip-vertical\']"></i>\
				</th>'
		},
		"page-btn-group": {
			props: ["pageCount","pageNow","pageArray","goPage"],
			template:
				'<ul class="pagination float-right mb-0">\
					<li v-if="pageNow>1" :class="[\'page-item\',pageNow<=1&&\'disabled\']">\
						<a class="page-link" href="#" @click.prevent="goPage(1)">First</a>\
					</li>\
					<li v-if="pageNow>1" :class="[\'page-item\',pageNow<=1&&\'disabled\']">\
						<a class="page-link" href="#" @click.prevent="goPage(pageNow-1)">Prev</a>\
					</li>\
					<li v-for="page in pageArray" :class="[\'page-item\',pageNow===page&&\'active\']">\
						<a class="page-link" href="#" @click.prevent="goPage(page)">{{page}}</a>\
					</li>\
					<li v-if="pageNow<pageCount" :class="[\'page-item\',pageNow>=pageCount&&\'disabled\']">\
						<a class="page-link" href="#" @click.prevent="goPage(pageNow+1)">Next</a>\
					</li>\
					<li v-if="pageNow<pageCount" :class="[\'page-item\',pageNow>=pageCount&&\'disabled\']">\
						<a class="page-link" href="#" @click.prevent="goPage(pageCount)">Last</a>\
					</li>\
				</ul>'
		}
	},
	methods: {
		goPage: function (target) {
			var limit = this.limit;
			var offset = (target - 1) * limit;
			if (offset < 0) offset = 0;
			else if (offset > this.count) offset = Math.floor((this.count - 1) / limit) * limit;
			this.offset = offset;
			this.refresh();
		},
		sort: function (by) {
			if (by === this.orderBy) {
				this.orderByDesc = !this.orderByDesc;
			} else {
				this.orderBy = by;
				this.orderByDesc = false;
			}
			this.refresh();
		},
		refresh: function (param,callbackSuccess,callbackFail) {
			var vm = this;
			vm.loading = true;
			if(typeof param!=="object" || param instanceof Event)param={};
			param.offset=this.offset,
			param.limit=this.limit,
			param.orderby=this.orderBy,
			param.desc=this.orderByDesc ? 1 : 0
			$.getJSON(this.URL, param, (data, status, xhr) => {
				if (data.success) {
					vm.count = data.count;
					vm.offset = data.offset;
					vm.limit = data.limit;
					vm.orderBy = data.orderBy;
					vm.orderByDesc = data.orderByDesc;
					vm.list = data.result;
					vm.msg=vm.countShow<1?'No data to show.':false;
					vm.loading=false;
					typeof callbackSuccess==="function" && callbackSuccess(data);
				}else{
					vm.msg=data.msg;
					vm.list=[];
					vm.loading=false;
					typeof callbackFail==="function" && callbackFail(data);
				}
			}).fail((xhr, status, err)=>{
				vm.msg="Server connection failed. "+err;
				vm.list=[];
				vm.loading = false;
				typeof callbackFail==="function" && callbackFail();
			});
		}
	},
	mounted: function () {
		this.refresh();
	}
})
