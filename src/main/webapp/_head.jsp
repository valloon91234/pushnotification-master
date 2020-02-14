<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="URL_CONTEXT" value="${pageContext.request.contextPath}" scope="application" />
	<meta charset="UTF-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
	<link rel="icon" href="${URL_CONTEXT}/assets/images/favicon.png"/>
	<link rel="stylesheet" href="${URL_CONTEXT}/lib/bootstrap4/css/bootstrap.min.css" />
	<link rel="stylesheet" href="${URL_CONTEXT}/lib/bootstrap4-datetimepicker/css/bootstrap-datetimepicker.min.css" />
	<link rel="stylesheet" href="${URL_CONTEXT}/lib/font-awesome/css/all.min.css" />
	<link rel="stylesheet" href="${URL_CONTEXT}/assets/css/_common.css" />
	<link rel="stylesheet" href="${URL_CONTEXT}/assets/css/dashboard.css" />
	<style>
		[v-cloak]{opacity: 0;}
	</style>
<%--<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="Cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="-1"/><%--
--%>