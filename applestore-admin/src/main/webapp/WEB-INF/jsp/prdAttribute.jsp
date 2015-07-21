<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="header.jsp"/>
</head>
<body>

<form action="/product/${productId}/attribute" method="POST">
    <table>
        <thead>
        <tr>
            <td>선택</td>
            <td>속성명</td>
            <td>레이블</td>
            <td>노출순서</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="attr" items="${product.attributeList}">
            <tr>
                <td><input type="checkbox" name="selectAttrId" value="${attr.attributeId}" checked></td>
                <td>${attr.attributeName}</td>
                <td>${attr.label}</td>
                <!-- TODO 상품 별로 설정해주어야 하지 않는가? -->
                <td>${attr.displayOrder}</td>
            </tr>
        </c:forEach>
        <c:forEach var="attr" items="${attrList}">
            <tr>
                <td><input type="checkbox" name="selectAttrId" value="${attr.attributeId}"></td>
                <td>${attr.attributeName}</td>
                <td>${attr.label}</td>
                <!-- TODO 상품 별로 설정해주어야 하지 않는가? -->
                <td>${attr.displayOrder}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a href="/catalog" type="button">상품목록으로</a>
    <input type="submit" value="저장">
</form>

</body>
</html>