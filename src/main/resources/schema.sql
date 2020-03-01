CREATE DATABASE IF NOT exists `customer_db`;
USE `customer_db`;

DROP table if exists `customer`;

CREATE TABLE `customer`(
`id` int(11) NOT NULL auto_increment,
`first_name` varchar(45) default NULL,
`last_name` varchar(45) default NULL,
`email` varchar(45) default NULL,
primary key(`id`)
)
engine=InnoDB auto_increment=1 default charset=latin1;
