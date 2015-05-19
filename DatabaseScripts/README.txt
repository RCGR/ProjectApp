DB CREDENTIALS

UITJESDB:
Host: sql5.freemysqlhosting.net
Database name: sql577884
Database user: sql577884
Database password: hC6*bN8!
Port number: 3306
--------
Tabellen: Uitjes: 
`uitjesID` INT NOT NULL AUTO_INCREMENT,
  
`Naam` VARCHAR(45) NOT NULL,
  
`WeerType` VARCHAR(45) NOT NULL,
  
`Email` VARCHAR(45) NULL,
  
`Adres_ID` INT NOT NULL,
Adres:
`ID` INT NOT NULL AUTO_INCREMENT,
  
`Straat` VARCHAR(45) NOT NULL,
  
`PostCode` VARCHAR(45) NOT NULL,
  
`Stad` VARCHAR(45) NOT NULL,