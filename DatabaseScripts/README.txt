DB CREDENTIALS

UITJESDB:
EMAIL: coldfusiondata@gmail.com
EMAILWW: coldfusion

HOSTACCOUNTNAME: coldfusion
HOSTACCOUNTWW: coldfusion4
HOST URL: www.coldfusiondata.site90.net

DB HOSTNAME: mysql3.000webhost.com
DB NAME: a4934812_uitjes
DB USERNAME: a4934812_user
DB PASSWORD: coldfusion4
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