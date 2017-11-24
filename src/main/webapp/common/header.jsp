<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <title><sitemesh:write property="title"/></title>
    <meta charset="UTF-8" name="viewport" http-equiv="X-UA-Compatible" content="IE=edge" content="width=device-width, initial-scale=1.0">
    <script src="${ctx }/common/js/jquery-1.9.1.min.js"></script>
    <%--<link rel="stylesheet" type="text/css" href="${ctx }/css/component.css">--%>

    <sitemesh:write property="head"/>
</head>
<body>
<sitemesh:write property="body"/>
</body>
</html>
