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
       ('Хуторок'),
       ('Parmesan');

INSERT INTO DISH (name, price, in_menu_date, restaurant_id)
VALUES ('Маргарита', 499.90, '2022-09-25', 1),
       ('Пепперони', 649.90, '2022-09-24', 1),
       ('Гавайская', 579.90, '2022-09-25', 1),
       ('Моцарела', 649.9, '2022-09-26', 1),
       ('Бишбармак', 500, '2022-09-24', 2),
       ('Шурпа', 400, '2022-09-25', 2),
       ('Шашлык', 700, '2022-09-24', 2),
       ('Кебаб', 700, '2022-09-26', 2),
       ('Пельмени', 350, '2022-09-25', 3),
       ('Борщ', 250, '2022-09-24', 3),
       ('Смазженина с овощами', 550, '2022-09-25', 3);

INSERT INTO VOTE (restaurant_id, user_id, vote_date)
VALUES (3, 3, '2022-09-24'),
       (2, 4, '2022-09-24'),
       (2, 4, '2022-09-25'),
       (2, 4, '2022-09-26');