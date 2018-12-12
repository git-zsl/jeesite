<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>自定义栏目配置管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/custom/customCategory/">自定义栏目配置列表</a></li>
		<li class="active"><a href="${ctx}/custom/customCategory/form?id=${customCategory.id}">自定义栏目配置<shiro:hasPermission name="custom:customCategory:edit">${not empty customCategory.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="custom:customCategory:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="customCategory" action="${ctx}/custom/customCategory/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">自定义栏目名称：</label>
			<div class="controls">
				<form:input path="categoryName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">自定义栏目编号：</label>
			<div class="controls">
				<form:input path="categoryId" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">栏目标志：</label>
			<div class="controls">
				<form:select path="customMark" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('custom_category')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="custom:customCategory:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>