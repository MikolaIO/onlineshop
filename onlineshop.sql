-- MySQL Script generated by MySQL Workbench
-- Tue Jan 21 11:51:55 2025
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`table1`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`table1` ;

CREATE TABLE IF NOT EXISTS `mydb`.`table1` (
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Users` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Users` (
  `id` BIGINT(32) UNSIGNED NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `age` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Shipments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Shipments` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Shipments` (
  `id` BIGINT(32) UNSIGNED NOT NULL,
  `customer_id` BIGINT(32) UNSIGNED NOT NULL,
  `from` VARCHAR(45) NOT NULL,
  `to` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Shipments_Users1_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_Shipments_Users1`
    FOREIGN KEY (`customer_id`)
    REFERENCES `mydb`.`Users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Orders` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Orders` (
  `id` BIGINT(32) UNSIGNED NOT NULL,
  `customer_id` BIGINT(32) UNSIGNED NOT NULL,
  `shipment_id` BIGINT(32) UNSIGNED NOT NULL,
  `total_price` DOUBLE UNSIGNED NOT NULL,
  `payment_method` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Orders_Users_idx` (`customer_id` ASC) VISIBLE,
  INDEX `fk_Orders_Shipments1_idx` (`shipment_id` ASC) VISIBLE,
  CONSTRAINT `fk_Orders_Users`
    FOREIGN KEY (`customer_id`)
    REFERENCES `mydb`.`Users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Orders_Shipments1`
    FOREIGN KEY (`shipment_id`)
    REFERENCES `mydb`.`Shipments` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Storage`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Storage` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Storage` (
  `id` INT UNSIGNED NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Product` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Product` (
  `id` DECIMAL(32) NOT NULL,
  `name` VARCHAR(45) NULL,
  `order_id` BIGINT(32) UNSIGNED NOT NULL,
  `storage_id` INT UNSIGNED NOT NULL,
  `price` DOUBLE UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Product_Orders1_idx` (`order_id` ASC) VISIBLE,
  INDEX `fk_Product_Catalogue1_idx` (`storage_id` ASC) VISIBLE,
  CONSTRAINT `fk_Product_Orders1`
    FOREIGN KEY (`order_id`)
    REFERENCES `mydb`.`Orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Product_Catalogue1`
    FOREIGN KEY (`storage_id`)
    REFERENCES `mydb`.`Storage` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
