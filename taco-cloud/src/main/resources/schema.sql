CREATE TABLE IF NOT EXISTS Ingredient(
    id VARCHAR(4) NOT NULL,
    name VARCHAR(25) NOT NULL,
    type VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS Taco(
    id IDENTITY,
    name VARCHAR(50) NOT NULL,
    createdAt TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS Taco_Ingredient(
    taco BIGINT NOT NULL,
    ingredient VARCHAR(4)
);

ALTER TABLE Taco_Ingredient ADD FOREIGN KEY (taco) REFERENCES Taco(id);
ALTER TABLE Taco_Ingredient ADD FOREIGN KEY (ingredient) REFERENCES Ingredient(id);

CREATE TABLE IF NOT EXISTS TacoOrder(
    id IDENTITY,
    name VARCHAR(50) NOT NULL,
    street VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(2) NOT NULL,
    zip VARCHAR(10) NOT NULL,
    ccNumber VARCHAR(16) NOT NULL,
    ccExpiration VARCHAR(5) NOT NULL,
    ccCVV VARCHAR(3) NOT NULL,
    placedAt TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS TacoOrder_Taco(
    tacoOrder BIGINT NOT NULL,
    taco BIGINT NOT NULL
);

ALTER TABLE TacoOrder_Taco ADD FOREIGN KEY (tacoOrder) REFERENCES TacoOrder(id);
ALTER TABLE TacoOrder_Taco ADD FOREIGN KEY (taco) REFERENCES Taco(id); 

-- CREATE TABLE IF NOT EXISTS User(
--     name VARCHAR(50) NOT NULL PRIMARY KEY,
--     password VARCHAR(256) NOT NULL,
--     enabled BOOLEAN NOT NULL
-- );

-- CREATE TABLE IF NOT EXISTS Authority(
--     name VARCHAR(50) NOT NULL PRIMARY KEY, 
--     auth VARCHAR(50) NOT NULL,
--     CONSTRAINT fk_authority_user FOREIGN KEY(name) REFERENCES User(NAME)
-- );