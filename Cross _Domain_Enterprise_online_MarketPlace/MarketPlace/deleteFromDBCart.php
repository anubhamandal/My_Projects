<?php
extract($_POST);
session_start();
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

$cid =$_POST['cartid'];
$sql= " Delete from Cart where Cart_id  =$cid";
if (mysql_query($sql, $conn) === FALSE) {
    echo mysql_error($conn);
}
else{
    include 'DisplayCart.php';
}
mysql_close($conn);
?>
