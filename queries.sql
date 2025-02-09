USE onlineshop;

/*Insert statemens*/
INSERT INTO Addresses(street, city, postal_code)
VALUES ('Magnolia Alley', 'Wroclaw', '34-239');

INSERT INTO Users(first_name, last_name, age, address_id)
VALUES ('Mikolaj', 'Santa', '56', (SELECT id FROM Addresses WHERE street='Magnolia Alley'));