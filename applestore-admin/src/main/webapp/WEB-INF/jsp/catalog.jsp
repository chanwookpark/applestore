<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="header.jsp"/>
</head>
<body>

<form action="/catalog" method="post" name="grid">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>#</th>
            <th>ID</th>
            <th>상품명</th>
            <th>상품상태</th>
            <th>전시 카테고리</th>
            <th>이미지(메인)</th>
            <th>기능</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${productList}" var="product" varStatus="status">
            <tr>
                <td>
                        ${status.index}
                    <input type="hidden" name="rowList[${status.index}].rowStatus" value="R"
                           class="row-status-${status.index}">
                </td>
                <td>
                    <input type="text" name="rowList[${status.index}].productId" value="${product.productId}" readonly>
                </td>
                <td><input type="text" name="rowList[${status.index}].productName" value="${product.productName}"
                           onchange="gridRowUpdate('row-status-${status.index}')"></td>
                <td>
                    <select class="form-control" name="rowList[${status.index}].productStatus"
                            onchange="gridRowUpdate('row-status-${status.index}')">
                        <c:if test="${product.status == 'READY'}">
                            <option value="READY" selected>READY</option>
                            <option value="SALES">SALES</option>
                            <option value="SOLD_OUT">SOLD_OUT</option>
                        </c:if>
                        <c:if test="${product.status == 'SALES'}">
                            <option value="READY">READY</option>
                            <option value="SALES" selected>SALES</option>
                            <option value="SOLD_OUT">SOLD_OUT</option>
                        </c:if>
                        <c:if test="${product.status == 'SOLD_OUT'}">
                            <option value="READY">READY</option>
                            <option value="SALES">SALES</option>
                            <option value="SOLD_OUT" selected>SOLD_OUT</option>
                        </c:if>
                    </select>
                </td>
                <td>
                    <input type="text" name="rowList[${status.index}].categoryName"
                           value="${product.displayCategory.categoryName}" readonly>
                </td>
                <td>
                    <input type="text" name="rowList[${status.index}].mainImageUrl" size="50"
                           value="${product.imageList[0].imageUrl}">
                </td>
                <td>
                    <a href="/product/${product.productId}/detail">상품상세관리</a>
                </td>
            </tr>
            <c:if test="${status.last == true}">
                <tr>
                    <c:set var="createIndex" value="${status.index+1}"/>
                    <td>추가<input type="hidden" name="rowList[${createIndex}].rowStatus" value="C"></td>
                    <td><input type="text" name="rowList[${createIndex}].productId" placeholder="ID입력하세요"></td>
                    <td><input type="text" name="rowList[${createIndex}].productName" placeholder="상품명도입력해요"></td>
                    <td>
                        <select class="form-control" name="rowList[${createIndex}].productStatus">
                            <option value="READY">READY</option>
                            <option value="SALES">SALES</option>
                            <option value="SOLD_OUT">SOLD_OUT</option>
                        </select>
                    </td>
                    <td>
                        <select class="form-control" name="rowList[${createIndex}].categoryName">
                            <c:forEach items="${allCategories}" var="category">
                                <option value="${category.categoryName}">${category.categoryName}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td><input type="text" name="rowList[${createIndex}].mainImageUrl" size="50"
                               placeholder="이미지URL을넣어요"></td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
    <input type="submit" value="저장">
</form>

<script>
    function gridRowUpdate(index) {
        console.log(index + ' input 변경!');
        $('.' + index).val('U');
    }
</script>

</body>
</html>