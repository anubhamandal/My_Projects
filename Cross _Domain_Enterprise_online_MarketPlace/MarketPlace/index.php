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
        <link rel="stylesheet" href="http://www.w3schools.com/lib/w3.css">
	</head>
	<body>
	<?php
    		session_start();
    	
        
    		// define variables and set to empty values
    		$unameErr = $passwdErr = $msg = "";
    		$uname = $passwd = "";
    
    		$servername = "localhost";
    		$username = "";
    		$password = "";
    		$dbname = "mktplace";

    		if ($_SERVER["REQUEST_METHOD"] == "POST") {
      			if (empty($_POST["username"])) {
        			$unameErr = "Username is required";
      			} else {
        			$uname = test_input($_POST["username"]);
      			}

      			if (empty($_POST["password"])) {
        			$passwdErr = "Password is required";
      			} else {
        			$passwd = test_input($_POST["password"]);
      			}
      
      			if($uname != '' && $passwd != '') {	
        			
        			// Create connection
        			$conn = new mysqli($servername, $username, $password, $dbname);
        			$msg2= "Connected successfully -- index --". $_SESSION["uname"]; 
        			// Check connection
        			if ($conn->connect_error) {
          				die("Connection failed: " . $conn->connect_error);
        			}
        			$sql = "SELECT FIRST_NAME, PASSWORD FROM user WHERE USERNAME = '$uname' and PASSWORD = '$passwd'";
        			$result = $conn->query($sql);
        			if ($result->num_rows > 0) {
          				$msg = "found";
          				$_SESSION["uname"] = $uname;
          				$msg= "Welcome ". $_SESSION["uname"]."! "; 
                    
                    } 
        			$conn->close();

      			}
    		}
    
    		function test_input($data) {
      			$data = trim($data);
      			$data = stripslashes($data);
      			$data = htmlspecialchars($data);
      			return $data;
    		}
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

		<!-- Banner -->
                <section id="banner" class="major" style="height:100%">
                    <div>
                        <div class="mySlides">
                            <img src="images/yoga_big.jpg" style="width:100%">
                            <div class="w3-display-middle w3-large w3-container w3-padding-16 ">
                               <div class="inner">
			           <header class="major">
				       <h1>Welcome!</h1>
				   </header>
				   <div class="content">
				       <p>Feel free to check out our new collection<br />in all categories.</p>
				           <ul class="actions">
					       <li><a href="#one" class="button next scrolly">Get Started</a></li>
					   </ul>
				   </div>
                               </div>
                           </div>
                       </div>

                       <div class="mySlides">
                           <img src="images/homedecor_big.jpg" style="width:100%">
                           <div class="w3-display-middle w3-large w3-container w3-padding-16 ">
                               <div class="inner">
			           <header class="major">
				       <h1>Welcome!</h1>
				   </header>
			           <div class="content">
				       <p>Feel free to check out our new collection<br />in all categories</p>
				           <ul class="actions">
					       <li><a href="#one" class="button next scrolly">Get Started</a></li>
					   </ul>
				   </div>
			      </div>
                           </div>
                       </div>

                       <div class="mySlides">
                           <img src="images/greetings_big.jpg" style="width:100%">
                               <div class="w3-display-middle w3-large w3-container w3-padding-16 ">
                                   <div class="inner">
			 	       <header class="major">
				           <h1>Welcome!</h1>
				       </header>
				       <div class="content">
				           <p>Feel free to check out our new collection<br />in all categories.</p>
				           <ul class="actions">
				               <li><a href="#one" class="button next scrolly">Get Started</a></li>
				           </ul>
				       </div>
                                   </div>
                               </div>
                       </div>

                       <div class="mySlides">
                           <img src="images/fruits_big.jpg" style="width:100%">
                           <div class="w3-display-middle w3-large w3-container w3-padding-16">
                               <div class="inner">
			           <header class="major">
				        <h1>Welcome!</h1>
				   </header>
				   <div class="content">
				       <p>Feel free to check out our new collection<br />in all categories.</p>
				       <ul class="actions">
				           <li><a href="#one" class="button next scrolly">Get Started</a></li>
				       </ul>
				   </div>
			       </div>
                           </div>
                       </div>

                       <div class="mySlides">
                           <img src="images/books_big.jpg" style="width:100%">
                           <div class="w3-display-middle w3-large w3-container w3-padding-16 ">
                               <div class="inner">
			           <header class="major">
				       <h1>Welcome!</h1>
				   </header>
			           <div class="content">
				       <p>Feel free to check out our new collection<br />in all categories.</p>
				       <ul class="actions">
					    <li><a href="#one" class="button next scrolly">Get Started</a></li>
				        </ul>
				    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
                
                <script>
                    var myIndex = 0;
                    carousel();

                    function carousel() {
                        var i;
                        var x = document.getElementsByClassName("mySlides");
                        
                        for (i = 0; i < x.length; i++) {
                            x[i].style.display = "none";
                        }
                        myIndex++;
                        if (myIndex > x.length) {myIndex = 1}
                        x[myIndex-1].style.display = "block";
                        setTimeout(carousel, 2000); // Change image every 2 seconds
                    }
                </script>

					
                   
		<!-- Main -->
		<div id="main">

		    <!-- One -->
		    <section id="one" class="tiles">
		        <article>
			    <span class="image">
			        <img src="images/decor.jpg" alt="" />
			    </span>
			    <header class="major">
			        <h3><a href="homedecor.php" class="link">Home Decor</a></h3>
				            <p>
                                    <ul>
                                       <li><b>Side tables</b></li>
                                        <li><b>Sofa</b></li>
                                        <li><b>Arm Chair</b></li>
                                        <li><b>Corner Shelve</b></li>
                                        <li><b>Shoe Racks</b></li>
                                    </ul>
                    
			    </header>
			</article>
                                
			<article>
			    <span class="image">
			        <img src="images/yoga.jpg" alt="" />
			    </span>
			    <header class="major">
			        <h3><a href="yoga.php" class="link">Yoga</a></h3>
				<p>
                                    <ul>
                                        <li><b>Yoga Mat</b></li>
                                        <li><b>Massage balls</b></li>
                                        <li><b>Yoga Block</b></li>
                                        <li><b>Yoga Bag</b></li>
                                        <li><b>Weights</b></li>
                                    </ul>
                                
			    </header>
			</article>
                                
			<article>
			    <span class="image">
			        <img src="images/fruits.jpg" alt="" />
			    </span>
			    <header class="major">
			        <h3><a href="flowers.php" class="link">Fruits & Flowers</a></h3>
			        <p>
                                    <ul>
                                        <li><b>Roses</b></li>
                                        <li><b>Daisies</b></li>
                                        <li><b>Tulips</b></li>
                                        <li><b>Apples</b></li>
                                        <li><b>Kiwi</b></li>
                                    </ul>
                                
			    </header>
			</article>
                                
			<article>
			    <span class="image">
			        <img src="images/greetings1.jpg" alt="" />
			    </span>
			    <header class="major">
			        <h3><a href="greetings.php" class="link">Greeting Cards</a></h3>
			        <p>
			            <ul>
			                <li><b>Birthday greetings</b></li>
			                <li><b>Valentine Day greetings</b></li>
			                <li><b>New Year greetings</b></li>
			                <li><b>Miscellaneous greetings</b></li>
			            </ul>
			        
			    </header>
			</article>
                                
			<article>
			    <span class="image">
			        <img src="images/books.jpg" alt="" />
			    </span>
			    <header class="major">
			        <h3><a href="books.php" class="link">Books</a></h3>
			        <p>
			            <ul>
			                <li><b>Python</b></li>
			                <li><b>The Autobiography of a Yogi</b></li>
			                <li><b>Easy To Use</b></li>
			                <li><b>The Artists Daughter</b></li>
			                <li><b>Software Security Technologies</b></li>
			            </ul>
             </header>
   
         </article>
			</section>

			<!-- Two -->
			<section  style="margin-left:2px;">
			    <div class="inner">
			        <header class="major">
				    <h2>Top picks</h2>
				</header>
				  <p>
                      <?php                 
                                           $conn = new mysqli($servername, $username, $password, $dbname);
    					   if ($conn->connect_error) 
    					   {
        					die("Connection failed: " . $conn->connect_error);
    					   } 

    					   $sql = "SELECT name, link, image,(review_total / review_count) as ratings FROM products order by ratings desc limit 5";
					   $result = $conn->query($sql);
    					   $conn->close();
				      
         					
           					    while($row = $result->fetch_assoc()) {
             					        $name = $row["name"];
             					        $link = $row["link"];console.log($link);
             					        $image = $row["image"];
             					        $ratings = $row["ratings"];
                                    ?>
             					      
                                       <div class="responsive" style="float:left; padding:5px">
                                         <div class="img">
                                            <a href='productDisplay.php?pageName="<?php  echo $image; ?>"'  >
                                                <img src="<?php echo $image;?>"  width="200" height="200" />
                                                     </a>
                                                    <div class="product"> <b> <?php echo $name; ?> </b><br>
                                                     <i>Rating:  <?php echo number_format((float)$ratings, 2, '.', '');?></i></div>
                                                       </div>
                                                        </div>
                                
 						<?php  } ?>
       					
				       
			        
                    
                      
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