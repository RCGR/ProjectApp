
<?php
 
/*
 * Deze code geeft alle resultaten van een rij terug (in dit geval, alle namen en id's van ieder uitje)
 */
 
// Deze array gaat de JSON-response bevatten
$response = array();
 
// Hier includen we de db_connect.php
require_once 'db_connect.php';
 
// We maken verbinding met onze database
$db = new DB_CONNECT();
 
// We voeren een query uit, en gooien het resultaat in $result
$result = mysql_query("INSERT INTO Uitjes(Naam, Weertype, Beschrijving, Categorie, Email, Straat, Straatnummer, PostCode, Stad)
VALUES(NaamVar, WeerTypeVar, BeschrijvingVar, CategorieVar, EmailVar, StraatVar, StraatnummerVar, PostCodeVar, StadVar)") or die(mysql_error());

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