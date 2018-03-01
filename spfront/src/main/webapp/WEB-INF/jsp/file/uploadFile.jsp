<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My Files</title>
    <link rel="shortcut icon" type="image/x-icon" href="/images/favicon.ico" />
</head>
<body>
<form id="upload" action="/file/upload" method="post" enctype="multipart/form-data">
    <input type="hidden" name="userId" value=${user.userId}>
    <input type="file" name="file" multiple value="上传文件">
    <input type="submit" name="上传" value="上传">
</form>
<%--<form action="/file/downloadMul" method="get">
    <input type="text" name="userId" value=${user.userId}>
    <input type="submit" value="下载我的附件"/>
</form>--%>
<div>
<table class="checkbox">
    <tr>
        <th> </th>
        <th>勾选框</th>
        <th>文件名</th>
        <th>   </th>
        <th>文件预览</th>
    </tr>
    <tr></tr>
    <c:forEach items="${fileDate}" var="item" varStatus="status">
        <tr>
            <th>
            <td><input type="checkbox" id="index${status.index + 1}" value="${item.id}"></td>
            <td>${item.fileName}</td>
            </th>
            <th>  </th>
            <th><a target="_blank" href="http://39.108.74.219/shopfileserver/images/${item.relativePath}"><img style="width: 30px;height: 30px;" src='http://39.108.74.219/shopfileserver/images/${item.relativePath}'/></a><th>
        </tr>
    </c:forEach>
</table>
</div>
<form action="/file/downloadMulByUrls" method="post">
    <input type="hidden" name="url" id="loadUrl" value="">
    <input type="submit" value="下载文件">
</form>
<div>
    <div class="col-md-4 column">
        <label>共${pageUser.pages}页${pageUser.total}个图片</label>
        <label>第 ${pageUser.pageNum} 页</label>
    </div>
    <div>
        <table>
            <tr>
                <th><a href="${pageContext.request.contextPath}/file/toFile?pageNum=1"<c:if test="${pageUser.pageNum==1}"> onclick=" return document.execCommand('refresh',false,0)"</c:if>>首页</a></th>
                <th><a href="${pageContext.request.contextPath}/file/toFile?pageNum=${pageUser.prePage}"<c:if
                        test="${pageUser.pageNum==1}"> onclick=" return document.execCommand('refresh',false,0)"</c:if>>上页</a></th>
                <th><a href="${pageContext.request.contextPath}/file/toFile?pageNum=${pageUser.nextPage}" <c:if
                        test="${pageUser.pageNum==pageUser.pages}"> onclick=" return document.execCommand('refresh',false,0)"</c:if>>下页</a></th>
                <th><a href="${pageContext.request.contextPath}/file/toFile?pageNum=${pageUser.pages}" <c:if
                        test="${pageUser.pageNum==pageUser.pages}"> onclick=" return document.execCommand('refresh',false,0)"</c:if>>尾页</a></th>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript">

    $(function () {
        $("input:checkbox").each(function (i) {
            $(this).on('click', function () {
                var id = "index" + (i + 1);
                console.log(id);
                var s = $("#loadUrl").val();
                if (document.getElementById(id).checked) {
                    s = s + ";" + $(this).val();
                    $("#loadUrl").val(s);
                } else {
                    s = getStringByIndex(id, s);
                    $("#loadUrl").val(s);
                }
            });
        });

        function getStringByIndex(id, url) {
            var string = document.getElementById(id).value;
            var s = url.replace(";" + string, "");
            return s;
        }
    });

</script>
</body>
</html>
