<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="header.jsp"/>
</head>
<body>

<h2>상품속성매핑</h2>

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
    <input type="submit" value="저장">
</form>

<h2>SKU 관리</h2>

<form:form action="/sku/update" method="post" modelAttribute="skuForm">
    <table>
        <thead>
        <tr>
            <td>SKU ID</td>
            <td>Sku Name</td>
            <td>판매가</td>
            <td>소매가</td>
            <td>재고</td>
            <td>설명</td>
            <td>기본SKU</td>
            <td>상태</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${product.skuList}" varStatus="row">
            <tr>
                <td>
                    <form:input path="skuList[${row.index}].skuId" readonly="true"/>
                </td>
                <td><form:input path="skuList[${row.index}].skuName"/></td>
                <td><form:input path="skuList[${row.index}].salesPrice"/></td>
                <td><form:input path="skuList[${row.index}].retailPrice"/></td>
                <td><form:input path="skuList[${row.index}].salesStock"/></td>
                <td><form:input path="skuList[${row.index}].description"/></td>
                <td><form:input path="skuList[${row.index}].defaultSku"/></td>
                <td><form:input path="skuList[${row.index}].status"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="/product/${productId}/sku/create?shiftable=yes">SKU생성</a>
    <input type="hidden" name="productId" value="${productId}">
    <input type="submit" value="SKU저장">
</form:form>
<p>
    <a href="/catalog">상품목록으로</a>
</p>
</body>
</html>