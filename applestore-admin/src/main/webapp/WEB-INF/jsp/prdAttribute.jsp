<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="header.jsp"/>
</head>
<body>

<h2>상품 기본 정보 관리(기본 SKU)</h2>
<form:form action="/product/main" method="post" modelAttribute="productMainForm">
    <form:hidden path="productId"/>
    <form:hidden path="categoryId"/>
    <form:hidden path="skuId"/>

    <p><span>상품명</span> <form:input path="productName" size="50"/></p>

    <p><span>상품상태</span> <form:input path="status" size="50"/></p>

    <p>
        <span>전시 카테고리</span> <form:input path="categoryName" size="50"/>
    </p>

    <p><span>기본SKU명</span> <form:input path="skuName" size="50"/></p>

    <p><span>판매가격</span> <form:input path="salesPrice" size="50"/></p>

    <p><span>소매가격</span> <form:input path="retailPrice" size="50"/></p>

    <p><span>판매재고</span> <form:input path="salesStock" size="50"/></p>

    <p><span>기술서</span> <form:textarea path="description" cols="50"/></p>

    <input type="submit" value="저장">
</form:form>

<h2>상품속성매핑</h2>

<form action="/product/${product.productId}/attribute" method="POST">
    <table>
        <thead>
        <tr>
            <td>선택</td>
            <td>속성명</td>
            <td>레이블</td>
            <td>속성값 갯수</td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="attr" items="${product.attributeList}">
            <tr>
                <td><input type="checkbox" name="selectAttrId" value="${attr.attributeId}" checked></td>
                <td>${attr.attributeName}</td>
                <td>${attr.label}</td>
                <td>${attr.valueSize}개</td>
            </tr>
        </c:forEach>
        <c:forEach var="attr" items="${attrList}">
            <tr>
                <td><input type="checkbox" name="selectAttrId" value="${attr.attributeId}"></td>
                <td>${attr.attributeName}</td>
                <td>${attr.label}</td>
                <td>${attr.valueSize}개</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <input type="submit" value="저장">
</form>

<h2>추가 SKU 관리</h2>

<form:form action="/sku/update" method="post" modelAttribute="skuForm">
    <table>
        <thead>
        <tr>
            <td>SKU ID</td>
            <td>Sku Name</td>
            <td>Label</td>
            <td>판매가</td>
            <td>소매가</td>
            <td>재고</td>
            <td>진열순서</td>
            <td>설명</td>
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
                <td><form:input path="skuList[${row.index}].label"/></td>
                <td><form:input path="skuList[${row.index}].salesPrice"/></td>
                <td><form:input path="skuList[${row.index}].retailPrice"/></td>
                <td><form:input path="skuList[${row.index}].salesStock"/></td>
                <td><form:input path="skuList[${row.index}].displayOrder"/></td>
                <td><form:textarea path="skuList[${row.index}].description"/></td>
                <td><form:input path="skuList[${row.index}].status"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <a href="/product/${product.productId}/sku/create?shiftable=yes">SKU생성</a>
    <input type="hidden" name="productId" value="${product.productId}">
    <input type="submit" value="SKU저장">
</form:form>
<p>
    <a href="/catalog">상품목록으로</a>
</p>
</body>
</html>