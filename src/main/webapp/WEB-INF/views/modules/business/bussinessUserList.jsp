<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
        function page(n,s){
            if(n) $("#pageNo").val(n);
            if(s) $("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/sys/user/businessList");
            $("#searchForm").submit();
            return false;
        }
	</script>
</head>
<body>
<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/businessList" method="post" class="breadcrumb form-search ">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
	<ul class="ul-form">
		<li><label>企业名称：</label><sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
												title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/></li>
		<li><label>姓名：</label>
			<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
		</li>
		<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>

		<li><label>状态：</label>
			<form:radiobuttons onclick="$('#searchForm').submit();" path="delFlag" items="${fns:getDictList('is_pass')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		</li>
		<li class="clearfix"></li>
	</ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead><tr><th class="sort-column login_name">企业名称</th><th class="sort-column login_name">登录名</th><th class="sort-column name">姓名</th><th>电话</th><th>手机</th><%--<th>角色</th> --%><shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
	<tbody>
	<c:forEach items="${page.list}" var="user">
		<tr>
			<td>${user.office.name}</td>
			<td><a href="${ctx}/sys/user/bussinessForm?id=${user.id}">${user.loginName}</a></td>
			<td>${user.name}</td>
			<td>${user.phone}</td>
			<td>${user.mobile}</td><%--
				<td>${user.roleNames}</td> --%>
			<shiro:hasPermission name="sys:user:edit"><td>
				<a href="${ctx}/sys/user/bussinessDelete?id=${user.id}&delFlag=${user.delFlag eq 0?'1': user.delFlag eq 1?'2':'0'}" onclick="return confirmx('确认要${user.delFlag eq 0?'删除吗？': user.delFlag eq 1?'设置为待审核吗？':'设置为审核通过吗？'}', this.href)">${user.delFlag eq 0?'删除': user.delFlag eq 1?'待审核':'审核通过'}</a>
				<a href="${ctx}/sys/user/bussinessDelete?id=${user.id}&isSendEmail=true&delFlag=${user.delFlag eq 0?'1': user.delFlag eq 1?'2':'1'}" onclick="return confirmx('确认要${user.delFlag eq 0?'删除吗？': user.delFlag eq 1?'设置为待审核吗？':'设置为审核失败吗？'}', this.href)">${user.delFlag eq 0?'': user.delFlag eq 1?'':'审核失败'}</a>
				<a href="${ctx}/sys/user/bussinessForm?id=${user.id}">修改信息</a>
			</td></shiro:hasPermission>
		</tr>
	</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>