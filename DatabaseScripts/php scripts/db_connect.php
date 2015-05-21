<?php
 
/**
 * Deze klasse connect met de database, met de variabelen in db_config.php
 */
class DB_CONNECT {
 
    // constructor
    function __construct() {
        // De constructor callt meteen de connect methode
        $this->connect();
    }
 
    // destructor
    function __destruct() {
        // De desctructor callt de close methode en sluit de verbinding dus af
        $this->close();
    }
 
    /**
     * Deze methode handelt het daadwerkelijkke connecten af
     */
    function connect() {
        // hier importeren we het variabelen bestand
        require_once 'db_config.php';
 
        // hier verbinden we met de Database server
        $con = mysql_connect(UITJES_DB_SERVER, UITJES_DB_USER, UITJES_DB_PASSWORD) or die(mysql_error());
 
        // Hier selecteren we onze database
        $db = mysql_select_db(UITJES_DB_DATABASE) or die(mysql_error()) or die(mysql_error());
 
        // returing connection cursor
        return $con;
    }
 
    /**
     * Function to close db connection
     */
    function close() {
        // closing db connection
        mysql_close();
    }
 
}
 
?>