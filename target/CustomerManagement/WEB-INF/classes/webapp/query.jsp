<%--
  Created by IntelliJ IDEA.
  User: adamhand
  Date: 2018/12/11
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body style="text-align: center;">
<h1 align="center">客户关系管理系统</h1>
<a href="<c:url value='/add.jsp'/>">添加客户</a>
<a href="<c:url value='/CustomerServlet?method=findAll'/>">查询客户</a>
<a href="<c:url value='/query.jsp'/>">高级搜索</a>

<h3 align="center">高级搜索</h3>
<form action="<c:url value="/CustomerServlet"/>" method="get">
    <input type="hidden" name="method" value="query">
    <table border="0" align="center" width="40%" style="margin-center: 100px">
        <tr>
            <td width="40px">客户名称</td>
            <td width="40%">
                <input type="text" name="name">
            </td>
        </tr>
        <tr>
            <td>客户性别</td>
            <td>
                <select name="gender">
                    <option value="">==请选择性别==</option>
                    <option value="male">male</option>
                    <option value="female">female</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>手机</td>
            <td>
                <input type="text" name="phone"/>
            </td>
        </tr>
        <tr>
            <td>邮箱</td>
            <td>
                <input type="text" name="email"/>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>

                <input type="submit" value="搜索"/>
                <input type="reset" value="重置"/>
            </td>
        </tr>

    </table>
</form>
</body>
</html>
