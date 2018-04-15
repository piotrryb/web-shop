<%@ page import="hibernate.shop.order.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="hibernate.shop.order.OrderRepository" %>
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
    <%@include file="head.jsp"%>

    <%
        if (userFromCookie != null) {
            List<Order> allOrders = OrderRepository.findAllByUserId(userFromCookie.getId(),0);
            pageContext.setAttribute("userOrders", allOrders);
        }
    %>

    <!-- Page Content -->
    <div class="container">

        <div class="row">

            <%@include file="leftMenu.jsp"%>
            <!-- /.col-lg-3 -->

            <div class="col-lg-9">

                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col">Lp.</th>
                            <th scope="col">Data</th>
                            <th scope="col">Kwota netto</th>
                            <th scope="col">Kwota brutto</th>
                            <th scope="col">Guziki</th>
                        </tr>
                    </thead>
                    <tbody>

                    <c:forEach items="${userOrders}" var="order" varStatus="lp">
                        <tr>
                            <th scope="row">${lp.index+1}</th>
                            <td>${order.orderHistory.confirmDate}</td>
                            <td>${order.totalNet}</td>
                            <td>${order.totalGross}</td>
                            <td><a href="/order.jsp?orderId=${order.id}">Details</a></td>
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
    <%@include file="footer.jsp"%>

    <!-- Bootstrap core JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>

</html>
