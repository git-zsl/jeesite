<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/peruser/personalUser/">个人用户列表</a></li>
		<shiro:hasPermission name="peruser:personalUser:edit"><li><a href="${ctx}/peruser/personalUser/form">个人用户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="personalUser" action="${ctx}/peruser/personalUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户名：</label>
				<form:input path="loginName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>联系电话：</label>
				<form:input path="phone" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>是否黑名单：</label>
				<form:select path="isBlacklist" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户名</th>
				<th>联系电话</th>
				<th>邮箱</th>
				<th>姓名</th>
				<th>是否黑名单</th>
				<shiro:hasPermission name="peruser:personalUser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="personalUser">
			<tr>
				<td><a href="${ctx}/peruser/personalUser/form?id=${personalUser.id}">
					${personalUser.loginName}
				</a></td>
				<td>
					${personalUser.phone}
				</td>
				<td>
					${personalUser.email}
				</td>
				<td>
					${personalUser.userName}
				</td>
				<td>
					${fns:getDictLabel(personalUser.isBlacklist, 'yes_no', '')}
				</td>
				<shiro:hasPermission name="peruser:personalUser:edit"><td>
    				<a href="${ctx}/peruser/personalUser/form?id=${personalUser.id}">修改</a>
					<a href="${ctx}/peruser/personalUser/delete?id=${personalUser.id}" onclick="return confirmx('确认要删除该个人用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>