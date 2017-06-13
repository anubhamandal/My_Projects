<!DOCTYPE HTML>
<html>

	<head>

		<title>Market Place</title>

		<meta charset="utf-8" />

		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />

		<link rel="stylesheet" href="assets/css/main.css" />

	</head>

	<body>
        <?php session_start(); ?>
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
				<section>
					<div class="inner">
					        <?php 
						$servername = "localhost";
                                                $username = "";
                                                $password = "";
                                                $dbname = "mktplace";
                                                ?>
                                               
						<header class="major">
							<h2>Home Decor</h2>
						</header>
						<div class="major">
							
                                                	<?php
                                                    $imageName = array();
                                                    $desc = array();
                                                    $price = array();
                                                    $pname = array();
                                                    function get_string_between($string, $start, $end)
                                                        {
                                                            $string = " ".$string;
                                                            $ini = strpos($string,$start);
                                                            if ($ini == 0)
                                                                return "";
                                                            $ini += strlen($start);
                                                            $len = strpos($string,$end,$ini) - $ini;
                                                            return substr($string,$ini);
                                                        }

                                                    $ch = curl_init("http://zetaplot.com/shareProducts.php");
                                                    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
                                                    $op = curl_exec($ch);
                                                    $dom =  new DOMDocument;
                                                    $dom->loadHTML($op);
                                                    
                                                

                                                    $links = $dom->getElementsByTagName('img');

                                                    foreach ($links as $link) {
                                                        $imgPath =  $link->getAttribute('src');
                                                        array_push($imageName, $imgPath);
                                                    }


                                                    $elems = $dom->getElementsByTagName('b');
                                                    $i=1;
                                                    foreach ($elems as $ele) {
                                                        if($i == 1) {
                                                            array_push($pname, $ele->nodeValue);
                                                            $i++;
                                                        }

                                                        else if($i == 2){
                                                            $withPrice = $ele->nodeValue;
                                                            $onlyPrice =get_string_between($withPrice,'Product Price: $','');
                                                            array_push($price,$onlyPrice);
                                                            $i++;
                                                        }
                                                        else if($i == 3) {
                                                            array_push($desc, $ele->nodeValue);
                                                            $i=1;
                                                        }
                                                    }

                                                    curl_close ($ch);

                                                    

                                                    $conn = new mysqli($servername, $username, $password, $dbname);
                                                    if ($conn->connect_error) {
                                                        die("Unable to connect Server: " . $conn->connect_error);
                                                    }
                                                    $store = 'fruitsflowers';

                                                    for ($x = 0; $x < count($imageName); $x++) {
                                                        $checkQuery = "SELECT * FROM products WHERE image = '".$imageName[$x]."'";

                                                        $result = $conn->query($checkQuery);
                                                        $num_rows = $result->num_rows;

                                                        if ($num_rows >0) {
                                                            // do nothing

                                                        }
                                                        else{

                                                            $sql = "INSERT INTO products (name, description,price,image,store) VALUES ('$pname[$x]','$desc[$x]',$price[$x],'$imageName[$x]','$store')";

                                                            if (mysqli_query($conn, $sql)=== TRUE) {

                                                                // do nothing
                                                            }
                                                            else {

                                                                echo mysql_error($conn);

                                                            }
                                                        }

                                                        ?>
                                                        <center style="margin-left:50px">
                                                        <div class="responsive" style="float:left; padding:10px">
                                                            <div class="img">
                                                                <a href='productDisplay.php?pageName="<?php  echo $imageName[$x]; ?>"'  >
                                                                    <img src="<?php echo $imageName[$x];?>"  width="200" height="200" />
                                                                </a>
                                                                <div class="product"> <b> <?php echo $pname[$x]; ?> </b><br>
                                                                    <i>Product Price: $ <?php echo $price[$x];?></i></div>
                                                            </div>
                                                        </div>
                                                            </center>
                                                    <?php }    $conn->close();?>
                            </div>


											
					</div>
				</section>
				
				<section>
					<div class="inner">
						<?php 
							$servername = "localhost";
							$username = "";
							$password = "";
							$dbname = "mktplace";
						?>
						<header class="major">
						<h2>Recently Visited</h2>
							</header>
								<div class="major">
										
													<?php
													
													
														if (isset($_COOKIE["zetaplot"]))
														{
															$i = 0;
															$temp = array();
															$cookie = $_COOKIE["zetaplot"];
															$cookie = explode(',',$cookie);
															$conn = new mysqli($servername, $username, $password, $dbname);
															if ($conn->connect_error) {
																die("Unable to connect Server: " . $conn->connect_error);
															}
															while ($i<5 && !empty($cookie))
															{
																$item = array_pop($cookie);
																if(!in_array($item,$temp))
																{
																		$sql = "select * from products where code = ".$item."";

																		$result = $conn->query($sql);
																		$row = $result->fetch_assoc();

																		if ($result->num_rows > 0) 
																		{
																			$pname = $row["name"];
																			$price = $row["price"];
																			$image = $row["image"];
																		}
																	?>
																	<center style="margin-left:50px">
																		<div class="responsive" style="float:left; padding:10px">
																			<div class="img">
																				<a href='productDisplay.php?pageName="<?php  echo $image; ?>"'  >
																					<img src="<?php echo $image;?>"  width="200" height="200" />
																				</a>
																			<div class="product"> <b> <?php echo $pname; ?> </b><br>
																					<i>Product Price: $ <?php echo $price;?></i></div>
																			</div>
																		</div>
																	</center>
																	<?php 
																		$i++;
																		array_push($temp, $item);
																}
															}
														}
														$conn->close();?>
                            </div>
					</div>
				</section>
				
				<section>
					<div class="inner">
						<?php 
							$servername = "localhost";
							$username = "";
							$password = "";
							$dbname = "mktplace";
						?>
						<header class="major">
						<h2>Most Visited</h2>
							</header>
								<div class="major">
										
													<?php
													
														if (isset($_COOKIE["zetaplot"]))
														{
															$i = 0;
															$cookie = $_COOKIE["zetaplot"];
															$cookie = explode(',',$cookie);
															$temp = array_count_values($cookie);
															arsort($temp);
															$temp1 = array_slice($temp, 0, 5, true);
															$conn = new mysqli($servername, $username, $password, $dbname);
															if ($conn->connect_error) {
																die("Unable to connect Server: " . $conn->connect_error);
															}
															foreach (array_keys($temp1) as $item)
															{
																$sql = "select * from products where code = ".$item."";

																$result = $conn->query($sql);
																$row = $result->fetch_assoc();

																if ($result->num_rows > 0) 
																{
																	$pname = $row["name"];
																	$price = $row["price"];
																	$image = $row["image"];
																}
																?>
																<center style="margin-left:50px">
																	<div class="responsive" style="float:left; padding:10px">
																		<div class="img">
																			<a href='productDisplay.php?pageName="<?php  echo $image; ?>"'  >
																				<img src="<?php echo $image;?>"  width="200" height="200" />
																			</a>
																		<div class="product"> <b> <?php echo $pname; ?> </b><br>
																				<i>Product Price: $ <?php echo $price;?></i></div>
																		</div>
																	</div>
																</center>
																<?php
															}
														}
														$conn->close();?>
                            </div>
					</div>
				</section>
            <section  style="margin-left:2px;">
			    <div class="inner">
			        <header class="major">
				    <h2>Top picks</h2>
				</header>
				  <p>
                      <?php 
                      
                      
                                $servername = "localhost";
                                $username = "";
                                $password = "";
                                $dbname = "mktplace";               
                            $conn = new mysqli($servername, $username, $password, $dbname);
    					   if ($conn->connect_error) 
    					   {
        					die("Connection failed: " . $conn->connect_error);
    					   } 
    					   $sql = "SELECT name, link, image,(review_total / review_count) as ratings FROM products WHERE store = 'zetaplot'order by ratings desc limit 5";
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