
<?php
 
/*
 * Deze code geeft alle resultaten van een rij terug (in dit geval, alle namen en id's van ieder uitje)
 */

//Alle GET-vars
$NaamVar = $_GET["NaamVar"];

// Deze array gaat de JSON-response bevatten
$response = array();
 
// Hier includen we de db_connect.php
require_once 'db_connect_suggestion.php';
 
// We maken verbinding met onze database
$db = new DB_CONNECT();
 
// We voeren een query uit, en gooien het resultaat in $result
$result = mysql_query(DELETE FROM Uitjes WHERE Naam = ".$NaamVar.") or die(mysql_error());

   $response["Uitjes"] = array();
   
   $response["Response"] = $row["ResponseRow"];
       
    // Hier pushen we alle rijen in de vader-array 'Uitjes'.
    array_push($response["UitjesDetails"], $uitje);
    
    
    if ($conn->query($sql) === TRUE)
    {
        $response["Response"] = "Remove was succesfull!";
    } 
    else
    {
        $response["Response"] = "Remove failed.";
    }
    echo json_encode($response);
    

?>