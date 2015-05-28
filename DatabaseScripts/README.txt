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

---------------------------

VOORGESTELDE UITJES DB: 
EMAIL: coldfusiondata@gmail.com
EMAILWW: coldfusion

HOSTACCOUNTNAME: coldfusion
HOSTACCOUNTWW: coldfusion4
HOST URL: www.coldfusiondata.site90.net

DB HOSTNAME: mysql3.000webhost.com
DB NAME: a4934812_vuitjes
DB USERNAME: a4934812_user2
DB PASSWORD: coldfusion4

--------

WEER DB:
EMAIL: coldfusiondata@gmail.com
EMAILWW: coldfusion

HOSTACCOUNTNAME: a9489456
HOSTACCOUNTWW: coldfusion4
HOST URL: coldfusiondataweather.comule.com

DB HOSTNAME: mysql3.000webhost.com
DB NAME: a4934812_weer
DB USERNAME: a4934812_user3
DB PASSWORD: coldfusion4

---------

Tabellen: Uitjes: 
`uitjesID` INT NOT NULL AUTO_INCREMENT,
  
`Naam` VARCHAR(45) NOT NULL,
  
`WeerType` VARCHAR(45) NOT NULL,
  
`Email` VARCHAR(45) NULL,
  
`Straat` VARCHAR(45) NOT NULL,
  
`PostCode` VARCHAR(45) NOT NULL,
  
`Stad` VARCHAR(45) NOT NULL,

`Beschrijving` VARCHAR(45) NOT NULL,

`Categorie` VARCHAR(45) NOT NULL,

`Coordinaat` VARCHAR(45) NOT NULL,

<!-- de tabellen voor de voorgestelde uitjes DB is exact hetzelfde :) -->

Weer:

`weerID` INT NOT NULL AUTO_INCREMENT,
  
`weerType` VARCHAR(45) NOT NULL,
  
`timePulled` VARCHAR(45) NOT NULL,