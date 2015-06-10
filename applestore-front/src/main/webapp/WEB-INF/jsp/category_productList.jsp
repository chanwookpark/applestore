<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="header.jsp"/>
</head>
<body>
    <div class="list-group">
        <c:forEach var="product" items="${productList.content}">
            <a href="/product/${product.productId}" class="list-group-item">
                <img src="${product.imageList[0].imageUrl}"/>
                <h4 class="list-group-item-heading">${product.productName}</h4>
            </a>
        </c:forEach>
    </div>
</body>
</html>