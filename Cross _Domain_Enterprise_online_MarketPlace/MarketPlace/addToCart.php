<?php
extract($_POST);
session_start();
require('CartItem.php');

ini_set('display_errors', 'on');



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

$pid =$_POST['pid'];


$qid = $_POST['Quantity'];

$sql= " select * from products where code =$pid";

$result= mysql_query($sql,$conn);
if($result != FALSE) {
    while ($itemDetails = mysql_fetch_array($result)) {

        $product_name = $itemDetails["name"];
        $Description = $itemDetails["description"];
        $price = $itemDetails["price"];
        $pageName = $itemDetails["image"];


        if ($_SESSION['uname'] == null || !isset($_SESSION['uname'])) {

            if (!isset($_SESSION['myCart'])) {
                $_SESSION['myCart'] = array();

            }
            $item = new Cartitem();
            $item->quantity = $qid;
            $item->pageName = $pageName;
            $item->name = $product_name;
            $item->price = $price;
            $item->id = $pid;

            $cart = unserialize(serialize($_SESSION['myCart']));
            $index = -1;

            for ($i = 0; $i < count($cart); $i++) {
                if ($cart[$i]->id==$pid ) {

                    $index = $i;
                    break;
                }
            }
            if ($index == -1) {
                $item->price = $price*$qid;
                $_SESSION['myCart'] [] = $item;

            } else {
                $cart[$index]->quantity++;
                $cart[$index]->price = $cart[$index]->quantity*$price;
                $_SESSION['myCart'] = $cart;
            }

        } else {

            $username = $_SESSION['uname'];
            $sql2 = "select * from Cart where User_name = '{$username}' and product_id = $pid ";

            $result = mysql_query($sql2, $conn);
            $cartItem = mysql_fetch_object($result);
            if ($cartItem == FALSE) {
                $totalPrice =$price * $qid;
                $sql1 = "INSERT INTO Cart (product_id, User_name,Quantity,Price)
                VALUES ($pid,'$username',$qid,$totalPrice)";


                if (mysql_query($sql1, $conn) === FALSE) {
                    echo mysql_error($conn);
                }
            } else {
                $newQuantity = $cartItem->Quantity + 1;
                $newPrice = $newQuantity*$price;
                $cartid = $cartItem->Cart_id;
                $sql3 = "Update Cart set Quantity = {$newQuantity} ,price = {$newPrice} where Cart_id = {$cartid}";

                mysql_query($sql3, $conn);
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
                    <h2>Cart Products</h2>
                    <a href="/MarketPlace/landing.php" >Continue Shopping</a>
                    <a href="/MarketPlace/CheckOut.php" >CheckOut</a>
                </header>
                <div class="major">

<table>
    <tr>
        <th colspan="2">Product Name</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Cart Functions</th>
    </tr>
    <?php
    $displaycart = unserialize(serialize($_SESSION['myCart']));
    if(isset($_SESSION['myCart']) && ($_SESSION['uname'] == null || !isset($_SESSION['uname']))) {


        for ($i = 0; $i < count($displaycart); $i++) {

            ?>
            <tr>

                <td>
                    <img src="<?php echo $displaycart[$i]->pageName; ?>" width="200" height="200"/>
                </td>
                <td>
                    <?php echo $displaycart[$i]->name; ?>
                </td>
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
        while ($row = mysql_fetch_array($result)) {
            ?>
    <tr>
        <td>

            <img src="<?php echo $row["image"]; ?>" width="200" height="200"/>
        </td>
        <td> <?php echo $row["name"]; ?></td>
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


