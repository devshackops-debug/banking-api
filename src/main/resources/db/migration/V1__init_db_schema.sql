
-- =========================================================
-- CUSTOMERS TABLE
-- =========================================================
CREATE TABLE customers (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255),
                           email VARCHAR(255) UNIQUE NOT NULL
);

-- =========================================================
-- ACCOUNTS TABLE
-- =========================================================
CREATE TABLE accounts (
                          id SERIAL PRIMARY KEY,
                          account_number VARCHAR(255),
                          status VARCHAR NOT NULL,
                          customer_id BIGINT NOT NULL,
                          CONSTRAINT fk_account_customer FOREIGN KEY (customer_id)
                              REFERENCES customers (id)
                              ON DELETE CASCADE
);

-- =========================================================
-- TRANSACTIONS TABLE
-- =========================================================
CREATE TABLE transactions (
                              id SERIAL PRIMARY KEY,
                              transaction_type VARCHAR NOT NULL,
                              amount DOUBLE PRECISION NOT NULL,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              account_id BIGINT NOT NULL,
                              CONSTRAINT fk_transaction_account FOREIGN KEY (account_id)
                                  REFERENCES accounts (id)
                                  ON DELETE CASCADE
);
