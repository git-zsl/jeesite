<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>招聘城市管理</title>
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
		<li class="active"><a href="${ctx}/jobcity/jobCity/">招聘城市列表</a></li>
		<shiro:hasPermission name="jobcity:jobCity:edit"><li><a href="${ctx}/jobcity/jobCity/form">招聘城市添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="jobCity" action="${ctx}/jobcity/jobCity/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>拼音简称：</label>
				<form:input path="code" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>城市名称：</label>
				<form:input path="city" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编号</th>
				<th>拼音简称</th>
				<th>城市名称</th>
				<shiro:hasPermission name="jobcity:jobCity:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="jobCity">
			<tr>
				<td><a href="${ctx}/jobcity/jobCity/form?id=${jobCity.id}">
					${jobCity.id}
				</a></td>
				<td>
					${jobCity.code}
				</td>
				<td>
					${jobCity.city}
				</td>
				<shiro:hasPermission name="jobcity:jobCity:edit"><td>
    				<a href="${ctx}/jobcity/jobCity/form?id=${jobCity.id}">修改</a>
					<a href="${ctx}/jobcity/jobCity/delete?id=${jobCity.id}" onclick="return confirmx('确认要删除该招聘城市吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>