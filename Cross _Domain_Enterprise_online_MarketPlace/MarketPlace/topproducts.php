<style>
div.img {
    border: 1px solid #ccc;
}

div.img:hover {
    border: 1px solid #777;
}

div.img img {
    width: 100%;
    height: 100%;
}

div.desc {
    padding: 2px;
    text-align: center;
}

* {
    box-sizing: border-box;
}
.responsive {
    padding: 0 6px;
    float: left;
    width: 200px;
    height: 400px
}

@media only screen and (max-width: 700px){
    .responsive {
        width: 49.99999%;
        margin: 6px 0;
    }
}

@media only screen and (max-width: 500px){
    .responsive {
        width: 100%;
    }
}

.clearfix:after {
    content: "";
    display: table;
    clear: both;
}

</style>
<?php
    session_start();
    $servername = "localhost";
    $username = "";
    $password = "";
    $dbname = "mktplace";
    
    $conn = new mysqli($servername, $username, $password, $dbname);
    $msg= "Connected successfully"; 
    if ($conn->connect_error) 
    {
        die("Connection failed: " . $conn->connect_error);
    } 

    $sql = "SELECT name, link, image, price,(review_total / review_count) as ratings FROM products WHERE store = 'bookstore'order by ratings desc limit 5";

    $result = $conn->query($sql);
    
    $conn->close();
    
?>
<table style="width:30%">
        <tr>
            <th>Top 5 Best reviewed products for bookstore</th><th>Price</th><th>Review</th>
        </tr>
        <?php
         if ($result->num_rows > 0) {
           // output data of each row
           while($row = $result->fetch_assoc()) {
             $name = $row["name"];
             $link = $row["link"];
             $image = $row["image"];
             $price = $row["price"];
             $ratings = $row["ratings"];
             echo "<tr><td><a href='" . $link.  "'>" . $name."</a></td><td>". $price. "</td><td>" . $ratings . "</td></tr>";
           }
         } else {
             echo "<tr>". "0 rows ". "</tr>";
         }
       ?>
</table>
