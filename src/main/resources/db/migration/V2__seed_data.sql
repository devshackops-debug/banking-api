-- =========================================================
-- SEED DATA: CUSTOMERS, ACCOUNTS, AND TRANSACTIONS
-- =========================================================

-- CUSTOMERS
INSERT INTO customers (name, email) VALUES
                                        ('Alice Johnson', 'alice@example.com'),
                                        ('Bob Smith', 'bob@example.com'),
                                        ('Carol White', 'carol@example.com'),
                                        ('David Brown', 'david@example.com'),
                                        ('Eve Black', 'eve@example.com');

-- ACCOUNTS (each with initial balance 100000)
-- We'll store balance indirectly by assuming transactions track movements.
INSERT INTO accounts (account_number, status, customer_id)
VALUES
    ('ACC001', 'ACTIVE', 1),
    ('ACC002', 'ACTIVE', 2),
    ('ACC003', 'ACTIVE', 3),
    ('ACC004', 'ACTIVE', 4),
    ('ACC005', 'ACTIVE', 5);

-- =========================================================
-- TRANSACTIONS
-- Two-legged: for every CREDIT, a matching DEBIT exists on another account.
-- =========================================================

-- Pair 1: Alice -> Bob (Alice sends 2,000 to Bob)
INSERT INTO transactions (transaction_type, amount, created_at, account_id)
VALUES
    ('DEBIT', 2000, NOW(), 1),  -- Alice
    ('CREDIT', 2000, NOW(), 2); -- Bob

-- Pair 2: Bob -> Carol (Bob sends 3,500 to Carol)
INSERT INTO transactions (transaction_type, amount, created_at, account_id)
VALUES
    ('DEBIT', 3500, NOW(), 2),  -- Bob
    ('CREDIT', 3500, NOW(), 3); -- Carol

-- Pair 3: Carol -> David (Carol sends 1,250 to David)
INSERT INTO transactions (transaction_type, amount, created_at, account_id)
VALUES
    ('DEBIT', 1250, NOW(), 3),  -- Carol
    ('CREDIT', 1250, NOW(), 4); -- David

-- Pair 4: David -> Eve (David sends 4,800 to Eve)
INSERT INTO transactions (transaction_type, amount, created_at, account_id)
VALUES
    ('DEBIT', 4800, NOW(), 4),  -- David
    ('CREDIT', 4800, NOW(), 5); -- Eve

-- Pair 5: Eve -> Alice (Eve sends 900 to Alice)
INSERT INTO transactions (transaction_type, amount, created_at, account_id)
VALUES
    ('DEBIT', 900, NOW(), 5),   -- Eve
    ('CREDIT', 900, NOW(), 1);  -- Alice
