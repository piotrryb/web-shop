<%@ page import="com.shop.repository.CartRepository" %>
<%@ page import="com.shop.domain.Cart" %>
<%@ page import="java.util.Optional" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Jeff Shop - cart</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" crossorigin="anonymous">

    <!-- Custom styles for this template -->
    <link href="css/shop-homepage.css" rel="stylesheet">

</head>

<body>

    <!-- Navigation -->
    <%@include file="head.jsp"%>

    <%
        if (userFromCookie != null) {
            Optional<Cart> byUserId = CartRepository.findByUserId(userFromCookie.getId());
            if (byUserId.isPresent()) {
                pageContext.setAttribute("cart",byUserId.get());
            }
        }
    %>

    <!-- Page Content -->
    <div class="container">
        <div class="row">
            <%@include file="leftMenu.jsp"%>
            <!-- /.col-lg-3 -->
            <div class="col-lg-9">
                <h2>Cart</h2>
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th scope="col">Product name</th>
                            <th scope="col">Amount</th>
                            <th scope="col">Net price</th>
                            <th scope="col">Gross price</th>
                            <th scope="col">Total net</th>
                            <th scope="col">Total gross</th>
                            <th scope="col"></th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${cart.cartDetailSet}" var="cd" varStatus="it">
                        <tr>
                            <th scope="row">${cd.product.name}</th>
                            <td><fmt:formatNumber type="number" minFractionDigits="0" maxFractionDigits="0"
                                                  value="${cd.amount}"/>
                                <a href="/decreaseProductAmount?productId=${cd.product.id}&method=add">
                                    <i class="fas fa-plus-square"></i></a>
                                <a href="/decreaseProductAmount?productId=${cd.product.id}&method=subtract">
                                    <i class="fas fa-minus-square"></i></a>
                                </td>
                            <td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                  value="${cd.price.netPrice}"/></td>
                            <td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                  value="${cd.price.grossPrice}"/></td>
                            <fmt:formatNumber var="totalNetPrice" type="number" minFractionDigits="2" maxFractionDigits="2" value="${cd.price.netPrice.multiply(cd.amount)}"/>
                            <fmt:formatNumber var="totalGrossPrice" type="number" minFractionDigits="2" maxFractionDigits="2" value="${cd.price.grossPrice.multiply(cd.amount)}"/>
                            <td>${totalNetPrice}</td>
                            <td>${totalGrossPrice}</td>
                            <td></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <td></td>
                            <td colspan="3"><b>Total:</b></td>
                            <td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                  value="${cart.totalNetPrice}"/></td>
                            <td><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2"
                                                  value="${cart.totalGrossPrice}"/></td>
                            <td></td>
                        </tr>
                    </tfoot>
                </table>
                <!-- /.card -->

                <div class="row">
                    <div class="col-md-6">
                        <h5>Delivery method:</h5>
                        <label>
                            <select class="form-control">
                                <option>Courier</option>
                                <option>In-store pickup</option>
                            </select>
                        </label>
                    </div>
                    <div class="col-md-6">
                        <h5>Delivery address</h5>
                        <div class="col-md-12">
                            <label>City</label>
                            <input type="text" class="form-control" name="city"/>
                        </div>
                        <div class="col-md-12">
                            <label>Zip code</label>
                            <input type="text" class="form-control" name="zipCode"/>
                        </div>
                        <div class="col-md-12">
                            <label>Street</label>
                            <input type="text" class="form-control" name="street"/>
                        </div>
                    </div>
                </div>

                <div>
                    <a href="/createOrder" class="btn btn-success">Buy and pay</a>
                </div>

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
