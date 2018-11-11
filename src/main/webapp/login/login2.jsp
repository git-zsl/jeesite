<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#inputForm").validate({
                submitHandler: function(form){
                    loading('正在提交，请稍等...');
                    form.submit();
                },
            });
        });
    </script>
</head>
<body>
<form role="form" action="${ctx}/bus/bookManager/text">
    <div class="form-group">
        <label for="loginName">帐号</label>
        <input type="text" class="form-control" id="loginName" name="loginName"
               placeholder="请输入帐号">
    </div>
    <div class="form-group">
        <label for="password">密码</label>
        <input type="text" class="form-control" id="password" name="password"
               placeholder="请输入密码">
    </div>

    <button type="inputForm" class="btn btn-default">提交</button>
</form>

</body>
</html>
</body>
</html>