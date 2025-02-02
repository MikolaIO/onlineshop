-- MySQL Script generated by MySQL Workbench
-- Sun Feb  2 21:06:27 2025
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema onlineshop
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema onlineshop
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `onlineshop` DEFAULT CHARACTER SET utf8 ;
USE `onlineshop` ;

-- -----------------------------------------------------
-- Table `onlineshop`.`Addresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onlineshop`.`Addresses` ;

CREATE TABLE IF NOT EXISTS `onlineshop`.`Addresses` (
  `id` INT NOT NULL,
  `street` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `postal_code` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onlineshop`.`Users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onlineshop`.`Users` ;

CREATE TABLE IF NOT EXISTS `onlineshop`.`Users` (
  `id` BIGINT(32) UNSIGNED NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `age` INT UNSIGNED NOT NULL,
  `address_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Users_Addresses1_idx` (`address_id` ASC) VISIBLE,
  CONSTRAINT `fk_Users_Addresses1`
    FOREIGN KEY (`address_id`)
    REFERENCES `onlineshop`.`Addresses` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onlineshop`.`Orders`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onlineshop`.`Orders` ;

CREATE TABLE IF NOT EXISTS `onlineshop`.`Orders` (
  `id` BIGINT(32) UNSIGNED NOT NULL,
  `total_price` DOUBLE UNSIGNED NOT NULL,
  `customer_id` BIGINT(32) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Orders_Users_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_Orders_Users`
    FOREIGN KEY (`customer_id`)
    REFERENCES `onlineshop`.`Users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onlineshop`.`Sellers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onlineshop`.`Sellers` ;

CREATE TABLE IF NOT EXISTS `onlineshop`.`Sellers` (
  `id` BIGINT(32) UNSIGNED NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `address_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Storage_Addresses1_idx` (`address_id` ASC) VISIBLE,
  CONSTRAINT `fk_Sellers_Addresses1`
    FOREIGN KEY (`address_id`)
    REFERENCES `onlineshop`.`Addresses` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onlineshop`.`Products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onlineshop`.`Products` ;

CREATE TABLE IF NOT EXISTS `onlineshop`.`Products` (
  `id` DECIMAL(32) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `price` DOUBLE UNSIGNED NOT NULL,
  `seller_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Product_Catalogue1_idx` (`seller_id` ASC) VISIBLE,
  CONSTRAINT `fk_Product_Catalogue1`
    FOREIGN KEY (`seller_id`)
    REFERENCES `onlineshop`.`Sellers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onlineshop`.`Credentials`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onlineshop`.`Credentials` ;

CREATE TABLE IF NOT EXISTS `onlineshop`.`Credentials` (
  `id` BIGINT(32) UNSIGNED NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `mail` VARCHAR(30) NOT NULL,
  `user_id` BIGINT(32) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Credentials_Users1_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_Credentials_Users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `onlineshop`.`Users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onlineshop`.`Payments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onlineshop`.`Payments` ;

CREATE TABLE IF NOT EXISTS `onlineshop`.`Payments` (
  `id` INT UNSIGNED NOT NULL,
  `payment_method` VARCHAR(10) NOT NULL,
  `pay_status` TINYINT NOT NULL,
  `order_id` BIGINT(32) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Payments_Orders1_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `fk_Payments_Orders1`
    FOREIGN KEY (`order_id`)
    REFERENCES `onlineshop`.`Orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onlineshop`.`Shipments`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onlineshop`.`Shipments` ;

CREATE TABLE IF NOT EXISTS `onlineshop`.`Shipments` (
  `product_id` DECIMAL(32) NOT NULL,
  `order_id` BIGINT(32) UNSIGNED NOT NULL,
  `from` VARCHAR(45) NOT NULL,
  `to` VARCHAR(45) NOT NULL,
  `sent_status` TINYINT NOT NULL,
  PRIMARY KEY (`product_id`, `order_id`),
  INDEX `fk_Product_has_Orders_Orders1_idx` (`order_id` ASC) VISIBLE,
  INDEX `fk_Product_has_Orders_Product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_Product_has_Orders_Product1`
    FOREIGN KEY (`product_id`)
    REFERENCES `onlineshop`.`Products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Product_has_Orders_Orders1`
    FOREIGN KEY (`order_id`)
    REFERENCES `onlineshop`.`Orders` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onlineshop`.`Reviews`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onlineshop`.`Reviews` ;

CREATE TABLE IF NOT EXISTS `onlineshop`.`Reviews` (
  `id` BIGINT(32) NOT NULL,
  `product_id` DECIMAL(32) NULL,
  `review_text` VARCHAR(300) NULL,
  `review_rating` ENUM('1', '2', '3', '4', '5') NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Reviews_Product1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_Reviews_Products1`
    FOREIGN KEY (`product_id`)
    REFERENCES `onlineshop`.`Products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onlineshop`.`Discounts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onlineshop`.`Discounts` ;

CREATE TABLE IF NOT EXISTS `onlineshop`.`Discounts` (
  `id` BIGINT(32) NOT NULL,
  `percentage` INT NOT NULL,
  `product_id` DECIMAL(32) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Discounts_Products1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_Discounts_Products1`
    FOREIGN KEY (`product_id`)
    REFERENCES `onlineshop`.`Products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onlineshop`.`Categories`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onlineshop`.`Categories` ;

CREATE TABLE IF NOT EXISTS `onlineshop`.`Categories` (
  `id` INT NOT NULL,
  `name` VARCHAR(10) NOT NULL,
  `description` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onlineshop`.`Categories_Products`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onlineshop`.`Categories_Products` ;

CREATE TABLE IF NOT EXISTS `onlineshop`.`Categories_Products` (
  `category_id` INT NOT NULL,
  `product_id` DECIMAL(32) NOT NULL,
  PRIMARY KEY (`category_id`, `product_id`),
  INDEX `fk_Categories_has_Products_Products1_idx` (`product_id` ASC) VISIBLE,
  INDEX `fk_Categories_has_Products_Categories1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_Categories_has_Products_Categories1`
    FOREIGN KEY (`category_id`)
    REFERENCES `onlineshop`.`Categories` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Categories_has_Products_Products1`
    FOREIGN KEY (`product_id`)
    REFERENCES `onlineshop`.`Products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onlineshop`.`Favorites`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onlineshop`.`Favorites` ;

CREATE TABLE IF NOT EXISTS `onlineshop`.`Favorites` (
  `product_id` DECIMAL(32) NOT NULL,
  `user_id` BIGINT(32) UNSIGNED NOT NULL,
  `added_date` DATE NOT NULL,
  PRIMARY KEY (`product_id`, `user_id`),
  INDEX `fk_Products_has_Users_Users1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_Products_has_Users_Products1_idx` (`product_id` ASC) VISIBLE,
  CONSTRAINT `fk_Products_has_Users_Products1`
    FOREIGN KEY (`product_id`)
    REFERENCES `onlineshop`.`Products` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Products_has_Users_Users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `onlineshop`.`Users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `onlineshop`.`Messages`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `onlineshop`.`Messages` ;

CREATE TABLE IF NOT EXISTS `onlineshop`.`Messages` (
  `seller_id` INT UNSIGNED NOT NULL,
  `user_id` BIGINT(32) UNSIGNED NOT NULL,
  `message_text` VARCHAR(45) NOT NULL,
  `date_sent` DATETIME NOT NULL,
  PRIMARY KEY (`seller_id`, `user_id`),
  INDEX `fk_Sellers_has_Users_Users1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_Sellers_has_Users_Sellers1_idx` (`seller_id` ASC) VISIBLE,
  CONSTRAINT `fk_Sellers_has_Users_Sellers1`
    FOREIGN KEY (`seller_id`)
    REFERENCES `onlineshop`.`Sellers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Sellers_has_Users_Users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `onlineshop`.`Users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
