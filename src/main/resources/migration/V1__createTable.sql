CREATE SCHEMA IF NOT EXISTS iws;
SET search_path TO iws;

CREATE TABLE iws.order
(
    order_id    serial PRIMARY KEY,
    customer_id integer NOT NULL,
    items       text,
    price       varchar(10),
    bill        text
);