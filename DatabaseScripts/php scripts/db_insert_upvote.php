<?php

/*
*
* Dit script insert een nieuwe upvote 
*
*/

// Deze array gaat de JSON-response bevatten
$response = array();
 
// Hier includen we de db_connect.php
require_once 'db_connect_suggestion.php';
 
// We maken verbinding met onze database
$db = new DB_CONNECT();

$id = $_GET["id"];

//We verhogen de upVoteCount met 1
$result = mysql_query("UPDATE Uitjes SET upVoteCount = upVoteCount + 1 WHERE uitjesID = ".$id."") or die(mysql_error());

   $response["Uitjes"] = array();
   
   $response["Response"] = $row["ResponseRow"];
       
    // Hier pushen we alle rijen in de vader-array 'Uitjes'.
    array_push($response["UitjesDetails"], $uitje);
    
    if ($conn->query($sql) === TRUE)
    {
        $response["Response"] = "Insert was succesfull!";
    } 
    else
    {
        $response["Response"] = "Insert failed.";
    }
    echo json_encode($response);
    
?>