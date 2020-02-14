<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!doctype html>
<html lang="en-US">

<head>
	<title>Send a Question</title>
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
			<div class="div-panel container mx-auto p-0">
				<div class="div-panel-heading">
					<i class="fa fa-envelope"></i>
					Send a Question
				</div>
				<div class="div-panel-body">
					<form method="POST">
						<input type="text" class="form-control mb-3" name="id" placeholder="Send To..." required="required" autofocus="autofocus"/>
						<textarea class="form-control mb-3" name="message" placeholder="Question..." required="required" style="height: 240px;"></textarea>
						<button type="submit" class="btn btn-info"><i class="fa fa-paper-plane"></i>&ensp;Send</button>&emsp;
						<a href="./" class="btn btn-success"><i class="fa fa-arrow-circle-left"></i>&ensp;Back</a>
					</form>
				</div>
			</div>
		</section>
	</div>
</body>

<jsp:include page="/_script.jsp" />
<script type="text/javascript" src="${URL_CONTEXT}/assets/js/_vue.table.js"></script>
<script type="text/javascript" src="${URL_CONTEXT}/assets/js/list.js"></script>

<c:if test="${msg!=null}">
<script type="text/javascript">
	printNotification("${msg}");
	location.href="/";
</script>
</c:if>

</html>