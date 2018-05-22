<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Login - Jeff Shop</title>

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
                    <form method="post" action="/login" role="login">
                        <div>
                            <a class="navbar-brand" href="/index.jsp">Jeff Shop</a>
                        </div>
                        <%--<img src="http://i.imgur.com/RcmcLv4.png" class="img-responsive" alt="" />--%>
                        <input type="email" name="email" placeholder="Email" required class="form-control input-lg" value="mail@gmail.com" />

                        <input type="password" name="password" class="form-control input-lg" id="password" placeholder="password" aria-required="true" required="" />

                        <%--<div class="pwstrength_viewport_progress"></div>--%>
                        <button type="submit" name="go" class="btn btn-lg btn-primary btn-block">Sign in</button>
                        <div>
                            <a href="/register.jsp">Create account</a>
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
