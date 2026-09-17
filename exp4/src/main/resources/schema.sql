CREATE TABLE IF NOT EXISTS t_user (
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    username VARCHAR(50)  NOT NULL,
    password VARCHAR(100) NOT NULL,
    enabled  TINYINT(1)   NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
);

CREATE TABLE IF NOT EXISTS t_role (
    id   BIGINT      NOT NULL AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code)
);

CREATE TABLE IF NOT EXISTS t_permission (
    id   BIGINT      NOT NULL AUTO_INCREMENT,
    code VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_code (code)
);

CREATE TABLE IF NOT EXISTS t_user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE IF NOT EXISTS t_role_permission (
    role_id       BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id)
);
