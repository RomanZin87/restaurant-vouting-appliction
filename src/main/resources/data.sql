INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name)
VALUES ('PIZZA HOT'),
       ('HALVA'),
       ('HUTOROK');

INSERT INTO DISH (name, price, restaurant_id)
VALUES ('Маргарита', 500, 1),
       ('Пепперони', 700, 1),
       ('Гавайская', 575, 1),
       ('Бишбармак', 500, 2),
       ('Шурпа', 400, 2),
       ('Шашлык', 700, 2),
       ('Пельмени', 350, 3),
       ('Борщ',  250, 3),
       ('Смазженина с овощами', 550, 3);