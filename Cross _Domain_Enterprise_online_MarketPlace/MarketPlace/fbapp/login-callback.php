<?php
session_start();
require_once __DIR__ . '/src/Facebook/autoload.php';

$fb = new Facebook\Facebook([
  'app_id' => '',
  'app_secret' => '',
  'default_graph_version' => 'v2.8'
]);

$helper = $fb->getJavaScriptHelper();

try {
  $accessToken = $helper->getAccessToken();
  } catch(Facebook\Exceptions\FacebookResponseException $e) {
    // When Graph returns an error
    echo 'Graph returned an error: ' . $e->getMessage();
} catch(Facebook\Exceptions\FacebookSDKException $e) {
    // When validation fails or other local issues
    echo 'Facebook SDK returned an error: ' . $e->getMessage();
}

if (isset($accessToken)) {
   $fb->setDefaultAccessToken($accessToken);

  try {
  
    $requestProfile = $fb->get("/me?fields=name,first_name,last_name,email");
    $profile = $requestProfile->getGraphNode()->asArray();
  } catch(Facebook\Exceptions\FacebookResponseException $e) {
    // When Graph returns an error
    echo 'Graph returned an error: ' . $e->getMessage();
  } catch(Facebook\Exceptions\FacebookSDKException $e) {
    // When validation fails or other local issues
    echo 'Facebook SDK returned an error: ' . $e->getMessage();
  }

    $_SESSION["uname"] = $profile['name'];
   $fname= $profile['first_name']; $lname= $profile['last_name'];$address=" ";$emailId= $profile['email'];
      $hphone="";$cphone=""; $uname=$profile['name']; $passwd=$profile['first_name'];  
        
    		$servername = "";
    		$username = "";
    		$password = "";
    		$dbname = "";
             $conn = new mysqli($servername, $username, $password, $dbname);
                $msg= "Connected successfully"; 
                if ($conn->connect_error) {
                    die("Connection failed: " . $conn->connect_error);
                } 

                $sql1="select USERNAME from user where username='$uname'";
                $result= $conn->query($sql1);
                if ($result->num_rows > 0) {
                    header('location: ../');
                }
                else {
                    $sql = "INSERT INTO user(FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, HOME_PHONE, CELL_PHONE, USERNAME, PASSWORD) VALUES  ('$fname', '$lname', '$emailId', '$address', '$hphone', '$cphone', '$uname', '$passwd')";

                    $result = $conn->query($sql);
                    if ($result == FALSE) {

                        exit();
                    } else {

                    }
                }
                $conn->close();
            
  header('location: ../');
  exit;
} else {
    echo "Unauthorized access!!!";
    exit;
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
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<meta content="utf-8" http-equiv="encoding">
</head>
</html>
