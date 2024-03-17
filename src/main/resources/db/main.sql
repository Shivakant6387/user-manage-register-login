--database name:
CREATE DATABASE user_manager;
User user_manager;
GRANT ALL PRIVILEGES ON user_manager.*TO 'root'@'localhost' IDENTIFIED BY 'data#12345'
--USER Table Structure:
CREATE TABLE users(
    id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50)NOT NULL ,
    last_name VARCHAR(50)NOT NULL ,
    email VARCHAR(255)NOT NULL ,
    password VARCHAR(255)NOT NULL ,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    primary key (id)
);