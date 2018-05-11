<%@ page import="hibernate.shop.domain.Product" %>
<%@ page import="hibernate.shop.repository.ProductRepository" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
<%@ page import="hibernate.shop.ProductType" %>
<%@ page import="hibernate.shop.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Portfolio Item - Start Bootstrap Template</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/portfolio-item.css" rel="stylesheet">

    <!-- rating -->
    <!-- default styles -->
    <link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.css" rel="stylesheet">
    <link href="css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>

    <!-- optionally if you need to use a theme, then include the theme CSS file as mentioned below -->
    <link href="themes/krajee-svg/theme.css" media="all" rel="stylesheet" type="text/css"/>

    <!-- important mandatory libraries -->
    <script src="js/jquery.js"></script>
    <script src="js/star-rating.js" type="text/javascript"></script>

    <!-- optionally if you need to use a theme, then include the theme JS file as mentioned below -->
    <script src="themes/krajee-svg/theme.js"></script>


</head>
<%
    Optional<Product> product = ProductRepository.findOneById(
            ProjectHelper.parseStringToLong(request.getParameter("productId")));
    ProductType[] values = ProductType.values();
    pageContext.setAttribute("productTypeList", values);

    if (product.isPresent()) {
        pageContext.setAttribute("product", product.get());
    } else {
        Product product1 = new Product();
        product1.setPrice(new Price());
        pageContext.setAttribute("product", product1);
    }
%>
<body>

<!-- Navigation -->
<%@include file="head.jsp" %>

<!-- Page Content -->
<div class="container">
    <form method="post" action="/editOrAddProduct" enctype="multipart/form-data">
        <div class="form-group">
            <label>Name</label>
            <input type="text" name="name" value="${product.name}">
            <input type="hidden" name="id" value="${product.id}">
        </div>
        <div class="form-group">
            <label>Product type:</label>
            <select name="productType">
                <c:forEach items="${productTypeList}" var="pd">
                    <c:if test="${pd.equals(product.productType)}">
                        <option selected="selected">${pd.name()}</option>
                    </c:if>
                    <c:if test="${! pd.equals(product.productType)}">
                        <option>${pd.name()}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Net price:</label>
            <input type="number" name="netPrice" value="${product.price.netPrice}">
        </div>
        <div class="form-group">
            <label>Gross price:</label>
            <input type="number" name="grossPrice" value="${product.price.grossPrice}">
        </div>
        <div class=" form-group">
            <label>Description:</label>
            <textarea name="description">${product.description}</textarea>
        </div>
        <div class="form-group">
            <input type="file" name="image"/>
        </div>
        <div class="form-group">
            <button type="submit">Save</button>
        </div>
    </form>

</div>
<!-- /.container -->

<!-- Footer -->
<%@include file="footer.jsp" %>

<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>
