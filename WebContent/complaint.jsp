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

    <!-- Page Content -->
    <div class="container">

        <div class="row">

            <%@include file="leftMenu.jsp"%>
            <!-- /.col-lg-3 -->

            <div class="col-lg-9">
                <div class="card-body">
                    <h3 class="card-title">Zamówienie numer 333</h3>
                    <h4>kwota 333zl</h4>
                    <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Sapiente dicta fugit fugiat hic aliquam itaque facere, soluta. Totam id dolores, sint aperiam sequi pariatur praesentium animi perspiciatis molestias iure, ducimus!</p>

                </div>

                <div class="row">
                    <h3>Zgłoś reklamację</h3>

                    <div class="col-md-12">
                        <textarea class="form-control" id="description" rows="3"></textarea>
                    </div>
                    <div class="col-md-12">
                        <button class="btn btn-warning">Zgłoś reklamację</button>
                    </div>
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
