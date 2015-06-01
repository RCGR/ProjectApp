
<?php
 
/*
 * Deze code geeft alle resultaten van een rij terug (in dit geval, alle namen en id's van ieder uitje)
 */
 
// Deze array gaat de JSON-response bevatten
$response = array();
 
// Hier includen we de db_connect.php
require_once 'db_connect_suggestion.php';
 
// We maken verbinding met onze database
$db = new DB_CONNECT();
 
// We voeren een query uit, en gooien het resultaat in $result
$result = mysql_query("SELECT * FROM Uitjes") or die(mysql_error());
 
// We kijken of we een resultaat krijgen, zo ja:
if (mysql_num_rows($result) > 0)
{
    // En maken een sub-array in de $response-array aan, die alle resultaten uit de tabel Uitjes gaat bevatten

    $response["Uitjes"] = array();

    // We loopen door alle resultaten
    while ($row = mysql_fetch_array($result))
    {
        // Hier maken we weer een subarray voor iedere rij van ieder resultaat.
        // Voor nu halen we alleen UitjesID en Naam op.
        $uitje = array();
        $uitje["uitjesID"] = $row["uitjesID"];
        $uitje["Naam"] = $row["Naam"];
 
        // Hier pushen we alle rijen in de vader-array 'Uitjes'.
        array_push($response["Uitjes"], $uitje);
    }

    // Succes! We geven de succes-rij in de JSON-array code 1.
    $response["success"] = 1;
 
    // We outputten de JSON response.
    echo json_encode($response);

}

else
{
    // Als er geen uitjes gevonden zijn, koppelen we dat terug
    $response["success"] = 0;
    $response["message"] = "Geen resultaten gevonden";
 
    // echo no users JSON
    echo json_encode($response);
}
?>