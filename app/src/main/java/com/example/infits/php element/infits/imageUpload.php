<?php
require "connect.php";

$clientuserID = $_POST['clientuserID'];
$imageData = $_POST['imageData'];

// $clientuserID = 'dev';
// $imageData="iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAACvklEQVR42mJ8X2TEURQFnwAQwA1r4O4jgAHEgBOBOfgAfDwAuDTEA6LYAxEHxAZ0FHYDgA1ZzwzC4B0gE+Kl5iIAGFgz4DYEKcTkHArAArOQAuNACWQj4A6QrB7COIAJiM+QHYCAyH4AZQDzDK5ADMB/ApgANsD2YABjhI+QHYDAyH4gBnBP9gAHEAxAZ0AHkxAGQDxALsADiBM+ACsAzgAHEA4DK5AJkA5wAHEAPsADyBP4A6wCtAAM3hOyAP0rGk+AeV4ByEAnUB+gkEMDGhCfABz0D2S4BxIAjADnALRAOaQBeVPIM7gOJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLMByQAvMI8yA9QDPzII0ADnALTAZM7gPog3gKvQDmAM7gQogBzIAJiAjkByQDjAHNQDiACJwCDACPMgLM";

// Decode the Base64 data
$decodedImage = base64_decode($imageData);

$sql = "UPDATE client SET profilePhoto='$imageData' WHERE clientuserID='$clientuserID'";

if ($conn->query($sql) === TRUE) {
    echo "Successfully uploaded";
} else {
    echo "Error updating record: " . $conn->error;
}

$conn->close();
?>