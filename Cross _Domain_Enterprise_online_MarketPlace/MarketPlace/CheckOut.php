<?php
session_start();
ini_set('display_errors', 'on');

global $orderid;
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
mysql_select_db($dbname, $conn);


if(($_SESSION['uname'] == null || !isset($_SESSION['uname']))) {
    include "login.html";
}
else if(isset($_SESSION['uname']) && isset($_SESSION['myCart'])) {
    $username=$_SESSION['uname'];
    $sql1 = "INSERT INTO myOrder (User_name)
                VALUES ('$username')";
    echo "<br>";

    if (mysql_query($sql1, $conn) === FALSE) {
        echo mysql_error($conn);
    }
    else {
        $orderid = $conn->insert_id;
        echo $orderid;
        $displaycart = $_SESSION['myCart'];
        for ($i = 0; $i < count($displaycart); $i++) {

        $pid = $displaycart[$i]->id;
        $price = $displaycart[$i]->price;
        $quantity = $displaycart[$i]->quantity;

        $sql2 = "INSERT INTO Order_line_item (product_id,Order_id,price,quantity)
                VALUES ($pid,$orderid,$price,$quantity)";
        echo $sql2;
            if (mysql_query($sql2, $conn) === FALSE) {
                 echo mysql_error($conn);
            }

        }

    }

}
else if(isset($_SESSION['uname']) && !isset($_SESSION['myCart'])) {
    $username=$_SESSION['uname'];
    $sql3 = "INSERT INTO myOrder (User_name)
                VALUES ('$username')";
    echo "<br>";
    echo "inside last";
    if (mysql_query($sql3, $conn) === FALSE) {
        echo mysql_error($conn);
    }
    else {
        $orderid=$conn->insert_id;

        $sql = " select * from Cart  where user_name = '$username' ";

        $result = mysql_query($sql, $conn);
        $num_rows = mysql_num_rows($result);
        if ($num_rows > 0) {
            while ($row = mysql_fetch_array($result)) {
                $pid = $row["product_id"];
                $price = $row["Price"];
                $quantity = $row["Quantity"];

                $sql4 = "INSERT INTO Order_line_item (product_id,Order_id,price,quantity)
                VALUES ($pid,$orderid,$price,$quantity)";
                if (mysql_query($sql4, $conn) === FALSE) {
                    echo mysql_error($conn);
                }
            }
        }
    }
}
mysql_close($conn);
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
                    <h2>Your order has been Placed. Order id : <?php echo $orderid;?></h2>
                </header>
                <div class="major">
<table width="100%" border="0" cellspacing="0" cellpadding="15">
    <tr>
        <th> Product Id</th>
        <th>Quantity</th>
        <th>Price</th>

    </tr>
    <?php
$servername = "localhost";
$username = "";
$password = "";
$dbname = "mktplace";
echo "diaply Cart";
$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
    die("Unable to connect Server: " . $conn->connect_error);
}


global $conn;
$conn = mysql_connect($host,$username,$password);
    mysql_select_db($dbname, $conn);


$sqllast = "select * from Order_line_item where Order_id=$orderid";
    $result= mysql_query($sqllast,$conn);
    $num_rows = mysql_num_rows($result);
    if($num_rows >0) {
    while ($row = mysql_fetch_array($result)) {
    ?>
    <tr>
        <td>

            <?php echo $row["product_id"]; ?>
        </td>
        <td><?php echo $row["Quantity"]; ?></td>
        <td>
            <?php echo $row["Price"]; ?>
        </td>
    </tr>
    <?php }
    }?>
</table>
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
