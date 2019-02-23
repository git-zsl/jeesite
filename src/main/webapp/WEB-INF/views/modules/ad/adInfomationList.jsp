<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>广告信息管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].parentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
						rootIds.push(data[i].parentId);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/ad/adInfomation/">广告信息列表</a></li>
		<shiro:hasPermission name="ad:adInfomation:edit"><li><a href="${ctx}/ad/adInfomation/form">广告信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="adInfomation" action="${ctx}/ad/adInfomation/" method="post" class="breadcrumb form-search">
		<ul class="ul-form">
			<li><label>广告位名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>发布时间：</label>
				<input name="releaseTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${adInfomation.releaseTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>下架时间：</label>
				<input name="soldOutTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${adInfomation.soldOutTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>发布者：</label>
				<form:input path="promulgator" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>广告位名称</th>
				<th>发布时间</th>
				<th>下架时间</th>
				<th>发布者</th>
				<th>备注信息</th>
				<shiro:hasPermission name="ad:adInfomation:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/ad/adInfomation/form?id={{row.id}}">
				{{row.name}}
			</a></td>
			<td>
				{{row.releaseTime}}
			</td>
			<td>
				{{row.soldOutTime}}
			</td>
			<td>
				{{row.promulgator}}
			</td>
			<td>
				{{row.remarks}}
			</td>
			<shiro:hasPermission name="ad:adInfomation:edit"><td>
   				<a href="${ctx}/ad/adInfomation/form?id={{row.id}}">修改</a>
				<a href="${ctx}/ad/adInfomation/delete?id={{row.id}}" onclick="return confirmx('确认要删除该广告信息及所有子广告信息吗？', this.href)">删除</a>
				<a href="${ctx}/ad/adInfomation/form?parent.id={{row.id}}">添加下级广告信息</a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>