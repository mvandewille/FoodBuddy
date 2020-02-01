set FOREIGN_KEY_CHECKS = 0;
CREATE DATABASE IF NOT EXISTS app_db;

USE app_db;


DROP TABLE IF EXISTS allergen;

CREATE TABLE allergen(
	food_name varchar(100),
	primary key(food_name)
);


DROP TABLE IF EXISTS user;

CREATE TABLE user(
	email varchar(100),
	name varchar(150),
	user_password varchar(100) NOT NULL,
	user_type varchar(20),
	height_in int,
	weight_lb int,
	calorie_limit int,
	primary key(email)
);


DROP TABLE IF EXISTS prof_picture;

CREATE TABLE prof_picture(
	user_email varchar(100) UNIQUE,
	picture image,
	foreign key(user_email) references `user` (`email`)
);


DROP TABLE IF EXISTS day;

CREATE TABLE day(
	user_email varchar(100) UNIQUE,
	stored_date date,
	calories int,
	carbohydrates int,
	sodium int,
	protein int,
	fat int,
	cholesterol int
	foreign key(user_email) references `user`(`email`)
);


DROP TABLE IF EXISTS status;

CREATE TABLE status(
	poster_email varchar(100),
	statusID int,
	referenced_day date,
	message varchar(200)
	foreign key(poster_email) references `user`(`email`),
	primary key(statusID)
);


DROP TABLE IF EXISTS friends;

CREATE TABLE friends(
	user1_email varchar(100),
	user2_email varchar(100),
	foreign key(user1_email) references `user`(`email`),
	foreign key(user2_email) references `user`(`email`)
);


DROP TABLE IF EXISTS allergic;

CREATE TABLE allergic(
	user_email varchar(100),
	food_name varchar(100),
	foreign key(food) references allergen(food_name),
	foreign key(user_email) references user(email)
);

SET FOREIGN_KEY_CHECKS = 1;
