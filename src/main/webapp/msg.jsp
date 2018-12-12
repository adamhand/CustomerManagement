<%--
  Created by IntelliJ IDEA.
  User: adamhand
  Date: 2018/12/11
  Time: 14:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body style="text-align: center;">
<h1 align="center">客户关系管理系统</h1>
<a href="<c:url value='/add.jsp'/>">添加客户</a>
<a href="<c:url value='/CustomerServlet?method=findAll'/>">查询客户</a>
<a href="<c:url value='/query.jsp'/>">高级搜索</a>

<h2 style="color:green;" align="center">${msg}</h2>
</body>
</html>
