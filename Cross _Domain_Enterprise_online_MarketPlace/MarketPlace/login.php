<!DOCTYPE HTML>
<html>
	<head>
		<title>Market Place</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
		<link rel="stylesheet" href="assets/css/main.css" />
		<!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
		<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
		<style type = "text/css">
                	h5 {
  				border-color: white;
  				border-width: .25em;
  				border-style: solid;
  				margin-top: auto;
  				margin-bottom: auto;
  				padding: .45em;
  			}
 		</style>
	</head>
	<body>
		<?php
   			ob_start();
   			session_start();
		?>

		<!-- Wrapper -->
		<div id="wrapper">

			<SCRIPT type="text/javascript" src="./fbapp/fb.js"></SCRIPT>
                <!-- Header -->
	        <header id="header" class="alt">
		    <a href="index.php" class="logo"><strong>Market Place</strong> <span>for one and all</span></a> 
                <p>  <?php if(!empty($_SESSION["uname"])) echo "Welcome ". $_SESSION["uname"]."! "; ?></p>  <p>  <?php if(empty($_SESSION["uname"])) echo "welcome guest"; ?></p>
                <p style="float:right; margin-right:50px;">  <?php if(!empty($_SESSION["uname"])){?>
                
              
  	             <p style="margin-left: auto; width: 20em;"><a href="logout.php">Log Out</a></p>
                  <?php } ?>
                
		   
              
               <?php if(!isset($_SESSION["uname"])) { ?>
                 <p style="margin-right:50px;" class="fb-login-button" data-scope="public_profile,email" onlogin="checkLoginState()" ></p>
                <?php }?>
                
              <nav>   
                <a href="#menu">Menu</a>
                
		    </nav>
		</header>

		<!-- Menu -->
			<nav id="menu">
				<ul class="links">
					<li><a href="index.php">Home</a></li>
					<li><a href="landing.php">Products</a></li>
                    <li><a href="deleteFromSessionCart.php">Cart</a></li>
				</ul>
				<ul class="actions vertical">
					<li><a href="#" class="button special fit">Get Started</a></li>
                    <?php if(empty($_SESSION["uname"])){?> <li><a href="login.html" class="button fit">Log In</a></li>  <?php } ?>
                     
                    <?php if(!empty($_SESSION["uname"])){?> <li><a href="logout.php" class="button fit">Log Out</a></li>  <?php } ?>
				</ul>
			</nav>

			<div id="main">
				<section>
					<form method="post" action="index.php">
						<div class="field half">
							<label for="username">User Name</label>
							<input type="text" name="username" id="username"  placeholder="user name " required/>
						</div>
						<div></div>
						<div class="field half">
							<label for="password">password</label>
							<input type="password" name="password" required placeholder="password">
						</div>
									
						<ul class="actions">
							<li><input type="submit" value="Login" class="special" name="submit"/></li>
							<li><input type="reset" value="Clear" /></li>
							<li><h5><a href="newuser.php">Create new User</a></h5></li>
						</ul>
					</form>
				</section>
			</div>
			
 		</div>
 	</body>
 </html>