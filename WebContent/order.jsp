<%@ page import="com.shop.ProjectHelper" %>
<%@ page import="com.shop.domain.Order" %>
<%@ page import="com.shop.repository.OrderRepository" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Shop Item - Start Bootstrap Template</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/shop-homepage.css" rel="stylesheet">

</head>

<body>

<!-- Navigation -->
<%@include file="head.jsp" %>

<%
    String orderId = request.getParameter("orderId");
    Optional<Order> order = OrderRepository.findOrder(ProjectHelper.parseStringToLong(orderId));

    if (order.isPresent() && userFromCookie != null && order.get().getUser().getId().equals(userFromCookie.getId())) {
        pageContext.setAttribute("order", order.get());
    }
%>

<!-- Page Content -->
<div class="container">

    <div class="row">

        <%@include file="leftMenu.jsp" %>
        <!-- /.col-lg-3 -->

        <div class="col-lg-9">
            <div class="card-body">
                <h3 class="card-title">Order no. ${order.id}</h3>
                <h4>Total gross: ${order.totalGross}</h4>
                <h5>Total net: ${order.totalNet}</h5>

            </div>

            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">No.</th>
                    <th scope="col">Product name</th>
                    <th scope="col">Net price</th>
                    <th scope="col">Gross price</th>
                    <th scope="col">Amount</th>
                    <th scope="col">Description</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${order.orderDetailSet}" var="od" varStatus="it">
                    <tr>
                        <th scope="row">${it.index+1}</th>
                        <td>${od.product.name}</td>
                        <td>${od.price.netPrice.multiply(od.amount)} zł</td>
                        <td>${od.price.grossPrice.multiply(od.amount)} zł</td>
                        <td>${od.amount}</td>
                        <td>${od.product.description}</td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>
            <!-- /.card -->

        </div>
        <!-- /.col-lg-9 -->

    </div>

</div>
<!-- /.container -->

<!-- Footer -->
<%@include file="footer.jsp" %>

<!-- Bootstrap core JavaScript -->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>
