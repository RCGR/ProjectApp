-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Adres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Adres` (
  `ID` INT NOT NULL,
  `Straat` VARCHAR(45) NULL,
  `PostCode` VARCHAR(45) NULL,
  `Stad` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Uitjes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Uitjes` (
  `ID` INT NOT NULL,
  `Naam` VARCHAR(45) NOT NULL,
  `WeerType` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  `Adres_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_Uitjes_Adres_idx` (`Adres_ID` ASC),
  CONSTRAINT `fk_Uitjes_Adres`
    FOREIGN KEY (`Adres_ID`)
    REFERENCES `mydb`.`Adres` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
