<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="list-group">
    <c:forEach var="product" items="${productList.content}">
        <a href="/product/${product.productId}" id="to-product" class="list-group-item"
                onclick="push('CATEGORY', '${category.categoryId}' + '_' + '${product.productId}')">
            <img src="${product.imageList[0].imageUrl}"/>
            <h4 class="list-group-item-heading">${product.productName}</h4>
        </a>
    </c:forEach>
</div>