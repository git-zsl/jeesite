<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>栏目数量管理</title>
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
		<li class="active"><a href="${ctx}/crn/userCategoryNum/">栏目数量列表</a></li>
		<shiro:hasPermission name="crn:userCategoryNum:edit"><li><a href="${ctx}/crn/userCategoryNum/form">栏目数量添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="userCategoryNum" action="${ctx}/crn/userCategoryNum/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户id：</label>
				<sys:treeselect id="user" name="user.id" value="${userCategoryNum.user.id}" labelName="user.name" labelValue="${userCategoryNum.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>栏目id：</label>
				<form:select path="categoryId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>用户id</th>
				<th>栏目id</th>
				<th>允许创建数量</th>
				<th>拥有数量</th>
				<shiro:hasPermission name="crn:userCategoryNum:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="userCategoryNum">
			<tr>
				<td><a href="${ctx}/crn/userCategoryNum/form?id=${userCategoryNum.id}">
					${userCategoryNum.user.name}
				</a></td>
				<td>
					${fns:getDictLabel(userCategoryNum.categoryId, '', '')}
				</td>
				<td>
					${userCategoryNum.createNum}
				</td>
				<td>
					${userCategoryNum.currentNum}
				</td>
				<shiro:hasPermission name="crn:userCategoryNum:edit"><td>
    				<a href="${ctx}/crn/userCategoryNum/form?id=${userCategoryNum.id}">修改</a>
					<a href="${ctx}/crn/userCategoryNum/delete?id=${userCategoryNum.id}" onclick="return confirmx('确认要删除该栏目数量吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>