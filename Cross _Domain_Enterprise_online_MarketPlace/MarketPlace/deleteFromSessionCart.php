<?php
extract($_POST);
session_start();
require('CartItem.php');
$pid = $_POST['pid'];
ini_set('display_errors', 'on');
if ($pid!=null || isset($_POST['pid'])) {

    unset($_SESSION['myCart'][$pid]);

    $_SESSION['myCart'] = array_values($_SESSION['myCart']);
}
else{
    echo "none to delete";
}
    include 'DisplayCart.php';

?>