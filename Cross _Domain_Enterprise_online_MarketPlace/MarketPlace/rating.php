<?php

    session_start();
    $servername = "localhost";
    $username = "";
    $password = "";
    $dbname = "mktplace";
    
    $conn = new mysqli($servername, $username, $password, $dbname);
    if ($conn->connect_error) {
        die("Unable to connect Server: " . $conn->connect_error);
    }

    if(empty($_POST['user'])) 
    {
    	echo "LOGIN";
    }
    else if (isset($_POST['rate']) && !empty($_POST['rate']) && isset($_POST['user']) && !empty($_POST['user']) 
        && isset($_POST['code']) && !empty($_POST['code']) && isset($_POST['store']) && !empty($_POST['store'])) 
    {

        $rate = $conn->real_escape_string($_POST['rate']);
    	$user = $conn->real_escape_string($_POST['user']);
    	$code = $conn->real_escape_string($_POST['code']);
    	$store = $conn->real_escape_string($_POST['store']);
    	$comment = $conn->real_escape_string($_POST['comment']);
    	
    	// check if user has already rated
    	$sql = "SELECT id FROM prodreview WHERE user='" . $user . "' and code='" . $code . "' and store='" . $store . "'";
    	$result = $conn->query($sql);
    	$row = $result->fetch_assoc();
    	if ($result->num_rows > 0) {
            echo $row['id'];
    	} else {
            $sql = "INSERT INTO prodreview ( code, store, user, review, comments) VALUES ('" . $code . "', '" . $store . "', '" . $user . "','" . $rate . "','" . $comment . "')";
            if (mysqli_query($conn, $sql)) {
        	$sql = "UPDATE products SET review_count = review_count +1, review_total = review_total + " . $rate . " WHERE code = '" . $code ."' AND store = '". $store ."'";
        	if(mysqli_query($conn, $sql)) {
            		echo "0";
            	} else {
            		echo "-1";
            	}
            }
    	}
    }
    $conn->close();
?>