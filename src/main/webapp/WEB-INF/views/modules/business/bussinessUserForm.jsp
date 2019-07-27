<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>用户管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#no").focus();
            $("#inputForm").validate({
                rules: {
                    loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
                },
                messages: {
                    loginName: {remote: "用户登录名已存在"},
                    confirmNewPassword: {equalTo: "输入与上面相同的密码"}
                },
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
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
    <li class="active"><a href="${ctx}/sys/user/form?id=${user.id}">用户<shiro:hasPermission
            name="sys:user:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="sys:user:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/bussinessSave" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">头像:</label>
        <div class="controls">
            <form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
            <sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100"
                          maxHeight="100"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">归属公司:</label>
        <div class="controls">
            <sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name"
                            labelValue="${user.company.name}"
                            title="公司" url="/sys/office/treeData?type=1" cssClass="required"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">归属部门:</label>
        <div class="controls">
            <sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name"
                            labelValue="${user.office.name}"
                            title="部门" url="/sys/office/treeData?type=2" cssClass="required"
                            notAllowSelectParent="true"/>
        </div>
    </div>
    <%--<div class="control-group">
        <label class="control-label">工号:</label>
        <div class="controls">
            <form:input path="no" htmlEscape="false" maxlength="50" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>--%>
    <div class="control-group">
        <label class="control-label">姓名:</label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">姓别:</label>
        <div class="controls">
            <form:select path="sex">
                <form:option value="" label="请选择"/>
                <form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">工作地:</label>
        <div class="controls">
            <form:select path="provence">
                <form:option value="" label="请选择"/>
                <form:options items="${provence}" itemLabel="name" itemValue="name" htmlEscape="false"/>
            </form:select>
            <form:select path="city">
                <form:option value="" label="请选择"/>
                <form:options items="${city}" itemLabel="name" itemValue="name" htmlEscape="false"/>
            </form:select>
            <form:select path="district">
                <form:option value="" label="请选择"/>
                <form:options items="${district}" itemLabel="name" itemValue="name" htmlEscape="false"/>
            </form:select>
        </div>
    </div>

    <div class="control-group">
        <label class="control-label">登录名:</label>
        <div class="controls">
            <input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
            <form:input path="loginName" htmlEscape="false" maxlength="50" class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">密码:</label>
        <div class="controls">
            <input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3"
                   class="${empty user.id?'required':''}"/>
            <c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
            <c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">确认密码:</label>
        <div class="controls">
            <input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50"
                   minlength="3" equalTo="#newPassword"/>
            <c:if test="${empty user.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">邮箱:</label>
        <div class="controls">
            <form:input path="email" htmlEscape="false" maxlength="100" class="email"/>
        </div>
        <label class="control-label">是否订阅每周精选:</label>
        <div class="controls">
            <form:checkbox path="subscription"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">电话:</label>
        <div class="controls">
            <form:input path="phone" htmlEscape="false" maxlength="100"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">手机:</label>
        <div class="controls">
            <form:input path="mobile" htmlEscape="false" maxlength="100"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">官网:</label>
        <div class="controls">
            <form:input path="officeLink" htmlEscape="false" maxlength="100"/>
        </div>
    </div>
    <div class="control-group">
        <div class="control-group">
            <label class="control-label">宣传绑定:</label>
        </div>
        <label class="control-label">微信二维码:</label>
            <%--  <div class="controls">
                  <form:input path="weiXinCode" htmlEscape="false" maxlength="1000"/>
              </div>--%>

        <div class="controls">
            <form:hidden id="weiXinCode" path="weiXinCode" htmlEscape="false" maxlength="255" class="input-xlarge"/>
            <sys:ckfinder input="weiXinCode" type="images" uploadPath="/sys/sysOfficeInformation" selectMultiple="false"
                          maxWidth="100"
                          maxHeight="100"/>
        </div>

        <label class="control-label">微薄:</label>
        <div class="controls">
            <form:input path="weiBo" htmlEscape="false" maxlength="1000"/>
        </div>
        <label class="control-label">知乎:</label>
        <div class="controls">
            <form:input path="zhiHu" htmlEscape="false" maxlength="1000"/>
        </div>
        <label class="control-label">豆瓣:</label>
        <div class="controls">
            <form:input path="douBan" htmlEscape="false" maxlength="1000"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">团队人数:</label>
        <div class="controls">
            <form:select path="teamSize">
                <form:option value="" label="请选择"/>
                <form:option value="1-10人" label="1-10人"/>
                <form:option value="10-20人" label="10-20人"/>
                <form:option value="20-30人" label="20-30人"/>
                <form:option value="30-50人" label="30-50人"/>
                <form:option value="50-70人" label="50-70人"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">创作环境:</label>
        <div class="controls">
            <form:hidden id="officeImage" path="officeImage" htmlEscape="false" maxlength="255" class="input-xlarge"/>
            <sys:ckfinder input="officeImage" type="images" uploadPath="/sys/sysOfficeInformation"
                          selectMultiple="false" maxWidth="100"
                          maxHeight="100" readonly="true"/>
        </div>
            <%--     <div class="controls">
                     <form:textarea path="officeImage" htmlEscape="false" rows="3" maxlength="2000" class="input-xlarge"/>
                 </div>--%>
    </div>
    <div class="control-group">
        <label class="control-label">是否允许登录:</label>
        <div class="controls">
            <form:select path="loginFlag">
                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
            <span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用户类型:</label>
        <div class="controls">
            <form:select path="userType" class="input-xlarge">
                <form:option value="" label="请选择"/>
                <form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label" itemValue="value"
                              htmlEscape="false"/>
            </form:select>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用户角色:</label>
        <div class="controls">
            <form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false"
                             class="required"/>
            <span class="help-inline"><font color="red">*</font> </span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">备注:</label>
        <div class="controls">
            <form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
        </div>
    </div>
    <c:if test="${not empty user.id}">
        <div class="control-group">
            <label class="control-label">创建时间:</label>
            <div class="controls">
                <label class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">最后登陆:</label>
            <div class="controls">
                <label class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate
                        value="${user.loginDate}" type="both" dateStyle="full"/></label>
            </div>
        </div>
    </c:if>
    <div class="form-actions">
        <shiro:hasPermission name="sys:user:edit"><input id="btnSubmit" class="btn btn-primary" type="submit"
                                                         value="保 存"/>&nbsp;</shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>