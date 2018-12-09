<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分类关系管理</title>
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
		<li class="active"><a href="${ctx}/classrelation/classificationRelation/">分类关系列表</a></li>
		<shiro:hasPermission name="classrelation:classificationRelation:edit"><li><a href="${ctx}/classrelation/classificationRelation/form">分类关系添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="classificationRelation" action="${ctx}/classrelation/classificationRelation/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>first_classification_id：</label>
				<form:input path="firstClassificationId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>second_classification_id：</label>
				<form:input path="secondClassificationId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>first_classification_id</th>
				<th>second_classification_id</th>
				<shiro:hasPermission name="classrelation:classificationRelation:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="classificationRelation">
			<tr>
				<td><a href="${ctx}/classrelation/classificationRelation/form?id=${classificationRelation.id}">
					${classificationRelation.firstClassificationId}
				</a></td>
				<td>
					${classificationRelation.secondClassificationId}
				</td>
				<shiro:hasPermission name="classrelation:classificationRelation:edit"><td>
    				<a href="${ctx}/classrelation/classificationRelation/form?id=${classificationRelation.id}">修改</a>
					<a href="${ctx}/classrelation/classificationRelation/delete?id=${classificationRelation.id}" onclick="return confirmx('确认要删除该分类关系吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>