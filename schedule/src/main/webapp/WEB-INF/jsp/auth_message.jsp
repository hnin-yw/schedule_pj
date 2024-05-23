<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Message for unauthorized users -->
<div class="card">
	<div class="card-body">
		<div class="form-group col-sm-12"
			style="height: 500px; text-align: center; padding: 50px; border: 3px;">
			<span>このページにアクセスする権限がありません。一覧画面に戻ってください。</span>
			<br><a href="${isAuthList}" style="text-underline:true;">一覧</a>
		</div>
	</div>
</div>