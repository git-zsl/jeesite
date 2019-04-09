<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
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
		<li><a href="${ctx}/sys/sysOfficeInformation/">信息列表${message}</a></li>
		<li class="active"><a href="${ctx}/sys/sysOfficeInformation/form?id=${sysOfficeInformation.id}">信息<shiro:hasPermission name="sys:sysOfficeInformation:edit">${not empty sysOfficeInformation.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysOfficeInformation:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sysOfficeInformation" action="${ctx}/sys/sysOfficeInformation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">机构Id：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${sysOfficeInformation.office.id}" labelName="office.name" labelValue="${sysOfficeInformation.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">简称：</label>
			<div class="controls">
				<form:input path="shortName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构类型：</label>
			<div class="controls">
				<form:radiobuttons path="officeType" items="${fns:getDictList('office_type')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">执照：</label>
			<div class="controls">
				<form:hidden id="image" path="image" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
				<sys:ckfinder input="image" type="files" uploadPath="/sys/sysOfficeInformation" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构相关文件：</label>
			<div class="controls">
				<form:hidden id="file" path="file" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
				<sys:ckfinder input="file" type="files" uploadPath="/sys/sysOfficeInformation" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:sysOfficeInformation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>