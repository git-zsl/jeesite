<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>栏目数量管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
            $(".valid").change(function(){
                alert("XXXXX");
                if($("#userId").val()){
                    $("#selectCategory").attr("disabled","false");
				}
			});
			$("#selectCategory").change(function(){
			    var categoryId = $("#selectCategory").val();
			    var createBy = $("user").val();
			    var url = "${ctx}/crn/userCategoryNum/ownNum?id="+categoryId+"&createBy="+createBy;
                $.ajax({
                    type: "GET",
                    url: url,
                    success: function(msg){
                        $("#num").val(msg);
                        $("#num").attr("readonly","false");
                    }
                });
			})
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/crn/userCategoryNum/">栏目数量列表</a></li>
		<li class="active"><a href="${ctx}/crn/userCategoryNum/form?id=${userCategoryNum.id}">栏目数量<shiro:hasPermission name="crn:userCategoryNum:edit">${not empty userCategoryNum.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="crn:userCategoryNum:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="userCategoryNum" action="${ctx}/crn/userCategoryNum/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">用户id：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${userCategoryNum.user.id}" labelName="user.name" labelValue="${userCategoryNum.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">栏目id：</label>
			<div class="controls">
				<form:select id="selectCategory" path="categoryId" disabled="true" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${categorys}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">允许创建数量：</label>
			<div class="controls">
				<form:input path="createNum" htmlEscape="false" maxlength="11" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">拥有数量：</label>
			<div class="controls">
				<form:input id="num" path="currentNum" htmlEscape="false" maxlength="11" value="${num}" readonly="true" class="input-xlarge  digits"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="crn:userCategoryNum:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>