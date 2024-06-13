-- -----------------------------------------------------
-- Schema Configuration
-- -----------------------------------------------------
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
SET @OLD_TIME_ZONE=@@TIME_ZONE, TIME_ZONE='+02:00';
-- -----------------------------------------------------
-- Schema businessTrip_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS businessTrip_db;
CREATE SCHEMA IF NOT EXISTS businessTrip_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE businessTrip_db;
-- -----------------------------------------------------
-- Table businessTrip_db.user
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS businessTrip_db.user; 
CREATE TABLE IF NOT EXISTS businessTrip_db.user (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
    UNIQUE INDEX username_UNIQUE (username ASC) VISIBLE,
    UNIQUE INDEX email_UNIQUE (email ASC) VISIBLE
    ) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table businessTrip_db.role
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS businessTrip_db.role; 
CREATE TABLE IF NOT EXISTS businessTrip_db.role (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
    UNIQUE INDEX name_UNIQUE (name ASC) VISIBLE
    ) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table businessTrip_db.trip
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS businessTrip_db.trip;
CREATE TABLE IF NOT EXISTS businessTrip_db.trip (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    budget FLOAT NOT NULL,
    details VARCHAR(4069) NOT NULL,
    destination VARCHAR(255) NOT NULL,
    start_at TIMESTAMP NOT NULL,
    end_at TIMESTAMP NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE
    ) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table businessTrip_db.user_role
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS user_role;
CREATE TABLE IF NOT EXISTS businessTrip_db.user_role (
    user_id BIGINT NOT NULL,
    role_id INT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    INDEX fk_user_role_user_id_idx (user_id ASC) VISIBLE,
    INDEX fk_user_role_role_id_idx (role_id ASC) VISIBLE,
    CONSTRAINT fk_user_role_user_id
    FOREIGN KEY (user_id)
    REFERENCES businessTrip_db.user (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
    CONSTRAINT fk_user_role_role_uuid
    FOREIGN KEY (role_id)
    REFERENCES businessTrip_db.role (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
    ) ENGINE = InnoDB;
-- -----------------------------------------------------
-- Table businessTrip_db.user_trip
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS user_role;
CREATE TABLE IF NOT EXISTS businessTrip_db.user_trip (
    user_id BIGINT NOT NULL,
    trip_id INT NOT NULL,
    PRIMARY KEY (user_id, trip_id),
    INDEX fk_user_trip_user_id_idx (user_id ASC) VISIBLE,
    INDEX fk_user_trip_trip_id_idx (trip_id ASC) VISIBLE,
    CONSTRAINT fk_user_trip_user_id
    FOREIGN KEY (user_id)
    REFERENCES businessTrip_db.user (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
    CONSTRAINT fk_user_role_trip_uuid
    FOREIGN KEY (trip_id)
    REFERENCES businessTrip_db.trip (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
    ) ENGINE = InnoDB;
-- -----------------------------------------------------
-- procedure GrantPermissions
-- -----------------------------------------------------
DELIMITER $$
USE businessTrip_db$$
DROP PROCEDURE IF EXISTS GrantPermissions$$
CREATE PROCEDURE GrantPermissions(IN p_username VARCHAR(256), IN p_permission VARCHAR(64))
BEGIN
	SET @dcl = CONCAT('GRANT ', p_permission, ' ON businessTrip_db.* TO ', p_username, '@localhost');
PREPARE stmt FROM @dcl;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
END;$$
DELIMITER ;
-- -----------------------------------------------------
-- procedure InsertUserWithRoleAndGrantPermissions
-- -----------------------------------------------------
DELIMITER $$
USE businessTrip_db$$
DROP PROCEDURE IF EXISTS InsertUserWithRoleAndGrantPermissions$$
CREATE PROCEDURE InsertUserWithRoleAndGrantPermissions(
    IN p_username VARCHAR(256),
    IN p_email VARCHAR(256),
    IN p_password VARCHAR(256),
    IN p_role VARCHAR(256))
BEGIN
	DECLARE permissions VARCHAR(255);
    DECLARE user_id BIGINT;
	DECLARE role_id INT;

INSERT INTO businessTrip_db.user(username, email, password)
VALUES
    (p_username, p_email, p_password);

SET user_id = (SELECT id FROM user WHERE username = p_username);
	SET role_id = (SELECT id FROM role WHERE SOUNDEX(name) = SOUNDEX(p_role));

	IF role_id IS NULL THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Role does not exist!';
ELSE
		INSERT INTO businessTrip_db.user_role(user_id, role_id)
		VALUES
			(user_id, role_id);
END IF;

	IF p_role LIKE '%ADMIN' OR p_role LIKE '%admin' THEN
        SET permissions = 'ALL PRIVILEGES';
    ELSEIF p_role LIKE '%MODERATOR' OR p_role LIKE '%moderator' THEN
        SET permissions = 'SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER, CREATE TEMPORARY TABLES, LOCK TABLES';
    ELSEIF p_role LIKE '%USER' OR p_role LIKE '%user' THEN
        SET permissions = 'SELECT, INSERT, UPDATE, DELETE';
ELSE
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid Role!';
END IF;

    -- Create the user and grant permissions
    SET @s = CONCAT('CREATE USER IF NOT EXISTS ', p_username, ' IDENTIFIED BY ', QUOTE(p_password));
PREPARE stmt FROM @s;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

IF permissions <> '' THEN
		SET @s = CONCAT('GRANT ', permissions, ' ON businessTrip_db.* TO ', QUOTE(p_username));
PREPARE stmt FROM @s;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
END IF;

    FLUSH PRIVILEGES;
END;$$
DELIMITER ;      
-- -----------------------------------------------------
-- procedure InsertDefaultRoles
-- -----------------------------------------------------       
DELIMITER $$
USE businessTrip_db$$
DROP PROCEDURE IF EXISTS InsertDefaultRoles$$
CREATE PROCEDURE InsertDefaultRoles()
BEGIN
DELETE FROM businessTrip_db.role WHERE id != 0;
INSERT INTO role(name) VALUES('ROLE_USER');
INSERT INTO role(name) VALUES('ROLE_MODERATOR');
INSERT INTO role(name) VALUES('ROLE_ADMIN');
END;$$
DELIMITER ;
-- -----------------------------------------------------
-- view user_role_view 
-- -----------------------------------------------------  
DROP VIEW IF EXISTS user_role_view;
CREATE VIEW user_role_view AS
SELECT
    u.id AS `User ID`,
    r.id AS `Role ID`,
    u.username AS `Username`,
    u.email AS `Email address`,
    u.password AS `Hashed password`,
    r.name AS `Name of role`
FROM
    user_role AS ur
        JOIN
    role AS r ON ur.role_id = r.id
        JOIN
    user AS u ON ur.user_id = u.id;
-- -----------------------------------------------------
-- DatabaseSeed - DML
-- -----------------------------------------------------
CALL InsertDefaultRoles();
CALL InsertUserWithRoleAndGrantPermissions('businessTrip_db_admin', 'admin@localhost.com', 'businessTrip_db_password', 'ROLE_ADMIN');
CALL InsertUserWithRoleAndGrantPermissions('businessTrip_db_user', 'user@localhost.com', 'businessTrip_db_password', 'ROLE_USER');
-- -----------------------------------------------------
-- DatabaseSeed - DQL
-- -----------------------------------------------------
-- USE businessTrip_db;
SELECT * FROM businessTrip_db.role;
SELECT * FROM businessTrip_db.user;
SELECT * FROM businessTrip_db.user_role;
SELECT * FROM businessTrip_db.user_role_view;
-- -----------------------------------------------------
-- DatabaseSeed - DCL
-- -----------------------------------------------------
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
