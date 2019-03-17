<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>广告信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")){
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
		<li><a href="${ctx}/ad/adInfomation/">广告信息列表</a></li>
		<li class="active"><a href="${ctx}/ad/adInfomation/form?id=${adInfomation.id}&parent.id=${adInfomationparent.id}">广告信息<shiro:hasPermission name="ad:adInfomation:edit">${not empty adInfomation.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="ad:adInfomation:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="adInfomation" action="${ctx}/ad/adInfomation/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="articleId"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">广告位置:</label>
			<div class="controls">
				<sys:treeselect id="parent" name="parent.id" value="${adInfomation.parent.id}" labelName="parent.name" labelValue="${adInfomation.parent.name}"
					title="父级编号" url="/ad/adInfomation/treeData" extId="${adInfomation.id}" cssClass="" allowClear="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">网页位置 :</label>
			<div class="controls">
				<sys:treeselect id="category" name="category.id" value="${adInfomation.category.id}" labelName="category.name" labelValue="${adInfomation.category.name}"
								title="栏目" url="/cms/category/treeData?isShowHome=1" extId="${category.id}"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">广告标题：</label>
			<div class="controls">
				<form:input path="name" readonly="true" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">链接：</label>
			<div class="controls">
				<form:input path="link" readonly="true" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序：</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">缩略图:</label>
			<div class="controls">
				<form:input path="image" readonly="true" htmlEscape="false" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布时间：</label>
			<div class="controls">
				<input name="releaseTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${adInfomation.releaseTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">下架时间：</label>
			<div class="controls">
				<input name="soldOutTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${adInfomation.soldOutTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">
			<label class="control-label">发布者：</label>
			<div class="controls">
				<form:input path="promulgator" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>--%>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="2000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="ad:adInfomation:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>