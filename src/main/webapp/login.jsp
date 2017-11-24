<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set scope="request" var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta charset="utf-8">
    <style type="text/css">
        .line{display: inline-block;width: 100px;text-align: right}
    </style>
</head>
<body>
<div align="center"><a href="${ctx}/process/modelList">流程列表</a> </div>
<div align="center">
    <form action="${ctx}/process/create" method="get">
        <p>
            <span class="line">key:</span>
            <input type="text" id="key" name="key"/>
        </p>
        <p>
            <span class="line">流程名称:</span>
            <input type="text" id="name" name="name"/><br>
        </p>
        <p>
            <span class="line">流程描述:</span>
            <input type="text" id="description" name="description"/><br>
        </p>
        <p>
            <span class="line">流程类型:</span>
            <input type="text" id="category" name="category"/><br>
        </p>
        <input type="submit" value="提交">
    </form>
</div>
</body>
</html>
