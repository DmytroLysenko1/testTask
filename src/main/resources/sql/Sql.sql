-- Create database
CREATE DATABASE testdb;

-- Use the database
\c testdb;

-- Create table for users
CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL,
                                     password VARCHAR(255) NOT NULL,
                                     email VARCHAR(255) NOT NULL,
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create table for bank accounts
CREATE TABLE IF NOT EXISTS bank_accounts (
                                             id BIGSERIAL PRIMARY KEY,
                                             bank_number VARCHAR(255) NOT NULL,
                                             type VARCHAR(50) NOT NULL,
                                             status VARCHAR(50) NOT NULL,
                                             user_id BIGINT NOT NULL,
                                             balance DECIMAL(19, 2) DEFAULT 0.00,
                                             created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                             FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create table for transactions
CREATE TABLE IF NOT EXISTS transactions (
                                            id BIGSERIAL PRIMARY KEY,
                                            account_id BIGINT NOT NULL,
                                            amount DECIMAL(19, 2) NOT NULL,
                                            type VARCHAR(50) NOT NULL,
                                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                            FOREIGN KEY (account_id) REFERENCES bank_accounts(id)
);