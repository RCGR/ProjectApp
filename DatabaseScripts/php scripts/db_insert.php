
<?php
 
/*
 * Deze code geeft alle resultaten van een rij terug (in dit geval, alle namen en id's van ieder uitje)
 */

//Alle GET-vars
$NaamVar = $_GET["NaamVar"];
$BeschrijvingVar = $_GET["BeschrijvingVar"];
$CategorieVar = $_GET["BeschrijvingsVar"];
$EmailVar = $_GET["EmailVar"];
$StraatVar = $_GET["StraatVar"];
$PostCodeVar = $_GET["PostCodeVar"];
$StadVar = $_GET["StadVar"];
$CoordinaatVar = $_GET["CoordinaatVar"];


 
// Deze array gaat de JSON-response bevatten
$response = array();
 
// Hier includen we de db_connect.php
require_once 'db_connect.php';
 
// We maken verbinding met onze database
$db = new DB_CONNECT();

//We vervangen de plusjes die we in de url krijgen terug naar spaties
str_replace("+", " ", $BeschrijvingsVar);

// We voeren een query uit, en gooien het resultaat in $result
$result = mysql_query("INSERT INTO Uitjes(Naam, Weertype, Beschrijving, Categorie, Email, Straat, PostCode, Stad, Coordinaat)
VALUES(".$NaamVar.", ".$BeschrijvingsVar.", ".$CategorieVar.", ".$EmailVar." ".$StraatVar.", ".$PostCodeVar.", ".$StadVar.", ".$CoordinaatVar.")") or die(mysql_error());

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