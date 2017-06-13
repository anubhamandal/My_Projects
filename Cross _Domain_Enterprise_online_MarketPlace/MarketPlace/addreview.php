<?php

define('DB_NAME', 'yogausers');
define('DB_USER', '');
define('DB_PASSWORD', '');
define('DB_HOST', '');

$link = mysql_connect(DB_HOST, DB_USER, DB_PASSWORD);

if(!link) {
    die('Could not connect to database: '.mysql_error());
    }
    
$db_selected = mysql_select_db(DB_NAME, $link);

if(!db_selected) {
    die('Cant use ' . DB_NAME . ': ' . mysql_error());
}

$value1 = $_POST['Firstname'];
$value2 = $_POST['Lastname'];
$value3 = $_POST['Email'];
$value4 = $_POST['Address'];
$value5 = $_POST['Homephone'];
$value6 = $_POST['Cellphone'];

$sql = "INSERT INTO Users VALUES ('$value1','$value2','$value3','$value4','$value5','$value6')";

if(!mysql_query($sql)) {
    die('Error' .mysql_error());
}
include('user_add.html');

mysql_close();

?>