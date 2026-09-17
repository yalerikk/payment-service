-- ============================================
-- SEED DATA FOR DEVELOPMENT
-- Контекст: dev (не применяется в prod)
-- ============================================

-- 15 пользователей
INSERT INTO users (email) VALUES
    ('alice@example.com'),
    ('bob@example.com'),
    ('charlie@example.com'),
    ('diana@example.com'),
    ('edward@example.com'),
    ('fiona@example.com'),
    ('george@example.com'),
    ('helen@example.com'),
    ('ivan@example.com'),
    ('julia@example.com'),
    ('kevin@example.com'),
    ('linda@example.com'),
    ('michael@example.com'),
    ('nina@example.com'),
    ('oscar@example.com');

-- 30 платежей (по 2 на каждого пользователя)
INSERT INTO payments (user_id, amount, status, created_at, updated_at) VALUES
    -- Alice (id=1)
    (1, 150.00, 'NEW', NOW(), NOW()),
    (1, 2500.50, 'SUCCEEDED', NOW(), NOW()),
    -- Bob (id=2)
    (2, 100.00, 'NEW', NOW(), NOW()),
    (2, 500.00, 'FAILED', NOW(), NOW()),
    -- Charlie (id=3)
    (3, 300.00, 'NEW', NOW(), NOW()),
    (3, 750.25, 'SUCCEEDED', NOW(), NOW()),
    -- Diana (id=4)
    (4, 1200.00, 'NEW', NOW(), NOW()),
    (4, 450.00, 'FAILED', NOW(), NOW()),
    -- Edward (id=5)
    (5, 50.00, 'NEW', NOW(), NOW()),
    (5, 999.99, 'SUCCEEDED', NOW(), NOW()),
    -- Fiona (id=6)
    (6, 2000.00, 'NEW', NOW(), NOW()),
    (6, 150.50, 'FAILED', NOW(), NOW()),
    -- George (id=7)
    (7, 80.00, 'NEW', NOW(), NOW()),
    (7, 3200.00, 'SUCCEEDED', NOW(), NOW()),
    -- Helen (id=8)
    (8, 600.00, 'NEW', NOW(), NOW()),
    (8, 275.75, 'FAILED', NOW(), NOW()),
    -- Ivan (id=9)
    (9, 450.00, 'NEW', NOW(), NOW()),
    (9, 1800.00, 'SUCCEEDED', NOW(), NOW()),
    -- Julia (id=10)
    (10, 900.00, 'NEW', NOW(), NOW()),
    (10, 110.00, 'FAILED', NOW(), NOW()),
    -- Kevin (id=11)
    (11, 3000.00, 'NEW', NOW(), NOW()),
    (11, 25.50, 'SUCCEEDED', NOW(), NOW()),
    -- Linda (id=12)
    (12, 70.00, 'NEW', NOW(), NOW()),
    (12, 5000.00, 'FAILED', NOW(), NOW()),
    -- Michael (id=13)
    (13, 1250.00, 'NEW', NOW(), NOW()),
    (13, 890.00, 'SUCCEEDED', NOW(), NOW()),
    -- Nina (id=14)
    (14, 400.00, 'NEW', NOW(), NOW()),
    (14, 1500.00, 'FAILED', NOW(), NOW()),
    -- Oscar (id=15)
    (15, 6000.00, 'NEW', NOW(), NOW()),
    (15, 50.00, 'SUCCEEDED', NOW(), NOW());