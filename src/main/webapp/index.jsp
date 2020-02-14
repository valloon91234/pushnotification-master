<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="en-US">

<head>
	<title>OneSignal Notification History</title>
	<meta name="description" content="" />
	<jsp:include page="/_head.jsp" />
</head>

<body>
	<jsp:include page="/_header.jsp" />
	<div class="div-root">
		<section>
			<!-- <div class="section-header d-flex align-items-center">
				<div class="section-title">Notification History</div>
			</div> -->
			<div id="app-result" class="div-panel" v-cloak>
				<div class="div-panel-heading">
					<i class="fa fa-bars"></i>
					Result Notification History&ensp;({{count|filterComma}})
					<div class="float-right">
						<button class="btn btn-pure fa fa-sync-alt" type="button" @click="refresh" alt="Refresh"></button>
					</div>
				</div>
				<div class="div-panel-body">
					<ul class="nav nav-query align-items-center">
						<li class="nav-item div-select-limit">
							<label class="mb-0 mr-2">Rows</label>
							<select class="form-control form-control-sm" v-model="limit" @change="refresh">
								<option value="10">10</option>
								<option value="20">20</option>
								<option value="50">50</option>
							</select>
						</li>
					</ul>
					<div :class="['table-container data-loading',loading&&'loading']">
						<div>
							<table class="table table-striped table-hover table-bordered th-primary table-sm text-center">
								<thead>
									<tr>
										<th>#</th>
										<th is="th-order" :orderby="'device_id'" :order-by="orderBy" :desc="orderByDesc" :sort="sort">Device ID</th>
										<th is="th-order" :orderby="'status'" :order-by="orderBy" :desc="orderByDesc" :sort="sort">Status</th>
										<th is="th-order" :orderby="'time'" :order-by="orderBy" :desc="orderByDesc" :sort="sort">Time</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="item in list">
										<td>{{ item.id }}</td>
										<td>{{ item.device_id }}</td>
										<td v-if="item.status.toUpperCase()==='APPROVED'"><b class="text-primary">APPROVED</b></td>
										<td v-else-if="item.status.toUpperCase()==='CANCELED'"><b class="text-danger">CANCELED</b></td>
										<td v-else>{{ item.status }}</td>
										<td>{{ item.time | YYYYMMDD_HHMMSS }}</td>
									</tr>
									<tr v-if="!loading&&msg" class="tr-msg">
										<td colspan="6">{{msg}}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="clearfix">
						<page-btn-group :page-count="pageCount" :page-now="pageNow" :page-array="pageArray" :go-page="goPage"></page-btn-group>
						<p v-if="loading">Loading...</p>
						<p v-else-if="countShow>1">Showing {{offset+1}} to {{offset+countShow}} of {{count}} Notifications.</p>
						<p v-else-if="countShow>0">Showing {{offset+1}} to {{offset+countShow}} of {{count}} Notification.</p>
					</div>
				</div>
			</div>
			<div id="app-previous" class="div-panel" v-cloak>
				<div class="div-panel-heading">
					<i class="fa fa-bars"></i>
					Previous Notification History&ensp;({{count|filterComma}})
					<div class="float-right">
						<button class="btn btn-pure fa fa-sync-alt" type="button" @click="refresh" alt="Refresh"></button>
					</div>
				</div>
				<div class="div-panel-body">
					<ul class="nav nav-query align-items-center">
						<li class="nav-item div-select-limit">
							<label class="mb-0 mr-2">Rows</label>
							<select class="form-control form-control-sm" v-model="limit" @change="refresh">
								<option value="10">10</option>
								<option value="20">20</option>
								<option value="50">50</option>
							</select>
						</li>
						<li class="nav-item flex-grow-1">
						</li>
						<li class="nav-item">
							<a href="post" class="btn btn-danger">Send a Question</a>
						</li>
					</ul>
					<div :class="['table-container data-loading',loading&&'loading']">
						<div>
							<table class="table table-striped table-hover table-bordered th-primary table-sm text-center">
								<thead>
									<tr>
										<th>#</th>
										<th is="th-order" :orderby="'message'" :order-by="orderBy" :desc="orderByDesc" :sort="sort">Message</th>
										<th is="th-order" :orderby="'send_to'" :order-by="orderBy" :desc="orderByDesc" :sort="sort">Send To</th>
										<th is="th-order" :orderby="'nfc_tag_id'" :order-by="orderBy" :desc="orderByDesc" :sort="sort">NFC Tag ID</th>
										<th is="th-order" :orderby="'time_registered'" :order-by="orderBy" :desc="orderByDesc" :sort="sort">Registered Time</th>
										<th is="th-order" :orderby="'time_reserved'" :order-by="orderBy" :desc="orderByDesc" :sort="sort">Reserved Time</th>
										<th is="th-order" :orderby="'status'" :order-by="orderBy" :desc="orderByDesc" :sort="sort">Status</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="item in list">
										<td>{{ item.id }}</td>
										<td>{{ item.message }}</td>
										<td>{{ item.send_to }}</td>
										<td>{{ item.nfc_tag_id }}</td>
										<td>{{ item.time_registered | YYYYMMDD_HHMMSS }}</td>
										<td>{{ item.time_reserved | YYYYMMDD_HHMMSS }}</td>
										<td v-if="item.status.toUpperCase()==='RESERVED'"><b class="text-primary">RESERVED</b></td>
										<td v-else-if="item.status.toUpperCase()==='SENT'"><b class="text-danger">SENT</b></td>
										<td v-else>{{ item.status }}</td>
									</tr>
									<tr v-if="!loading&&msg" class="tr-msg">
										<td colspan="6">{{msg}}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="clearfix">
						<page-btn-group :page-count="pageCount" :page-now="pageNow" :page-array="pageArray" :go-page="goPage"></page-btn-group>
						<p v-if="loading">Loading...</p>
						<p v-else-if="countShow>1">Showing {{offset+1}} to {{offset+countShow}} of {{count}} Notifications.</p>
						<p v-else-if="countShow>0">Showing {{offset+1}} to {{offset+countShow}} of {{count}} Notification.</p>
					</div>
				</div>
			</div>
			<div id="app-devices" class="div-panel" v-cloak>
				<div class="div-panel-heading">
					<i class="fa fa-bars"></i>
					Device List&ensp;({{count|filterComma}})
					<div class="float-right">
						<button class="btn btn-pure fa fa-sync-alt" type="button" @click="refresh" alt="Refresh"></button>
					</div>
				</div>
				<div class="div-panel-body">
					<ul class="nav nav-query align-items-center">
						<li class="nav-item div-select-limit">
							<label class="mb-0 mr-2">Rows</label>
							<select class="form-control form-control-sm" v-model="limit" @change="refresh">
								<option value="10">10</option>
								<option value="20">20</option>
								<option value="50">50</option>
							</select>
						</li>
					</ul>
					<div :class="['table-container data-loading',loading&&'loading']">
						<div>
							<table class="table table-striped table-hover table-bordered th-primary table-sm text-center">
								<thead>
									<tr>
										<th>#</th>
										<th>Device ID</th>
										<th>Registered Time</th>
										<th>Last Activated Time</th>
									</tr>
								</thead>
								<tbody>
									<tr v-for="item in list">
										<td>{{ item.no }}</td>
										<td>{{ item.device_id }}</td>
										<td>{{ item.time_registered | YYYYMMDD_HHMMSS }}</td>
										<td>{{ item.time_last | YYYYMMDD_HHMMSS }}</td>
									</tr>
									<tr v-if="!loading&&msg" class="tr-msg">
										<td colspan="4">{{msg}}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="clearfix">
						<page-btn-group :page-count="pageCount" :page-now="pageNow" :page-array="pageArray" :go-page="goPage"></page-btn-group>
						<p v-if="loading">Loading...</p>
						<p v-else-if="countShow>1">Showing {{offset+1}} to {{offset+countShow}} of {{count}} Notifications.</p>
						<p v-else-if="countShow>0">Showing {{offset+1}} to {{offset+countShow}} of {{count}} Notification.</p>
					</div>
				</div>
			</div>
		</section>
	</div>
</body>

<jsp:include page="/_script.jsp" />
<script type="text/javascript" src="${URL_CONTEXT}/assets/js/_vue.table.js"></script>
<script type="text/javascript" src="${URL_CONTEXT}/assets/js/index.js"></script>

</html>