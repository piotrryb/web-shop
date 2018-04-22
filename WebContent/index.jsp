<%@ page import="hibernate.shop.product.ProductRepository" %>
<%@ page import="hibernate.shop.product.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Shop Homepage - Start Bootstrap Template</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/shop-homepage.css" rel="stylesheet">

</head>
<%
    List<Product> allProduct = ProductRepository.findAll();
    pageContext.setAttribute("allProduct", allProduct);

    if (request.getParameter("isSuccessLogout") != null && request.getParameter("isSuccessLogout").equals("true")) {
        pageContext.setAttribute("info", "You are logged out!");
    }
%>
<body>

<!-- Navigation -->
<%@include file="head.jsp" %>

<c:if test="${info != null && info.length() > 0}">
    <div class="alert alert-danger" role="alert">${info}</div>
</c:if>
<!-- Page Content -->
<div class="container">

    <div class="row">

        <%@include file="leftMenu.jsp" %>
        <!-- /.col-lg-3 -->

        <div class="col-lg-9">

            <div id="carouselExampleIndicators" class="carousel slide my-4" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                </ol>
                <div class="carousel-inner" role="listbox">
                    <div class="carousel-item active">
                        <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="First slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="Second slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block img-fluid" src="http://placehold.it/900x350" alt="Third slide">
                    </div>
                </div>
                <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="sr-only">Previous</span>
                </a>
                <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="sr-only">Next</span>
                </a>
            </div>

            <div class="row">
                <c:forEach items="${allProduct}" var="product">

                    <div class="col-lg-4 col-md-6 mb-4">
                        <div class="card h-100">
                            <a href="/product.jsp?productId=${product.id}">
                                <img class="card-img-top" src="/productImage?productId=${product.id}" alt=""></a>
                            <div class="card-body">
                                <h4 class="card-title">
                                    <a href="/product.jsp?productId=${product.id}">${product.name}</a>
                                </h4>
                                <h5>${product.price.grossPrice} zł</h5>
                                <h6>${product.price.netPrice} zł</h6>
                                <p class="card-text">${product.description}</p>
                            </div>
                            <div class="card-footer">
                                <small class="text-muted">&#9733; &#9733; &#9733; &#9733; &#9734;</small>
                            </div>
                        </div>
                    </div>

                </c:forEach>
            </div>
            <!-- /.row -->

        </div>
        <!-- /.col-lg-9 -->

    </div>
    <!-- /.row -->

</div>
<!-- /.container -->

<!-- Footer -->
<%@include file="footer.jsp" %>

<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>
