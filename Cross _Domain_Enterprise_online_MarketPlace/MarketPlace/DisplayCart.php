<?php

session_start();
ini_set('display_errors', 'on');

?>

<!DOCTYPE HTML>

<html>
<head>
    <title>Product Page</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
    <!--[if lte IE 8]><script src="assets/js/ie/html5shiv.js"></script><![endif]-->
    <link rel="stylesheet" href="assets/css/main.css" />
    <!--[if lte IE 9]><link rel="stylesheet" href="assets/css/ie9.css" /><![endif]-->
    <!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
</head>
<body>

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
            <div class="inner">
                <header class="major">
                    <h2>Cart Products</h2>  <a href="/MarketPlace/landing.php" >Continue Shopping</a>
                    <a href="/MarketPlace/CheckOut.php" >CheckOut</a>
                </header>
                <div class="major">
<table width="100%" border="0" cellspacing="0" cellpadding="15">
    <tr>
        <th colspan="2"> Product Name</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Cart Functions</th>
    </tr>
    <?php

    if(isset($_SESSION['myCart']) && ($_SESSION['uname'] == null || !isset($_SESSION['uname']))) {
        $_SESSION['myCart'] = array_values($_SESSION['myCart']);
        $displaycart = unserialize(serialize($_SESSION['myCart']));
       if(count($displaycart) == 0){
         echo "No Products in cart";
       }
       else {
           for ($i = 0; $i < count($displaycart); $i++) {

               ?>
               <tr>
                   <td>

                       <img src="<?php echo $displaycart[$i]->pageName; ?>" width="200" height="200"/>
                   </td>
                   <td><?php echo $displaycart[$i]->name; ?></td>
                   <td>
                       <?php echo $displaycart[$i]->quantity; ?>
                   </td>
                   <td>
                       <?php echo $displaycart[$i]->price; ?>
                   </td>
                   <td>
                       <form method="post" action="deleteFromSessionCart.php">

                           <input type="hidden" value="<?php echo $i; ?>" name="pid" id="pid"/>
                           <input type="submit" value="Remove From Cart" class="special" name="Remove from Cart"/>
                       </form>
                   </td>
               </tr>
               <?php
           }
       }
    }
    else {
        $servername = "localhost";
        $username = "";
        $password = "";
        $dbname = "mktplace";

        $conn = new mysqli($servername, $username, $password, $dbname);
        if ($conn->connect_error) {
            die("Unable to connect Server: " . $conn->connect_error);
        }


        global $conn;
        $conn = mysql_connect($host,$username,$password);


        if ($conn->connect_error) {
            die("Connection failed: " . $conn->connect_error);
        }
        mysql_select_db($dbname, $conn);


        $username = $_SESSION['uname'];
        $sql= " select c.Cart_id as cart_id ,p.image as image , p.name as name,c.Quantity as qunatity , c.price as price from Cart c , products p  where c.User_name = '$username' AND c.product_id = p.code";

        $result= mysql_query($sql,$conn);
        $num_rows = mysql_num_rows($result);
        if($num_rows >0) {
            while ($row = mysql_fetch_array($result)) {
                ?>
                <tr>
                    <td>

                        <img src="<?php echo $row["image"]; ?>" width="200" height="200"/>
                    </td>
                    <td><?php echo $row["name"]; ?></td>
                    <td>
                        <?php echo $row["qunatity"]; ?>
                    </td>
                    <td>
                        <?php echo $row["price"]; ?>
                    </td>
                    <td>
                        <form method="post" action="deleteFromDBCart.php">

                            <input type="hidden" value="<?php echo $row["cart_id"]; ?>" name="cartid" id="cartid"/>
                            <input type="submit" value="Remove From Cart" class="special" name="Remove from Cart"/>
                        </form>
                    </td>
                </tr>

                <?php
            }
        }
        else {
            echo "No Products Available in Cart";
        }
    }
    mysql_close($conn);
    ?>
</table>
                    <a href="/MarketPlace/landing.php" >Continue Shopping</a>
                    <a href="/MarketPlace/CheckOut.php" >CheckOut</a>
                </div>
            </div>
        </section>
    </div>
</div>
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

