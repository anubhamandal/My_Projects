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
    	// define variables and set to empty values
        $fnameErr = $lnameErr = $emailIdErr = $hphoneErr = $cphoneErr = $unameErr = $passwdErr = $unameExistsErr = $msg ="";
        $fname = $lname = $emailId = $address = $hphone = $cphone = $uname = $passwd ="";
        $dbrow = "";
    
        $servername = "localhost";
        $username = "";
        $password = "";
        $dbname = "mktplace";

        if ($_SERVER["REQUEST_METHOD"] == "POST") {
    
            if (empty($_POST["firstname"])) {
                $fnameErr = "required.";
            } else {
                $fname = test_input($_POST["firstname"]);
            }
      
            if (empty($_POST["lastname"])) {
                $lnameErr = "required.";
            } else {
                $lname = test_input($_POST["lastname"]);
            }
      
            if (empty($_POST["email"])) {
                $emailIdErr = "required.";
            } else {
                $emailId = test_input($_POST["email"]);
            }
      
            if (empty($_POST["address"])) {
                $address = "";
            } else {
                $address = test_input($_POST["address"]);
            }

            if (empty($_POST["homephone"])) {
                $hphoneErr = "required.";
            } else {
                $hphone = test_input($_POST["homephone"]);
            }
      
            if (empty($_POST["cellphone"])) {
                $cphoneErr = "required.";
            } else {
                $cphone = test_input($_POST["cellphone"]);
            }

            if (empty($_POST["username"])) {
                $unameErr = "required.";
            } else {
                $uname = test_input($_POST["username"]);
            }

            if (empty($_POST["password"])) {
                $passwdErr = "required.";
            } else {
                $passwd = test_input($_POST["password"]);
            }
      
            if ($uname != '' && $passwd != '') {
        
                $conn = new mysqli($servername, $username, $password, $dbname);
                $msg= "Connected successfully"; 
                if ($conn->connect_error) {
                    die("Connection failed: " . $conn->connect_error);
                } 

                $sql = "INSERT INTO user(FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, HOME_PHONE, CELL_PHONE, USERNAME, PASSWORD) VALUES  ('$fname', '$lname', '$emailId', '$address', '$hphone', '$cphone', '$uname', '$passwd')";

                if ($conn->query($sql) === TRUE) {
                    echo "New record created successfully";
                } else {
                    echo "Error: " . $sql . "<br>" . $conn->error;
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

            
	<p><span class="error"><?php echo $unameExistsErr;?></span></p>
	<div id="main">
	    <section>
		<form method="post" action="newuser.php">
					
		    <div class="field half">
			<label for="firstname">First Name</label>
			<input type="text" name="firstname" id="firstname"  placeholder="first name " required/>
		    </div>
		    <div class="field half">
			<label for="lastname">Last Name</label>
			<input type="text" name="lastname" required placeholder="last name">
		    </div>
		    <div class="field half">
			<label for="address">Address</label>
			<input type="text" name="address" id="address"  placeholder="Type in an address" />
		    </div>
		    <div class="field half">
			<label for="email">Email Id</label>
			<input type="email" name="email" required placeholder="email" required>
		    </div>
		    <div id="map"></div>
		    <div class="field half">
			<label for="cellphone">Cell Phone</label>
			<input type="text" name="cellphone" id="cellphone"  placeholder="cellphone "/>
		    </div>
		    <div class="field half">
			<label for="homephone">Home Phone</label>
			<input type="text" name="homephone" required placeholder="email" required>
		    </div>
		    <div class="field half">
			<label for="username">User Name</label>
			<input type="text" name="username" id="username"  placeholder="user name " required/>
		    </div>
		    <div class="field half">
			<label for="password">password</label>
			<input type="password" name="password" required placeholder="password">
		    </div>			
		    <ul class="actions">
			<li><input type="submit" value="Create User" class="special" name="submit"/></li>
			<li><input type="reset" value="Clear" /></li>
			<li><h5><a href="login.html">Go to Login</a></h5></li>
		    </ul>
		</form>
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