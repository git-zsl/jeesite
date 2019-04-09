<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
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
		<li class="active"><a href="${ctx}/sys/sysOfficeInformation/">信息列表</a></li>
		<shiro:hasPermission name="sys:sysOfficeInformation:edit"><li><a href="${ctx}/sys/sysOfficeInformation/form">信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysOfficeInformation" action="${ctx}/sys/sysOfficeInformation/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>机构名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>机构Id</th>
				<th>机构名称</th>
				<th>简称</th>
				<th>机构类型</th>
				<th>创建人</th>
				<th>创建时间</th>
				<shiro:hasPermission name="sys:sysOfficeInformation:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>

		<c:forEach items="${page.list}" var="sysOfficeInformation">
			<tr>
				<td><a href="${ctx}/sys/sysOfficeInformation/form?id=${sysOfficeInformation.id}">
					${sysOfficeInformation.office.name}
				</a></td>
				<td>
					${sysOfficeInformation.name}
				</td>
				<td>
					${sysOfficeInformation.shortName}
				</td>
				<td>
					${fns:getDictLabel(sysOfficeInformation.officeType, 'office_type', '')}
				</td>
				<td>
					${sysOfficeInformation.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${sysOfficeInformation.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sys:sysOfficeInformation:edit"><td>
    				<a href="${ctx}/sys/sysOfficeInformation/form?id=${sysOfficeInformation.id}">修改</a>
					<a href="${ctx}/sys/sysOfficeInformation/delete?id=${sysOfficeInformation.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>