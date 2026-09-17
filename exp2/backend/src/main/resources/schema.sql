CREATE TABLE IF NOT EXISTS t_user (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    username    VARCHAR(50)  NOT NULL,
    password    VARCHAR(100) NOT NULL,
    email       VARCHAR(100),
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
);
