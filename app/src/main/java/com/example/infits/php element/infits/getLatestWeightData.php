<?php

$conn=new mysqli("www.db4free.net","infits_free_test","EH6.mqRb9QBdY.U","infits_db");

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$clientID = $_POST['clientID'];
$today = date('Y-m-d H:i:s');
$from=date('Y-m-d 00:00:00',strtotime("-1 days",strtotime($today)));
//$date = date("Y-m-d", strtotime($dateandtime));
$sql = "select weight,goal,bmi from weighttracker where clientuserID = '$clientID' and dateandtime between '$from' and '$today' order by dateandtime DESC";
$result = mysqli_query($conn, $sql);


$full = array();

while ($row = mysqli_fetch_assoc($result)) {
    $full['weight'] = $row['weight'];
    $full['goal'] = $row['goal'];
    $full['bmi'] = $row['bmi'];

}

echo json_encode(['weight' => $full]);

?>
