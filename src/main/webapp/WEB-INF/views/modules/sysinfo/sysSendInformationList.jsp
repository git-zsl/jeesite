<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统信息管理</title>
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
		<li class="active"><a href="${ctx}/sysinfo/sysSendInformation/">系统信息列表</a></li>
		<shiro:hasPermission name="sysinfo:sysSendInformation:edit"><li><a href="${ctx}/sysinfo/sysSendInformation/form">系统信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="sysSendInformation" action="${ctx}/sysinfo/sysSendInformation/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>标题：</label>
					<form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>接收者id：</label>
				<sys:treeselect id="user" name="user.id" value="${sysSendInformation.user.id}" labelName="user.name" labelValue="${sysSendInformation.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<%--<li><label>创建人：</label>
				<sys:treeselect id="createBy" name="createBy.id" value="${sysSendInformation.createBy.id}" labelName="" labelValue="${sysSendInformation.createBy.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>--%>
			<li><label>创建时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${sysSendInformation.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li><label>信息类型：</label>
				<form:radiobuttons path="type" items="${fns:getDictList('sys_information')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>信息类型</th>
				<th>接收者id</th>
				<th>信息内容</th>
				<th>标题</th>
				<th>用户名称</th>
				<th>创建时间</th>
				<th>到期时间</th>
				<shiro:hasPermission name="sysinfo:sysSendInformation:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sysSendInformation">
			<tr>
				<td><a href="${ctx}/sysinfo/sysSendInformation/form?id=${sysSendInformation.id}">
					${fns:getDictLabel(sysSendInformation.type, 'sys_information', '')}
				</a></td>
				<td>
					${sysSendInformation.user.name}
				</td>
				<td>
					${sysSendInformation.content}
				</td>
				<td>
					${sysSendInformation.title}
				</td>
				<td>
					${sysSendInformation.userName}
				</td>
				<td>
					<fmt:formatDate value="${sysSendInformation.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${sysSendInformation.timeOut}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sysinfo:sysSendInformation:edit"><td>
    				<%--<a href="${ctx}/sysinfo/sysSendInformation/form?id=${sysSendInformation.id}">修改</a>--%>
					<a href="${ctx}/sysinfo/sysSendInformation/delete?id=${sysSendInformation.id}" onclick="return confirmx('确认要删除该系统信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>