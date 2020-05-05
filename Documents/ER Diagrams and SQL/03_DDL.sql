set FOREIGN_KEY_CHECKS = 0;
CREATE DATABASE IF NOT EXISTS app_db;

USE app_db;


DROP TABLE IF EXISTS allergen;

CREATE TABLE allergen(
	food_name varchar(100),
	primary key(food_name)
);


DROP TABLE IF EXISTS user;

CREATE TABLE app_user(
	email varchar(100) PRIMARY KEY,
	name varchar(150),
	user_password varchar(100) NOT NULL,
	user_type varchar(20),
	height_in int,
	weight_lb int,
	calorie_limit int
);


DROP TABLE IF EXISTS prof_picture;

CREATE TABLE prof_picture(
	user_email varchar(100) FOREIGN KEY references app_user(email),
	picture_path varchar(100)
);


DROP TABLE IF EXISTS day;

CREATE TABLE day(
	user_email varchar(100) FOREIGN KEY references app_user(email),
	stored_date date,
	calories int,
	carbohydrates int,
	sodium int,
	protein int,
	fat int,
	cholesterol int
);


DROP TABLE IF EXISTS status;

CREATE TABLE status(
	poster_email varchar(100) FOREIGN KEY references app_user(email),
	statusID int PRIMARY KEY,
	referenced_day date,
	message varchar(200)
);


DROP TABLE IF EXISTS friends;

CREATE TABLE friends(
	user1_email varchar(100) FOREIGN KEY references app_user(email),
	user2_email varchar(100) FOREIGN KEY references app_user(email),
);


DROP TABLE IF EXISTS allergic;

CREATE TABLE allergic(
	user_email varchar(100)	foreign key references user(email),
	food_name varchar(100) foreign key references allergen(food_name),
);

SET FOREIGN_KEY_CHECKS = 1;
