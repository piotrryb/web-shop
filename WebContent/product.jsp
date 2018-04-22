<%@ page import="hibernate.shop.ProductRating" %>
<%@ page import="hibernate.shop.ProjectHelper" %>
<%@ page import="hibernate.shop.product.Product" %>
<%@ page import="hibernate.shop.product.ProductRepository" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Optional" %>
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

    if (product.isPresent()) {
        List<ProductRating> allProductRating = ProductRepository.findAllRatingByProductId(product.get().getId());
        Double avgProductRating = ProductRepository.avgRatingByProductId(product.get().getId());
        pageContext.setAttribute("product", product.get());
        pageContext.setAttribute("allProductRating", allProductRating);
        pageContext.setAttribute("avgRatingByProductId", avgProductRating);
    }
%>
<body>

<!-- Navigation -->
<%@include file="head.jsp" %>

<!-- Page Content -->
<div class="container">

    <!-- Portfolio Item Heading -->
    <h1 class="my-4">${product.name}</h1>

    <!-- Portfolio Item Row -->
    <div class="row">

        <div class="col-md-8">
            <img class="img-fluid" src="/productImage?productId=${product.id}" alt="">
            <!--750x500-->
        </div>

        <div class="col-md-4">
            <h3 class="my-3">${product.price.grossPrice}</h3>
            <h4 class="my-3">${product.price.netPrice}</h4>

            <p>${product.description}</p>

            <form action="/addProduct" method="get">
                <input name="productId" type="hidden" value="${product.id}"/>
                <input name="productAmount" type="text" value="1"/>
                <button type="submit" class="btn-primary btn">Add to cart</button>
            </form>

        </div>

    </div>
    <!-- /.row -->

    <!-- Related Projects Row -->
    <h3 class="my-4">Related Projects</h3>

    <div class="row">

        <div class="col-md-3 col-sm-6 mb-4">
            <a href="#">
                <img class="img-fluid" src="http://placehold.it/500x300" alt="">
            </a>
        </div>

        <div class="col-md-3 col-sm-6 mb-4">
            <a href="#">
                <img class="img-fluid" src="http://placehold.it/500x300" alt="">
            </a>
        </div>

        <div class="col-md-3 col-sm-6 mb-4">
            <a href="#">
                <img class="img-fluid" src="http://placehold.it/500x300" alt="">
            </a>
        </div>

        <div class="col-md-3 col-sm-6 mb-4">
            <a href="#">
                <img class="img-fluid" src="http://placehold.it/500x300" alt="">
            </a>
        </div>

    </div>
    <!-- /.row -->

    <!-- productRating -->
    <div class="col-md-12">

        <div class="p-5">
            <form method="post" action="/addNewProductRating">
                <label>Comment:</label>
                <textarea class="form-control" name="description"> </textarea>
                <input id="input-id" name="rating" type="text" class="rating" data-size="lg">
                <input type="hidden" value="1" name="productId"/>
                <button type="submit" class="btn-primary btn"> Save</button>

            </form>
        </div>
    </div>
    <div class="col-md-12">
        <c:forEach items="${allProductRating}" var="productRating">
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="text-left">Date: ${productRating.createDate}</div>
                        <div class="text-right">Rating: ${productRating.rating}</div>
                    </div>
                    <div class="panel-body">Comment: ${productRating.description}</div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div>Average rating: ${avgRatingByProductId}</div>
</div>
<!-- /.container -->

<!-- Footer -->
<%@include file="footer.jsp" %>

<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>
