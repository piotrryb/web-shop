<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Register - Jeff Shop</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/shop-homepage.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

</head>

<body>
    <div class="container">
        <div class="row" id="pwd-container">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <section class="login-form">
                    <form method="post" action="/register" role="register">
                        <div>
                            <a class="navbar-brand" href="/index.jsp">Super Jeff shop</a>
                        </div>
                        <%--<img src="http://i.imgur.com/RcmcLv4.png" class="img-responsive" alt="" />--%>
                        <label>Name</label>
                        <input type="text" name="firstName" placeholder="name" aria-required="true" class="form-control input-lg"/>
                        <label>Surname</label>
                        <input type="text" name="lastName" placeholder="surname" aria-required="true" class="form-control input-lg"/>
                        <label>Email</label>
                        <input type="text" name="email" placeholder="email" aria-required="true" class="form-control input-lg"/>
                        <label>Password</label>
                        <input type="password" name="password" placeholder="password" aria-required="true" class="form-control input-lg"/>
                        <label>Repeat password</label>
                        <input type="password" name="password2" placeholder="password2" aria-required="true" class="form-control input-lg"/>

                        <%--<div class="pwstrength_viewport_progress"></div>--%>

                        <button type="submit" name="go" class="btn btn-lg btn-primary btn-block">Register</button>
                        <div>
                            <a href="/login.jsp">Login</a>
                        </div>
                    </form>
                    <div class="form-links">
                        <a href="#">www.website.com</a>
                    </div>
                </section>
            </div>
            <div class="col-md-4"></div>
        </div>
    </div>
</body>
</html>
