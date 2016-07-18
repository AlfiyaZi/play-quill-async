# Devices schema

# --- !Ups
CREATE TABLE devices (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    user_id INT NOT NULL
);

# --- !Downs
DROP TABLE devices;