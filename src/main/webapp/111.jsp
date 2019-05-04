<%--
  Created by IntelliJ IDEA.
  User: Thinkpad
  Date: 2019/5/4
  Time: 22:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="http://localhost:8080/zsl/filter/loginSuccess" method="post">
    loginName:<input name = "loginName"/>
    password : <input name = "password"/>
    isCompany : <input name = "isCompany"/>
    <input type="submit" value="提交" />
</form>

<form action="http://localhost:8080/zsl/a/cms/article/filter/like" method="post">
    id:<input name = "id"/>
    userId : <input name = "userId"/>
    <input type="submit" value="提交" />
</form>

<form action="http://localhost:8080/zsl/a/sys/user/filter/clearCache" method="post">
    userId : <input name = "userId"/>
    <input type="submit" value="提交" />
</form>


</body>
</html>
