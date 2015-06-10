<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="header.jsp"/>
</head>
<body>

    <div class="page-header">
        <h2>
            <img src="${category.categoryImageUrl}">
            ${category.categoryName} <small>(${totalProductCount}개 상품 준비)</small>
        </h2>
    </div>

    <div id="contents"></div>
    <ul class="pager">
        <li>
            <a href="#" id="more">More...</a>
        </li>
    </ul>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#more").click(function () {
                $.get("/category/${category.categoryId}/list?page=${pageRequest.pageNumber}&size=${pageRequest.pageSize}",
                        function (data) {
                            $("#contents").append(data);
                        }
                );
            });
        });
    </script>

</body>
</html>