-- Insert players for Barcelona
-- Insert players for Barcelona
INSERT INTO player (name, last_name, age, team_id, salary, experience_months, market_value)
VALUES
    ('Marc-André', 'ter Stegen', 31, (SELECT id FROM team WHERE name = 'Barcelona' LIMIT 1), 15000000.00, 120, 30000000.00),
    ('Ronald', 'Araújo', 24, (SELECT id FROM team WHERE name = 'Barcelona' LIMIT 1), 8000000.00, 60, 70000000.00),
    ('Jules', 'Koundé', 24, (SELECT id FROM team WHERE name = 'Barcelona' LIMIT 1), 9000000.00, 70, 60000000.00),
    ('Andreas', 'Christensen', 27, (SELECT id FROM team WHERE name = 'Barcelona' LIMIT 1), 7000000.00, 80, 40000000.00),
    ('Alejandro', 'Balde', 19, (SELECT id FROM team WHERE name = 'Barcelona' LIMIT 1), 5000000.00, 20, 50000000.00),
    ('Sergio', 'Busquets', 35, (SELECT id FROM team WHERE name = 'Barcelona' LIMIT 1), 12000000.00, 200, 5000000.00),
    ('Frenkie', 'de Jong', 26, (SELECT id FROM team WHERE name = 'Barcelona' LIMIT 1), 11000000.00, 100, 90000000.00),
    ('Pedri', 'González', 20, (SELECT id FROM team WHERE name = 'Barcelona' LIMIT 1), 10000000.00, 50, 100000000.00),
    ('Gavi', 'Páez', 19, (SELECT id FROM team WHERE name = 'Barcelona' LIMIT 1), 9000000.00, 40, 90000000.00),
    ('Robert', 'Lewandowski', 35, (SELECT id FROM team WHERE name = 'Barcelona' LIMIT 1), 20000000.00, 180, 30000000.00),
    ('Ousmane', 'Dembélé', 26, (SELECT id FROM team WHERE name = 'Barcelona' LIMIT 1), 15000000.00, 90, 60000000.00);

-- Insert players for Manchester City
INSERT INTO player (name, last_name, age, team_id, salary, experience_months, market_value)
VALUES
    ('Ederson', 'Moraes', 30, (SELECT id FROM team WHERE name = 'Manchester City' LIMIT 1), 15000000.00, 80, 45000000.00),
    ('Kyle', 'Walker', 33, (SELECT id FROM team WHERE name = 'Manchester City' LIMIT 1), 8000000.00, 120, 15000000.00),
    ('Rúben', 'Dias', 26, (SELECT id FROM team WHERE name = 'Manchester City' LIMIT 1), 9000000.00, 70, 75000000.00),
    ('John', 'Stones', 29, (SELECT id FROM team WHERE name = 'Manchester City' LIMIT 1), 7000000.00, 100, 40000000.00),
    ('João', 'Cancelo', 29, (SELECT id FROM team WHERE name = 'Manchester City' LIMIT 1), 8000000.00, 90, 50000000.00),
    ('Rodri', 'Hernández', 27, (SELECT id FROM team WHERE name = 'Manchester City' LIMIT 1), 10000000.00, 80, 90000000.00),
    ('Kevin', 'De Bruyne', 32, (SELECT id FROM team WHERE name = 'Manchester City' LIMIT 1), 30000000.00, 120, 80000000.00),
    ('Bernardo', 'Silva', 29, (SELECT id FROM team WHERE name = 'Manchester City' LIMIT 1), 15000000.00, 90, 80000000.00),
    ('Phil', 'Foden', 23, (SELECT id FROM team WHERE name = 'Manchester City' LIMIT 1), 12000000.00, 50, 110000000.00),
    ('Erling', 'Haaland', 23, (SELECT id FROM team WHERE name = 'Manchester City' LIMIT 1), 25000000.00, 40, 180000000.00),
    ('Jack', 'Grealish', 28, (SELECT id FROM team WHERE name = 'Manchester City' LIMIT 1), 15000000.00, 60, 75000000.00);
