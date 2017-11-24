<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set scope="request" var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>流程编辑</title>
    <script type="text/javascript">
        function iframeBack(){
            window.location.href = "${ctx }/process/modelList";
        }
    </script>
</head>
<body>
<iframe src="${ctx }/modeler.html?modelId=${modelId}" style="width: 100%;height: 500px"></iframe>
</body>
</html>
