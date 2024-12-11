
--Tables--

--Users
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    email VARCHAR(255),
    role VARCHAR(255)
);

--Products
CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    price NUMBER,
    quantity INT,
    seller_id INT
);


--Queries--

--Users
INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)
SELECT id, username, password, email, role FROM users
SELECT * FROM users WHERE username = ?
DELETE FROM users WHERE id = ?

--Products
SELECT id, name, price, quantity, seller_id FROM products
INSERT INTO products (name, price, quantity, seller_id) VALUES (?, ?, ?, ?) RETURNING id
SELECT id, name, price, quantity, seller_id FROM products WHERE id = ?
SELECT id, name, price, quantity, seller_id FROM products WHERE seller_id = ?
SELECT id, name, price, quantity, seller_id FROM products WHERE name ILIKE ?
UPDATE products SET name = ?, price = ?, quantity = ? WHERE id = ?
DELETE FROM products WHERE id = ?