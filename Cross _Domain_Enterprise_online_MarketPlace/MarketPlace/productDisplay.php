<?php
session_start();
$uname = $_SESSION["uname"];
ini_set('display_errors', '1');
$servername = "localhost";
$username = "";
$password = "";
$dbname = "mktplace";
global $conn;
$sql;
global $result;
if (isset($_GET['pageName'])) {
    $id = $_GET['pageName'];
    $conn = new mysqli($servername, $username, $password, $dbname);
    if ($conn->connect_error) {
        die("Unable to connect Server: " . $conn->connect_error);
    }

    $sql = "select * from products where image =$id";

    $result = $conn->query($sql);
    $row = $result->fetch_assoc();

    if ($result->num_rows > 0) {
        $product_name = $row["name"];
        $Description = $row["description"];
        $price = $row["price"];
        $image = $row["image"];
        $pid = $row["code"];
	$store = $row["store"];
	$reviewTot = $row['review_total'];
        $reviewCount=$row['review_count'];
        if($reviewCount == 0) {
            $reviewCount = 1;
        }

    } else {
        echo " Does Not Exist";
        exit();
    }
    mysql_close($conn);
}

$value = $pid;
if(array_key_exists('recentview', $_COOKIE))
{
    $cookie = $_COOKIE['recentview'];
	$cookie = explode(',',$cookie);
}
else 
{
    $cookie = array();
}
// add the value to the array
array_push($cookie, $value);
//combine the array back
$cookie = implode(',',$cookie);
// save the cookie
setcookie('recentview', $cookie, time()+60*60*24*7,"/");

if( $store == "ketkisyoga")
{
	if(array_key_exists('ketkisyoga', $_COOKIE))
	{
		$cookie = $_COOKIE['ketkisyoga'];
		$cookie = explode(',',$cookie);
	}
	else 
	{
		$cookie = array();
	}
	// add the value to the array
	array_push($cookie, $value);
	//combine the array back
	$cookie = implode(',',$cookie);
	// save the cookie
	setcookie('ketkisyoga', $cookie, time()+60*60*24*7,"/");
}

if( $store == "fruitsflowers")
{
	if(array_key_exists('fruitsflowers', $_COOKIE))
	{
		$cookie = $_COOKIE['fruitsflowers'];
		$cookie = explode(',',$cookie);
	}
	else 
	{
		$cookie = array();
	}
	// add the value to the array
	array_push($cookie, $value);
	//combine the array back
	$cookie = implode(',',$cookie);
	// save the cookie
	setcookie('fruitsflowers', $cookie, time()+60*60*24*7,"/");
}

if( $store == "bookstore")
{
	if(array_key_exists('bookstore', $_COOKIE))
	{
		$cookie = $_COOKIE['bookstore'];
		$cookie = explode(',',$cookie);
	}
	else 
	{
		$cookie = array();
	}
	// add the value to the array
	array_push($cookie, $value);
	//combine the array back
	$cookie = implode(',',$cookie);
	// save the cookie
	setcookie('bookstore', $cookie, time()+60*60*24*7,"/");
}

if( $store == "zetaplot")
{
	if(array_key_exists('zetaplot', $_COOKIE))
	{
		$cookie = $_COOKIE['zetaplot'];
		$cookie = explode(',',$cookie);
	}
	else 
	{
		$cookie = array();
	}
	// add the value to the array
	array_push($cookie, $value);
	//combine the array back
	$cookie = implode(',',$cookie);
	// save the cookie
	setcookie('zetaplot', $cookie, time()+60*60*24*7,"/");
}

if( $store == "greeting")
{
	if(array_key_exists('greeting', $_COOKIE))
	{
		$cookie = $_COOKIE['greeting'];
		$cookie = explode(',',$cookie);
	}
	else 
	{
		$cookie = array();
	}
	// add the value to the array
	array_push($cookie, $value);
	//combine the array back
	$cookie = implode(',',$cookie);
	// save the cookie
	setcookie('greeting', $cookie, time()+60*60*24*7,"/");
}
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



    <!-- Main -->
    <div id="main">

        <!-- One -->
        <section style="margin-top:75px;">
            <div class="inner">

                <header class="major">
                    <h2>Product name</h2>
                </header>



                <img src="<?php echo $image; ?>" style="width:300px; height:300px;"> &nbsp;


                <div style=""  "style=float:left;">
                <h3>Name : <?php echo $product_name; ?></h3>
                Price: $  <?php echo $price; ?><br>
				
                Description:  <?php echo $Description; ?><br><br>
                                <h4 >Average review: <?php echo $reviewTot / $reviewCount ?></h4>
            <section>
                <form method="post" action="addToCart.php">

                    <input type="hidden" value="<?php echo $pid;?>" name="pid" id="pid"/>

                    <div class="field half">
                        <label for="Quantity">Quantity</label>
                        <input type="text" name="Quantity" id="Quantity"  placeholder="Quantity" required/>
                    </div>
                    <br>
                    <ul class="actions">
                        
                    <li><input type="submit" value="Add to Cart" class="special" name="Add to Cart"/></li>
                    </ul>
                </form>
            </section>
            <br>


                </div>

                <!-- 5 Start Review start -->
                <h4>Please review </h4>
                <script>
                    $(document).ready(function () {
                        $("#review .stars").click(function () {
                            var label = $("label[for='" + $(this).attr('id') + "']");
                            $("#feedback").text(label.attr('title'));
                            $.post('http://mystoreonlinehosted.com/MarketPlace/rating.php',
                                {
                                    code: '<?php echo $pid;?>',
                                    store: '<?php echo $store;?>',
                                    rate: $(this).val(),
                                    user: '<?php echo $uname;?>',
                                    comment: $("#comment").val()
                                } ,
                                function(d) {
                                    if(d == "LOGIN") {
                                        console.log("do: ", d); alert('Please login to rate and review');
                                    }
                                    else if(d > 0) { console.log("d1: ", d); alert('You already rated'); }
                                    else if(d == 0) { console.log("d2: ", d); alert('Thanks For Rating'); }
                                    else { console.log("d3: ", d); alert('Something went wrong!!'); }
                                });
                            $(this).attr("checked");
                        });
                    });
                </script>
                <input type="text" name="comment" id="comment" style="width:50%;"  placeholder="Please add your comments then rate"/><br>

                <fieldset id='review' class="rating">
                    <input class="stars" type="radio" id="star1" name="rating" value="1" />
                    <label class = "full" for="star1" title="Sucks big time - 1 star"></label>
                    <input class="stars" type="radio" id="star2" name="rating" value="2" />
                    <label class = "full" for="star2" title="Kind of bad - 2 stars"></label>
                    <input class="stars" type="radio" id="star3" name="rating" value="3" />
                    <label class = "full" for="star3" title="Meh - 3 stars"></label>
                    <input class="stars" type="radio" id="star4" name="rating" value="4" />
                    <label class = "full" for="star4" title="Pretty good - 4 stars"></label>
                    <input class="stars" type="radio" id="star5" name="rating" value="5" />
                    <label class = "full" for="star5" title="Awesome - 5 stars"></label>
                </fieldset><br>
                <div id='feedback'></div>
                <!-- 5 Start Review start end -->
                
                <div>
                    <h3>Reviews and ratings</h3>
                    <?php
                        $conn = new mysqli($servername, $username, $password, $dbname);
    	                if ($conn->connect_error) 
    	 	        {
        		    die("Connection failed: " . $conn->connect_error);
    		        } 

    		        $sql = "SELECT user, review, comments FROM prodreview where code='$pid'order by timestamp desc";
		        $result = $conn->query($sql);
    		        $conn->close();
		        while($row = $result->fetch_assoc()) {
		     
             		    $user = $row["user"];
             		    $review = $row["review"];
             		    $comments = $row["comments"];
             	    ?>
             		<p><b><?php echo $review; ?>* </b>&nbsp;<i><?php echo $comments; ?></i>&nbsp; - &nbsp;<b><?php echo $user; ?></b>
             	    <?php    
             		}
                    ?>			
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
