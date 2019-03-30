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
		<li class="active"><a href="${ctx}/ad/adRequirement/">信息列表</a></li>
		<shiro:hasPermission name="ad:adRequirement:edit"><li><a href="${ctx}/ad/adRequirement/form">信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="adRequirement" action="${ctx}/ad/adRequirement/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>广告标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>工作邮箱：</label>
				<form:input path="email" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>微信id：</label>
				<form:input path="wechatId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${adRequirement.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>广告标题</th>
			<th>广告投放类型</th>
				<th>工作邮箱</th>
				<th>微信id</th>
				<th>投放周期</th>
				<th>附件内容</th>
				<th>create_by</th>
				<th>create_date</th>
				<shiro:hasPermission name="ad:adRequirement:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="adRequirement">
			<tr>
				<td><a href="${ctx}/ad/adRequirement/form?id=${adRequirement.id}">
					${adRequirement.title}
				</a></td>
				<td>
					${adRequirement.adType}
				</td>
				<td>
					${adRequirement.email}
				</td>
				<td>
					${adRequirement.wechatId}
				</td>
				<td>
					${adRequirement.period}
				</td>
				<td>
					${adRequirement.content}
				</td>
				<td>
					${adRequirement.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${adRequirement.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="ad:adRequirement:edit"><td>
    				<a href="${ctx}/ad/adRequirement/form?id=${adRequirement.id}">修改</a>
					<a href="${ctx}/ad/adRequirement/delete?id=${adRequirement.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>