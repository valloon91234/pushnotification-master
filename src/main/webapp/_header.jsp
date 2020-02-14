<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<header class="navbar navbar-dark fixed-top">
		<%-- <ul class="navbar-nav">
			<li class="nav-item">
				<a class="nav-link fa fa-bars" href="#" data-toggle="sidebar" onclick="$(body).toggleClass('sidebar');"></a>
			</li>
		</ul>
		<a class="h-100" href="${URL_CONTEXT}/">
			<img class="img-logo" src="${URL_CONTEXT}/assets/images/logo.png" />
		</a>
		<ul class="navbar-nav">
			<li class="nav-item">
				<a class="nav-link nav-link-logout d-flex align-items-center" title="Log out" href="${URL_CONTEXT}/logout">
					<span>${ME.loginID}&ensp;</span>
					<i class="fa fa-sign-out-alt"></i>
				</a>
			</li>
		</ul> --%>
		<h3 class="text-white">OneSignal Notification</h3>
		<div class="float-right">
			<a href="clear" class="btn btn-dark" onclick="!confirm('The DB will be cleared. Really?')&&event.preventDefault();">Clear DB</a>
		</div>
	</header>