INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User2', 'user2@yandex.ru', '{noop}password'),
       ('User3', 'user3@yandex.ru', '{noop}password');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4);

INSERT INTO RESTAURANT (name)
VALUES ('Pizzitalia'),
       ('Халва'),
       ('Хуторок');

INSERT INTO DISH (name, price, restaurant_id)
VALUES ('Маргарита', 500, 1),
       ('Пепперони', 700, 1),
       ('Гавайская', 575, 1),
       ('Бишбармак', 500, 2),
       ('Шурпа', 400, 2),
       ('Шашлык', 700, 2),
       ('Пельмени', 350, 3),
       ('Борщ', 250, 3),
       ('Смазженина с овощами', 550, 3);

INSERT INTO VOTE (restaurant_id, user_id)
VALUES (3, 3),
       (2, 4);