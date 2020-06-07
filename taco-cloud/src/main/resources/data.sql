-- Delete all previously created data 
DELETE FROM TacoOrder_Taco;
DELETE FROM Taco_Ingredient;
DELETE FROM Taco;
DELETE FROM TacoOrder;
DELETE FROM Ingredient;

-- Insert demo data 
INSERT INTO INGREDIENT (id, name, type) VALUES
    ('FLTO', 'Flour Tortilla', 'WRAP'),
    ('COTO', 'Corn Tortilla', 'WRAP'),
    ('GRBF', 'Ground Beadf', 'PROTEIN'),
    ('CARN', 'Carnitas', 'PROTEIN'),
    ('TMTO', 'Diced Tomatoes', 'VEGGIES'),
    ('LECT', 'Lettuce', 'VEGGIES'),
    ('CHED', 'Cheddar', 'CHEESE'),
    ('JACK', 'Monterrey Jack', 'CHEESE'),
    ('SLSA', 'Salsa', 'SAUSE'),
    ('SRCR', 'Sour Cream', 'SAUSE');