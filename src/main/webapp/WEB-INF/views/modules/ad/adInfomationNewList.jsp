<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>广告信息管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 3});
		});
    	function updateSort() {
			loading('正在提交，请稍等...');
	    	$("#listForm").attr("action", "${ctx}/ad/adInfomation/updateSort");
	    	$("#listForm").submit();
    	}
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li class="active"><a href="${ctx}/ad/adInfomation/">广告信息列表</a></li>
	<%--<shiro:hasPermission name="ad:adInfomation:edit"><li><a href="${ctx}/ad/adInfomation/form">广告信息添加</a></li></shiro:hasPermission>--%>
</ul>
	<sys:message content="${message}"/>
	<form id="listForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed">
			<tr><th>广告位名称</th><th>发布时间</th><th>下架时间</th><th style="text-align:center;">排序</th><th>发布者</th><th>备注信息</th><th>操作</th></tr>
			<input id="categoryId" name="categoryId" type="hidden" value="${categoryId}"/>
			<c:forEach items="${list}" var="tpl">
				<tr id="${tpl.id}" pId="${tpl.parent.id ne '1'?tpl.parent.id:'0'}">
					<td><a href="${ctx}/ad/adInfomation/form?id=${tpl.id}">${tpl.name}</a></td>
					<td><fmt:formatDate value="${tpl.releaseTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatDate value="${tpl.soldOutTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td style="text-align:center;">
						<shiro:hasPermission name="cms:category:edit">
							<input type="hidden" name="ids" value="${tpl.id}"/>
							<input name="sorts" type="text" value="${tpl.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
						</shiro:hasPermission><shiro:lacksPermission name="cms:category:edit">
							${tpl.sort}
						</shiro:lacksPermission>
					</td>
					<td>${tpl.promulgator}</td>
					<td>${tpl.remarks}</td>
					<td>
						<shiro:hasPermission name="cms:category:edit">
							<a href="${ctx}/ad/adInfomation/form?id={{row.id}}">修改</a>
							<a href="${ctx}/ad/adInfomation/delete?id={{row.id}}" onclick="return confirmx('确认要删除该广告信息及所有子广告信息吗？', this.href)">删除</a>
						</shiro:hasPermission>
					</td>
				</tr>
			</c:forEach>
		</table>
		<shiro:hasPermission name="cms:category:edit"><div class="form-actions pagination-left">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保存排序" onclick="updateSort();"/>
		</div></shiro:hasPermission>
	</form>
</body>
</html>