<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数据管理</title>
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
		<li class="active"><a href="${ctx}/area/sysChina/">数据列表</a></li>
		<shiro:hasPermission name="area:sysChina:edit"><li><a href="${ctx}/area/sysChina/form">数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysChina" action="${ctx}/area/sysChina/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>父级编号：</label>
			</li>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>父级编号</th>
				<th>名称</th>
				<th>排序</th>
				<th>区域编码</th>
				<th>区域类型</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="area:sysChina:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysChina">
			<tr>
				<td><a href="${ctx}/area/sysChina/form?id=${sysChina.id}">
					${sysChina.parent.id}
				</a></td>
				<td>
					${sysChina.name}
				</td>
				<td>
					${sysChina.sort}
				</td>
				<td>
					${sysChina.code}
				</td>
				<td>
					${sysChina.type}
				</td>
				<td>
					<fmt:formatDate value="${sysChina.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${sysChina.remarks}
				</td>
				<shiro:hasPermission name="area:sysChina:edit"><td>
    				<a href="${ctx}/area/sysChina/form?id=${sysChina.id}">修改</a>
					<a href="${ctx}/area/sysChina/delete?id=${sysChina.id}" onclick="return confirmx('确认要删除该数据吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>