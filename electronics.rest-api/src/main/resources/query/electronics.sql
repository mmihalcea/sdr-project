INSERT INTO public.county (id, name)
VALUES (1, 'Alba'),
       (2, 'Arad'),
       (3, 'Argeș'),
       (4, 'Bacău'),
       (5, 'Bihor'),
       (6, 'Bistrița-Năsăud'),
       (7, 'Botoșani'),
       (8, 'Brăila'),
       (9, 'Brașov'),
       (10, 'București'),
       (11, 'Buzău'),
       (12, 'Călărași'),
       (13, 'Caraș-Severin'),
       (14, 'Cluj'),
       (15, 'Constanța'),
       (16, 'Covasna'),
       (17, 'Dâmbovița'),
       (18, 'Dolj'),
       (19, 'Galați'),
       (20, 'Giurgiu'),
       (21, 'Gorj'),
       (22, 'Harghita'),
       (23, 'Hunedoara'),
       (24, 'Ialomița'),
       (25, 'Iași'),
       (26, 'Ilfov'),
       (27, 'Maramureș'),
       (28, 'Mehedinți'),
       (29, 'Mureș'),
       (30, 'Neamț'),
       (31, 'Olt'),
       (32, 'Prahova'),
       (33, 'Sălaj'),
       (34, 'Satu Mare'),
       (35, 'Sibiu'),
       (36, 'Suceava'),
       (37, 'Teleorman'),
       (38, 'Timiș'),
       (39, 'Tulcea'),
       (40, 'Vâlcea'),
       (41, 'Vaslui'),
       (42, 'Vrancea');


INSERT INTO public.role (id, name)
VALUES
    (1, 'ROLE_USER'),
    (2, 'ROLE_ADMIN');

INSERT INTO public.stock_status (id, value)
VALUES
    (1, 'In stoc'),
    (2, 'Stoc redus'),
    (3, 'Stoc epuizat');

INSERT INTO public.store_order_status (id, value)
VALUES
    (1, 'Confirmată'),
    (2, 'În lucru'),
    (3, 'Livrată');

INSERT INTO public.address (id, city, lat, lon, number, post_code, street, county_id)
VALUES
    (1, 'BUcuresti', 44.408367, 26.117294, NULL, NULL, 'Bulevardul Tineretului', 10);

INSERT INTO public.store_user (id, email, name, password, profile_pic, username, address_id)
VALUES
    (1, 'test@mail.com', 'test', '$2a$10$rjsj6/88q/kEuy8fIjjuOObk0gJahF1D3GgIrgRV0x3fJC8DW6mpq', NULL, 'testtest', 1),
    (2, 'admin@mail.com', 'admin', '$2a$10$vROENxwhcP3olqBkCiaJR.ZIeC3zJm98kVh360v.ScbDR33r0Ndza', NULL, 'adminadmin', 1);


INSERT INTO public.store_user_role (user_id, role_id)
VALUES
    (1, 1),
    (2, 2);




INSERT INTO public.quiz_question (id, text) VALUES (1, 'Care este scopul principal pentru care cauți un produs nou astăzi?');
INSERT INTO public.quiz_question (id, text) VALUES (2, 'Unde vei folosi cel mai des acest dispozitiv?');


INSERT INTO public.quiz_answer (id, text, category_id, question_id) VALUES (1, 'Vreau o stație de lucru puternică pentru gaming, editare video sau programare intensivă', 3, 1);
INSERT INTO public.quiz_answer (id, text, category_id, question_id) VALUES (2, 'Am nevoie de un dispozitiv pe care să pot lucra, învăța sau naviga pe internet de oriunde (acasă, birou, cafenea)', 10, 1);
INSERT INTO public.quiz_answer (id, text, category_id, question_id) VALUES (3, 'Vreau ceva ultra-portabil pentru relaxare, citit, social media sau pentru a ține copiii ocupați.', 9, 1);
INSERT INTO public.quiz_answer (id, text, category_id, question_id) VALUES (4, 'Fix, pe un birou, conectat permanent la priză și la un monitor.', 3, 2);
INSERT INTO public.quiz_answer (id, text, category_id, question_id) VALUES (5, 'În rucsac/geantă, mereu cu mine în deplasări.', 10, 2);
INSERT INTO public.quiz_answer (id, text, category_id, question_id) VALUES (6, 'Într-un punct fix din living sau dormitor, ca piesă centrală.', 23, 2);

UPDATE public.store_user SET password = 'test123A#' WHERE username = 'testtest';
UPDATE public.store_user SET password = 'test123A#' WHERE username = 'adminadmin';

--user fara reviews
INSERT INTO public.store_user (id, email, name, password, profile_pic, username, address_id)
VALUES
    (202, 'user200_989898@mock.local', 'User 200', '=mock_password_201', NULL, 'user201_28221', 1);

UPDATE store_user SET address_id =2 where id % 2 = 0;
UPDATE store_user SET address_id =3 where id % 3 = 0;
UPDATE store_user SET address_id =4 where id % 4 = 0;
UPDATE store_user SET address_id =5 where id % 5 = 0;