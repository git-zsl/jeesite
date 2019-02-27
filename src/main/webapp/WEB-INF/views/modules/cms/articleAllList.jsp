<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function viewComment(href){
			top.$.jBox.open('iframe:'+href,'查看评论',$(top.document).width()-220,$(top.document).height()-120,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
					$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
					$("body", h.find("iframe").contents()).css("margin","10px");
				}
			});
			return false;
		}
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
		<li class="active"><a href="${ctx}/cms/article/allList?category.id=${article.category.id}">列表</a></li>
		<shiro:hasPermission name="classificationtree:classificationtree:view"><li><a href="${ctx}/classificationtree/classificationtree">分类管理</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="article" action="${ctx}/cms/article/allList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>栏目：</label>
		<sys:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}"
					title="栏目" url="/cms/category/treeData" module="article" notAllowSelectRoot="false" cssClass="input-small"/>
		<label>标题：</label>
		<form:select id="title" path="title" class="input-medium">
			<form:option value="" label="请选择"/>
			<form:options items="${titles}" htmlEscape="false"/>
		</form:select>&nbsp;
		<label>已置顶：</label>
		<form:select id="isTop" path="isTop" class="input-medium" style = "width:80px;">
			<form:option value="" label="请选择"/>
			<form:options items="${fns:getDictList('yes_no')}" htmlEscape="false" itemLabel="label" itemValue="value"/>
		</form:select>&nbsp;
		<label>已推荐：</label>
		<form:select id="isRecommend" path="isRecommend" class="input-medium" style = "width:80px;">
			<form:option value="" label="请选择"/>
			<form:options items="${fns:getDictList('yes_no')}" htmlEscape="false" itemLabel="label" itemValue="value"/>
		</form:select>&nbsp;
		<label>已审核：</label>
		<form:select id="isRead" path="isRead" class="input-medium" style = "width:80px;">
			<form:option value="" label="请选择"/>
			<form:options items="${fns:getDictList('yes_no')}" htmlEscape="false" itemLabel="label" itemValue="value"/>
		</form:select>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		<br/><br/>
		<label>状态：</label>
		<form:radiobuttons onclick="$('#searchForm').submit();" path="delFlag" items="${fns:getDictList('custom_article')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>栏目</th><th>标题</th><th>作者</th><th>权重</th><th>点击数</th><th>发布者</th><th>更新时间</th><th>审核人</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="article">
			<tr>
				<td><a href="javascript:" onclick="$('#categoryId').val('${article.category.id}');$('#categoryName').val('${article.category.name}');$('#searchForm').submit();return false;">${article.category.name}</a></td>
				<td><a href="${ctx}/cms/article/form?id=${article.id}" title="${article.title}">${fns:abbr(article.title,40)}</a></td>
				<td>${article.author}</td>
				<td>${article.weight}</td>
				<td>${article.hits}</td>
				<td>${article.user.name}</td>
				<td><fmt:formatDate value="${article.updateDate}" type="both"/></td>
				<td>${article.updateBy}</td>
				<td>
					<a href="${pageContext.request.contextPath}${fns:getFrontPath()}/view-${article.category.id}-${article.id}${fns:getUrlSuffix()}" target="_blank">访问</a>
					<shiro:hasPermission name="cms:article:edit">
						<c:if test="${article.category.allowComment eq '1'}"><shiro:hasPermission name="cms:comment:view">
							<a href="${ctx}/cms/comment/?module=article&contentId=${article.id}&delFlag=2" onclick="return viewComment(this.href);">评论</a>
						</shiro:hasPermission></c:if>
	    				<a href="${ctx}/cms/article/form?all=1&id=${article.id}">修改</a>
	    				<shiro:hasPermission name="cms:article:audit"><%----%>
							<a href="${ctx}/cms/article/delete?all=1&id=${article.id}${article.delFlag eq 0?'&isRe=true':'&isRe=false'}&categoryId=${article.category.id}" onclick="return confirmx('确认要${article.delFlag ne 0?'发布':'删除'}该文章吗？', this.href)" >${article.delFlag ne 0?'发布':'删除'}</a>
						</shiro:hasPermission>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>