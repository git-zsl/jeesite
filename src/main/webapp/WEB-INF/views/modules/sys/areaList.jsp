<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>区域管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/area/">区域列表</a></li>
		<shiro:hasPermission name="sys:area:edit"><li><a href="${ctx}/sys/area/form">区域添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>区域名称</th><th>区域编码</th><th>区域类型</th><th>备注</th><shiro:hasPermission name="sys:area:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<c:forEach items="${list}" var="row">
			<tr id="${row.id}" pId="${pid}">
				<td><a href="${ctx}/sys/area/form?id=${row.id}">${row.name}</a></td>
				<td>${row.code}</td>
				<td>${dict.type}</td>
				<td>${row.remarks}</td>
				<shiro:hasPermission name="sys:area:edit"><td>
					<a href="${ctx}/sys/area/form?id=${row.id}">修改</a>
					<a href="${ctx}/sys/area/delete?id=${row.id}" onclick="return confirmx('要删除该区域及所有子区域项吗？', this.href)">删除</a>
					<a href="${ctx}/sys/area/form?parent.id=${row.id}">添加下级区域</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
	</table>
</body>
</html>