USE onlineshop;

/*Insert statemens*/
INSERT INTO Addresses(street, city, postal_code)
VALUES ('Magnolia Alley', 'Wroclaw', '34-239');

INSERT INTO Addresses(street, city, postal_code)
VALUES ('Piastowska', 'Lodz', '34-210');

INSERT INTO Addresses(street, city, postal_code)
VALUES ('Drogowa', 'Poznan', '56-110');

INSERT INTO Users(first_name, last_name, age, address_id)
VALUES ('Mikolaj', 'Santa', '56', (SELECT id FROM Addresses WHERE street='Magnolia Alley'));

INSERT INTO Credentials(name, password, mail, user_id)
VALUES ('santamo', 'peaches20', 'santa@gmail.com', (SELECT id FROM Users WHERE last_name='Santa'));

INSERT INTO Products(name, price)
VALUES ('Rode NT-USB', 115.50);

INSERT INTO Sellers(name, address_id)
VALUES ('Audiophiles', (SELECT id FROM Addresses WHERE street='Piastowska'));

INSERT INTO Sellers(name, address_id)
VALUES ('Soundclouders', (SELECT id FROM Addresses WHERE street='Drogowa'));

INSERT INTO Storages(seller_id, product_id)
VALUES ((SELECT id FROM Sellers WHERE name='Audiophiles'), (SELECT id FROM Products WHERE name='Rode NT-USB'));

INSERT INTO Storages(seller_id, product_id)
VALUES ((SELECT id FROM Sellers WHERE name='Soundclouders'), (SELECT id FROM Products WHERE name='Rode NT-USB'));

