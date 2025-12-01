use pizzaDb;

insert into users(username, last_name, first_name, birth_date, role)
values ('kate@gmail.com',
        'Katov',
        'Kate',
        '2003-02-24',
        'USER'),
       ('ivan@gmail.com',
        'Ivanov',
        'Ivan',
        '2002-01-20',
        'USER'),
       ('petr@gmail.com',
        'Petrov',
        'Petr',
        '2001-02-10',
        'ADMIN');

insert into sizes(code, weight)
values ('S',
        500),
       ('M',
        1000),
       ('L',
        1500);

insert into pizzas(name)
values ('Margarita'),
       ('Peperoni'),
       ('Cheese');

insert into pizza_variants(pizza_id, size_id, price)
values (1,
        1,
        200),
       (1,
        2,
        300),
       (1,
        3,
        500),
       (2,
        1,
        200),
       (2,
        2,
        300),
       (2,
        3,
        500),
       (3,
        1,
        200),
       (3,
        2,
        300),
       (3,
        3,
        500);

insert into orders(user_id, pizza_variant_id)
values (1,
        2),
       (2,
        3);


