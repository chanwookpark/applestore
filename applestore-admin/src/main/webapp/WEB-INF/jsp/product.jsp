<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="header.jsp"/>
</head>
<body>

<table class="table table-striped">
    <thead>
        <tr>
            <th>#</th>
            <th>ID</th>
            <th>상품명</th>
            <th>전시 카테고리</th>
            <th>이미지(메인)</th>
            <th>액션</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${productList}" var="product" varStatus="status">
            <tr>
                <td>${status.index}</td>
                <td>${product.productId}</td>
                <td>${product.productName}</td>
                <td>${product.displayCategory.categoryName}</td>
                <td>${product.imageList[0].imageUrl}</td>
                <td><button value="삭제" /></td>
            </tr>
        </c:forEach>
        <!-- 입력 form -->
        <form action="/a/product/add" method="post">
            <tr>
                <td>-</td>
                <td><input type="text" name="productId" placeholder="지금은ID입력해야해요"></td>
                <td><input type="text" name="productName"></td>
                <td>
                    <select class="form-control" name="categoryId">
                    <c:forEach items="${allCategories}" var="category">
                        <option value="${category.categoryId}">${category.categoryName}</option>
                    </c:forEach>
                    </select>
                </td>
                <td><input type="text" name="mainImageUrl"></td>
                <td><input type="submit" value="저장" ></td>
            </tr>
        </form>
    </tbody>

</table>

</body>
</html>