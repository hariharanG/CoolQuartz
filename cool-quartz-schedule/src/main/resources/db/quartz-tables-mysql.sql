SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
SHOW WARNINGS;
-- -----------------------------------------------------
-- Schema quartz-db
-- -----------------------------------------------------
-- DROP SCHEMA IF EXISTS `quartz-db` ;
-- CREATE SCHEMA IF NOT EXISTS `quartz-db` DEFAULT CHARACTER SET latin1 ;
SHOW WARNINGS;
USE `quartz-db` ;

-- -----------------------------------------------------
-- Table `qrtz_blob_triggers`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `qrtz_blob_triggers` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `qrtz_blob_triggers` (
  `SCHED_NAME` VARCHAR(120) NOT NULL,
  `TRIGGER_NAME` VARCHAR(200) NOT NULL,
  `TRIGGER_GROUP` VARCHAR(200) NOT NULL,
  `BLOB_DATA` BLOB NULL DEFAULT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `qrtz_calendars`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `qrtz_calendars` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `qrtz_calendars` (
  `SCHED_NAME` VARCHAR(120) NOT NULL,
  `CALENDAR_NAME` VARCHAR(200) NOT NULL,
  `CALENDAR` BLOB NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `qrtz_job_details`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `qrtz_job_details` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `qrtz_job_details` (
  `SCHED_NAME` VARCHAR(120) NOT NULL,
  `JOB_NAME` VARCHAR(200) NOT NULL,
  `JOB_GROUP` VARCHAR(200) NOT NULL,
  `DESCRIPTION` VARCHAR(250) NULL DEFAULT NULL,
  `JOB_CLASS_NAME` VARCHAR(250) NOT NULL,
  `IS_DURABLE` TINYINT(1) NOT NULL,
  `IS_NONCONCURRENT` TINYINT(1) NOT NULL,
  `IS_UPDATE_DATA` TINYINT(1) NOT NULL,
  `REQUESTS_RECOVERY` TINYINT(1) NOT NULL,
  `JOB_DATA` BLOB NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `qrtz_triggers`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `qrtz_triggers` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `qrtz_triggers` (
  `SCHED_NAME` VARCHAR(120) NOT NULL,
  `TRIGGER_NAME` VARCHAR(200) NOT NULL,
  `TRIGGER_GROUP` VARCHAR(200) NOT NULL,
  `JOB_NAME` VARCHAR(200) NOT NULL,
  `JOB_GROUP` VARCHAR(200) NOT NULL,
  `DESCRIPTION` VARCHAR(250) NULL DEFAULT NULL,
  `NEXT_FIRE_TIME` BIGINT(20) NULL DEFAULT NULL,
  `PREV_FIRE_TIME` BIGINT(20) NULL DEFAULT NULL,
  `PRIORITY` INT(11) NULL DEFAULT NULL,
  `TRIGGER_STATE` VARCHAR(16) NOT NULL,
  `TRIGGER_TYPE` VARCHAR(8) NOT NULL,
  `START_TIME` BIGINT(20) NOT NULL,
  `END_TIME` BIGINT(20) NULL DEFAULT NULL,
  `CALENDAR_NAME` VARCHAR(200) NULL DEFAULT NULL,
  `MISFIRE_INSTR` SMALLINT(6) NULL DEFAULT NULL,
  `JOB_DATA` BLOB NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  CONSTRAINT `FK_QRTZ_TRIGGERS_QRTZ_JOB_DETAILS`
    FOREIGN KEY (`SCHED_NAME` , `JOB_NAME` , `JOB_GROUP`)
    REFERENCES `qrtz_job_details` (`SCHED_NAME` , `JOB_NAME` , `JOB_GROUP`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

SHOW WARNINGS;
-- CREATE INDEX `FK_QRTZ_TRIGGERS_QRTZ_JOB_DETAILS` ON `qrtz_triggers` (`SCHED_NAME` ASC, `JOB_NAME` ASC, `JOB_GROUP` ASC);
-- SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `qrtz_cron_triggers`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `qrtz_cron_triggers` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `qrtz_cron_triggers` (
  `SCHED_NAME` VARCHAR(120) NOT NULL,
  `TRIGGER_NAME` VARCHAR(200) NOT NULL,
  `TRIGGER_GROUP` VARCHAR(200) NOT NULL,
  `CRON_EXPRESSION` VARCHAR(120) NOT NULL,
  `TIME_ZONE_ID` VARCHAR(80) NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  CONSTRAINT `FK_QRTZ_CRON_TRIGGERS_QRTZ_TRIGGERS`
    FOREIGN KEY (`SCHED_NAME` , `TRIGGER_NAME` , `TRIGGER_GROUP`)
    REFERENCES `qrtz_triggers` (`SCHED_NAME` , `TRIGGER_NAME` , `TRIGGER_GROUP`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `qrtz_fired_triggers`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `qrtz_fired_triggers` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `qrtz_fired_triggers` (
  `SCHED_NAME` VARCHAR(120) NOT NULL,
  `ENTRY_ID` VARCHAR(95) NOT NULL,
  `TRIGGER_NAME` VARCHAR(200) NOT NULL,
  `TRIGGER_GROUP` VARCHAR(200) NOT NULL,
  `INSTANCE_NAME` VARCHAR(200) NOT NULL,
  `FIRED_TIME` BIGINT(20) NOT NULL,
  `SCHED_TIME` BIGINT(20) NOT NULL,
  `PRIORITY` INT(11) NOT NULL,
  `STATE` VARCHAR(16) NOT NULL,
  `JOB_NAME` VARCHAR(200) NULL DEFAULT NULL,
  `JOB_GROUP` VARCHAR(200) NULL DEFAULT NULL,
  `IS_NONCONCURRENT` TINYINT(1) NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `qrtz_job_history`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `qrtz_job_history` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `qrtz_job_history` (
  `ENTRY_ID` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `HOSTNAME` VARCHAR(250) NULL DEFAULT NULL,
  `IP_ADDR` VARCHAR(250) NULL DEFAULT NULL,
  `SCHED_NAME` VARCHAR(120) NOT NULL,
  `INSTANCE_NAME` VARCHAR(200) NOT NULL,
  `JOB_NAME` VARCHAR(200) NULL DEFAULT NULL,
  `JOB_GROUP` VARCHAR(200) NULL DEFAULT NULL,
  `TRIGGER_NAME` VARCHAR(200) NOT NULL,
  `TRIGGER_GROUP` VARCHAR(200) NOT NULL,
  `FIRED_TIME` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `NEXT_FIRE_TIME` TIMESTAMP NOT NULL,
  `PRIORITY` INT(11) NOT NULL,
  `IS_NONCONCURRENT` TINYINT(1) NULL DEFAULT NULL,
  `REQUESTS_RECOVERY` TINYINT(1) NULL DEFAULT NULL,
  `RESULT` VARCHAR(250) NOT NULL,
  `EXCEPTIONS` VARCHAR(2500) NULL DEFAULT NULL,
  PRIMARY KEY (`ENTRY_ID`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

-- ALTER TABLE `qrtz_job_history`
-- CHANGE COLUMN `NEXT_FIRE_TIME` TIMESTAMP NOT NULL DEFAULT '0000-00-00 00:00:00';

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `qrtz_locks`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `qrtz_locks` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `qrtz_locks` (
  `SCHED_NAME` VARCHAR(120) NOT NULL,
  `LOCK_NAME` VARCHAR(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `qrtz_paused_trigger_grps`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `qrtz_paused_trigger_grps` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `qrtz_paused_trigger_grps` (
  `SCHED_NAME` VARCHAR(120) NOT NULL,
  `TRIGGER_GROUP` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `qrtz_scheduler_state`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `qrtz_scheduler_state` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `qrtz_scheduler_state` (
  `SCHED_NAME` VARCHAR(120) NOT NULL,
  `INSTANCE_NAME` VARCHAR(200) NOT NULL,
  `LAST_CHECKIN_TIME` BIGINT(20) NOT NULL,
  `CHECKIN_INTERVAL` BIGINT(20) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `qrtz_simple_triggers`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `qrtz_simple_triggers` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `qrtz_simple_triggers` (
  `SCHED_NAME` VARCHAR(120) NOT NULL,
  `TRIGGER_NAME` VARCHAR(200) NOT NULL,
  `TRIGGER_GROUP` VARCHAR(200) NOT NULL,
  `REPEAT_COUNT` BIGINT(20) NOT NULL,
  `REPEAT_INTERVAL` BIGINT(20) NOT NULL,
  `TIMES_TRIGGERED` BIGINT(20) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  CONSTRAINT `FK_QRTZ_SIMPLE_TRIGGERS_QRTZ_TRIGGERS`
    FOREIGN KEY (`SCHED_NAME` , `TRIGGER_NAME` , `TRIGGER_GROUP`)
    REFERENCES `qrtz_triggers` (`SCHED_NAME` , `TRIGGER_NAME` , `TRIGGER_GROUP`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `qrtz_simprop_triggers`
-- -----------------------------------------------------
-- DROP TABLE IF EXISTS `qrtz_simprop_triggers` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `qrtz_simprop_triggers` (
  `SCHED_NAME` VARCHAR(120) NOT NULL,
  `TRIGGER_NAME` VARCHAR(200) NOT NULL,
  `TRIGGER_GROUP` VARCHAR(200) NOT NULL,
  `STR_PROP_1` VARCHAR(512) NULL DEFAULT NULL,
  `STR_PROP_2` VARCHAR(512) NULL DEFAULT NULL,
  `STR_PROP_3` VARCHAR(512) NULL DEFAULT NULL,
  `INT_PROP_1` INT(11) NULL DEFAULT NULL,
  `INT_PROP_2` INT(11) NULL DEFAULT NULL,
  `LONG_PROP_1` BIGINT(20) NULL DEFAULT NULL,
  `LONG_PROP_2` BIGINT(20) NULL DEFAULT NULL,
  `DEC_PROP_1` DECIMAL(13,4) NULL DEFAULT NULL,
  `DEC_PROP_2` DECIMAL(13,4) NULL DEFAULT NULL,
  `BOOL_PROP_1` TINYINT(1) NULL DEFAULT NULL,
  `BOOL_PROP_2` TINYINT(1) NULL DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
  CONSTRAINT `FK_QRTZ_SIMPROP_TRIGGERS_QRTZ_TRIGGERS`
    FOREIGN KEY (`SCHED_NAME` , `TRIGGER_NAME` , `TRIGGER_GROUP`)
    REFERENCES `qrtz_triggers` (`SCHED_NAME` , `TRIGGER_NAME` , `TRIGGER_GROUP`)
    ON DELETE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
