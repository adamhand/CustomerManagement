<%--
  Created by IntelliJ IDEA.
  User: adamhand
  Date: 2018/12/11
  Time: 14:39
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

<h3 align="center" >客户列表</h3>
<table border="1" width="70%" align="center">
    <tr>
        <th>客户姓名</th>
        <th>性别</th>
        <th>手机</th>
        <th>邮箱</th>
        <th>描述</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${pb.beanList}" var="cstm">
        <tr>
            <td>${cstm.name}</td>
            <td>${cstm.gender}</td>
            <td>${cstm.phone}</td>
            <td>${cstm.email}</td>
            <td>${cstm.description}</td>
            <td>
                <a href="<c:url value='/CustomerServlet?method=preEdit&id=${cstm.id}'/> ">编辑</a>
                <a href="<c:url value='/CustomerServlet?method=deleteCustomer&id=${cstm.id}'/> ">删除</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br/>
<center>
    第${pb.pageCode}页/共${pb.totalPage}页
    <a href="${pb.url}&pc=1">首页</a>
        <c:if test="${pb.pageCode>1}">
            <a href="${pb.url}&pc=${pb.pageCode-1}">上一页</a>
        </c:if>

        <c:choose>
           <c:when test="${pb.totalPage<=10}">
               <c:set var="begin" value="1"/>
               <c:set var="end" value="${pb.totalPage}"/>
           </c:when>
           <c:otherwise>
               <c:set var="begin" value="${pb.pageCode-5}"/>
               <c:set var="end" value="${pb.pageCode+4}"/>
               &lt;%&ndash;头溢出&ndash;%&gt;
               <c:if test="${begin<1}">
                   <c:set var="begin" value="1"/>
                   <c:set var="end" value="10"/>
               </c:if>
               &lt;%&ndash;尾溢出&ndash;%&gt;
               <c:if test="${end>pb.totalPage}">
                   <c:set var="end" value="${pb.totalPage}"/>
                   <c:set var="begin" value="${pb.totalPage-9}"/>
               </c:if>
           </c:otherwise>
        </c:choose>

        <%--&lt;%&ndash;循环遍历页码列表&ndash;%&gt;--%>
        <c:forEach var="i" begin="${begin}" end="${end}">
           <c:choose>
               <c:when test="${i eq pb.pageCode}">
                   [${i}]
               </c:when>
               <c:otherwise>
                   <a href="${pb.url}&pc=${i}">[${i}]</a>
               </c:otherwise>
           </c:choose>
        </c:forEach>


        <c:if test="${pb.pageCode<pb.totalPage}">
          <a href="${pb.url}&pc=${pb.pageCode+1}">下一页</a>
        </c:if>
    <a href="${pb.url}&pc=${pb.totalPage}">尾页</a>

</center>
</body>
</html>
