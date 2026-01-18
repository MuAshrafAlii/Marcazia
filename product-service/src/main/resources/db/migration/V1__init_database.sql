CREATE TABLE IF NOT EXISTS category (
    id BIGSERIAL PRIMARY KEY,     -- 64-bit auto-generated ID (maps to Java Long)
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS product (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    description VARCHAR(255),
    name VARCHAR(255),
    available_quantity DOUBLE PRECISION NOT NULL,
    price NUMERIC(38,2),
    category_id INTEGER,
    CONSTRAINT fk_category
    FOREIGN KEY (category_id)
    REFERENCES category(id)
    );
