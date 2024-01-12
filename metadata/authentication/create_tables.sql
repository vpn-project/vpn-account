CREATE TABLE IF NOT EXISTS "user"
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(32) UNIQUE  NOT NULL,
    password VARCHAR(60)         NOT NULL,
    email    VARCHAR(256) UNIQUE NOT NULL,
    role     VARCHAR(16) DEFAULT 'USER'
);
