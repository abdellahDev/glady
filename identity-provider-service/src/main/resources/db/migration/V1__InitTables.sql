DROP TABLE IF EXISTS "company";
DROP TABLE IF EXISTS "users";

CREATE TABLE IF NOT EXISTS company (
  company_id SERIAL PRIMARY KEY,
  company_name VARCHAR(255) NOT NULL UNIQUE,
  company_address VARCHAR(255),
  company_balance NUMERIC(19, 2)
);

CREATE TABLE IF NOT EXISTS users (
  user_id SERIAL PRIMARY KEY,
  user_firstname VARCHAR(255) NOT NULL,
  user_lastname VARCHAR(255) NOT NULL,
  user_email VARCHAR(255) NOT NULL UNIQUE,
  user_pass VARCHAR(255) NOT NULL,
  user_role VARCHAR(50),
  gift_balance NUMERIC(19, 2),
  meal_balance NUMERIC(19, 2),
  company_id INTEGER NOT NULL,
  CONSTRAINT fk_company FOREIGN KEY (company_id) REFERENCES company(company_id)
);

CREATE INDEX idx_user_email ON users(user_email);

