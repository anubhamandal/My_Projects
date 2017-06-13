<?php
   session_start();

   unset($_SESSION["uname"]);

?>
<!DOCTYPE HTML>
<!--
	Forty by HTML5 UP
	html5up.net | @ajlkn
	Free for personal and commercial use under the CCA 3.0 license (html5up.net/license)
-->
<html>
<head>
    <title>Product Page</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
    <link rel="stylesheet" href="assets/css/main.css" />
    <!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
    <!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
</head>
<body>

<!-- Wrapper -->
<div id="wrapper">

    <!-- Header -->
    <!-- Note: The "styleN" class below should match that of the banner element. -->
    <header id="header" class="alt style2">
        <a href="index.php" class="logo"><strong>Market Place</strong> </a>

        <nav>
            <a href="#menu">Menu</a>
        </nav>
    </header>

    <!-- Menu -->
    <nav id="menu">
        <ul class="links">
            <li><a href="index.php">Home</a></li>
            <li><a href="landing.php">Landing</a></li>
            <li><a href="deleteFromSessionCart.php">Cart</a></li>
        </ul>
        <ul class="actions vertical">
            <li><a href="#" class="button special fit">Get Started</a></li>
            <li><a href="login.html" class="button fit">Log In</a></li>
        </ul>
    </nav>

</div>
<section>
    <div id="main">
        <section style="margin-top: 200px;">
            <form method="get" action="login.html">
<center>
                <ul class="actions">
                    <li><input type="submit" value="Login Again" class="special" name="Login Again"/></li>

                </ul>
</center>
            </form>
        </section>
    </div>

</section>
<!-- Scripts -->
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/jquery.scrolly.min.js"></script>
<script src="assets/js/jquery.scrollex.min.js"></script>
<script src="assets/js/skel.min.js"></script>
<script src="assets/js/util.js"></script>
<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
<script src="assets/js/main.js"></script>
</body>
</html>