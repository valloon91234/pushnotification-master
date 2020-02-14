<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<div class="modal fade" id="modal-confirm" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title"></h4>
					<button type="button" class="close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<p id="modal-confirm-content"></p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info" id="modal-confirm-button-ok" autofocus="autofocus">확&nbsp인</button>
					<button type="button" class="btn btn-success" data-dismiss="modal">취&nbsp소</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="modal-alert" tabindex="-1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title"></h4>
					<button type="button" class="close" id="button-modal-alert-close" data-dismiss="modal">&times;</button>
				</div>
				<div class="modal-body">
					<p id="modal-alert-content"></p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-info" data-dismiss="modal" id="modal-alert-button-ok" autofocus="autofocus">닫&nbsp기</button>
				</div>
			</div>
		</div>
	</div>
