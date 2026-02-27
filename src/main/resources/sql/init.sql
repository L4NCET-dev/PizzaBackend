use pizzaDb;

create table if not exists users
(
    id         int unsigned           not null auto_increment,
    username   varchar(128)           not null unique,
    last_name  varchar(128),
    first_name varchar(128),
    birth_date date,
    role       enum ('USER', 'ADMIN') not null,
    primary key (id)
);

create table if not exists pizzas
(
    id   int unsigned not null auto_increment,
    name varchar(128) not null unique,
    primary key (id)
);

create table if not exists sizes
(
    id     int unsigned         not null auto_increment,
    code   enum ('S', 'M', 'L') not null,
    weight int unsigned         not null,
    primary key (id)

);

create table if not exists pizza_variants
(
    id       int unsigned   not null auto_increment,
    pizza_id int unsigned   not null,
    size_id  int unsigned   not null,
    price    decimal(10, 2) not null,
    primary key (id),
    foreign key (pizza_id) references pizzas (id),
    foreign key (size_id) references sizes (id),
    unique key uq_pizza_size (pizza_id, size_id)

);

create table if not exists orders
(
    id               int unsigned not null auto_increment,
    user_id          int unsigned not null,
    pizza_variant_id int unsigned not null,
    primary key (id),
    foreign key (user_id) references users (id),
    foreign key (pizza_variant_id) references pizza_variants (id)
);

alter table users
add column password varchar(128) not null;