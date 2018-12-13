<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>书籍管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		    $("#firstClassificationId").click(function(){
                var id = $("#firstClassificationId").val();
                var url = "${ctx}/book/bookManager/changeSecondClassification?firstClassId.id="+id;
                $.ajax({
                    type: "GET",
                    url: url,
                    success: function(result){
						document.getElementById("secondClassificationId").options.length=0;
                        $("#s2id_secondClassificationId .select2-chosen").html("");
                        $("#secondClassificationId").append( "<option value=''></option>");
                        $.each(result,function(index,item){
                            $("#secondClassificationId").append( "<option value='"+item.id+"'>"+item.name+"</option>");
                        });
                    }
                });
			});


			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/book/bookManager/">书籍列表</a></li>
		<li class="active"><a href="${ctx}/book/bookManager/form?id=${bookManager.id}">书籍<shiro:hasPermission name="book:bookManager:edit">${not empty bookManager.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="book:bookManager:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="bookManager" action="${ctx}/book/bookManager/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">图书名称：</label>
			<div class="controls">
				<form:input path="bookName" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作者名称：</label>
			<div class="controls">
				<form:input path="author" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">作者信息：</label>
			<div class="controls">
				<form:input path="authorIntroduce" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图书图片存储路径：</label>
			<div class="controls">
				<form:hidden id="bookImagUrl" path="bookImagUrl" htmlEscape="false" maxlength="2000" class="input-xlarge"/>
				<sys:ckfinder input="bookImagUrl" type="files" uploadPath="/book/bookManager" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图书类目：</label>
			<div class="controls">
				<form:select id="firstClassificationId" path="firstClassId" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${firstClassification}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图书分组：</label>
			<div class="controls">
				<form:select id="secondClassificationId" path="secondClassId" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${secondClassification}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">价格：</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">购买链接：</label>
			<div class="controls">
				<form:input path="buyLink" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
		<label class="control-label">是否推荐:</label>
		<div class="controls">
			<form:radiobuttons path="isRecommend" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			<span class="help-inline"></span>
		</div>
	</div>
		<div class="control-group">
			<label class="control-label">发布日期：</label>
			<div class="controls">
				<input name="publishDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${bookManager.publishDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主编荐语：</label>
			<div class="controls">
				<form:input path="editorRecommend" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图书类型：</label>
			<div class="controls">
				<form:input path="bookType" htmlEscape="false" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出版社：</label>
			<div class="controls">
				<form:input path="publishingHouse" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">语言：</label>
			<div class="controls">
				<form:input path="language" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格：</label>
			<div class="controls">
				<form:input path="specification" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出版地：</label>
			<div class="controls">
				<form:input path="placeOfPublication" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图文描述：</label>
			<div class="controls">
				<form:input path="particulars" htmlEscape="false" maxlength="2000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">点赞数：</label>
			<div class="controls">
				<form:input path="praiseNum" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">收藏数：</label>
			<div class="controls">
				<form:input path="collectNum" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">推荐数：</label>
			<div class="controls">
				<form:input path="recommendNum" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">点击量：</label>
			<div class="controls">
				<form:input path="hits" htmlEscape="false" maxlength="255" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="book:bookManager:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>