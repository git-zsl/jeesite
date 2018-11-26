<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业用户管理</title>
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
		<li class="active"><a href="${ctx}/business/bussinessUser/">企业用户列表</a></li>
		<shiro:hasPermission name="business:bussinessUser:edit"><li><a href="${ctx}/business/bussinessUser/form">企业用户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bussinessUser" action="${ctx}/business/bussinessUser/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>企业用户名：</label>
				<form:input path="bussinessLoginName" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>公司名称：</label>
				<form:input path="company" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>联系电话：</label>
				<form:input path="phone" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>审核：</label>
				<form:select path="checked" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bussiness_user')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>企业用户名</th>
				<th>公司名称</th>
				<th>联系电话</th>
				<th>邮箱</th>
				<th>是否通过审核</th>
				<th>积分</th>
				<th>积分截至时间</th>
				<shiro:hasPermission name="business:bussinessUser:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bussinessUser">
			<tr>
				<td><a href="${ctx}/business/bussinessUser/form?id=${bussinessUser.id}">
					${bussinessUser.bussinessLoginName}
				</a></td>
				<td>
					${bussinessUser.company}
				</td>
				<td>
					${bussinessUser.phone}
				</td>
				<td>
					${bussinessUser.email}
				</td>
				<td>
					${fns:getDictLabel(bussinessUser.checked, 'bussiness_user', '')}
				</td>
				<td>
					${bussinessUser.integral}
				</td>
				<td>
					<fmt:formatDate value="${bussinessUser.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="business:bussinessUser:edit"><td>
    				<a href="${ctx}/business/bussinessUser/form?id=${bussinessUser.id}">修改</a>
					<a href="${ctx}/business/bussinessUser/delete?id=${bussinessUser.id}" onclick="return confirmx('确认要删除该企业用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>