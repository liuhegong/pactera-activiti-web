<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../../common/taglibs.jsp"%>
<html>
<head>
    <title>流程实例列表</title>
    <script type="text/javascript">
        function createModel(){
            window.location.href="${ctx}/process/addModel";
        }
        function editModel(id){
            window.location.href="${ctx}/process/editorActiviti?modelId="+id;
        }
        function deployModel(id){
            $.ajax({
                type : "get",
                dataType : "text",
                url : "${ctx}/process/deployModel?modelId=" + id,
                success : function(result) {
                    if (result == "true") {
                        alert("部署成功！", function() {
                            window.location.href = "${ctx }/process/modelList";
                        });
                    } else {
                        alert("部署失败！", function() {
                            return;
                        });
                    }
                }
            });
        }
    </script>
</head>
<body>
<div align="center" style="top: 100px;">
    <table border="1px solid black">
        <caption>
            <div>
                <input type="button" value="创建流程" onclick="createModel()" style="float: right"/>
            </div>
        </caption>
        <thead>
            <tr>
                <td>流程KEY</td>
                <td>流程名称</td>
                <td>流程描述</td>
                <td>操作</td>
            </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="model">
            <tr>
                <td>${model.key}</td>
                <td>${model.name}</td>
                <td>
                    ${fn:split((fn:split((fn:split(model.metaInfo,',')[2]),':')[1]),'""')[0]}
                </td>
                <td>
                    <input type="button" value="编辑" onclick="editModel('${model.id}')"/>
                    <input type="button" value="部署" onclick="deployModel('${model.id}')"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
