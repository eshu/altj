BEGIN;

DROP SCHEMA IF EXISTS "user" CASCADE;
CREATE SCHEMA "user";
SET search_path = 'user';


CREATE TABLE "User" (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(64) UNIQUE,
    password TEXT,
    enabled BOOLEAN NOT NULL,
    created TIMESTAMP WITH TIME ZONE NOT NULL,
    updated TIMESTAMP WITH TIME ZONE NOT NULL
);

INSERT INTO users (login, password, enabled, created, updated)
    VALUES ('admin', '$2a$10$htysDx2sE1opbQ4JgRQmI.AgbDrbPXovs3i5Qzo5k4RiysFmkN1km', true, now(), now());

CREATE TABLE authorities (
    id SERIAL PRIMARY KEY,
    name VARCHAR(64) UNIQUE
);

INSERT INTO authorities (name)
    VALUES ('admin');

CREATE TABLE users_authorities (
    user_id BIGINT NOT NULL REFERENCES users (id),
    authority_id INTEGER NOT NULL REFERENCES authorities (id),
    created TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by BIGINT NOT NULL REFERENCES users (id)
);

INSERT INTO users_authorities (user_id, authority_id, created, created_by)
    VALUES (1, 1, now(), 1);

COMMIT;
