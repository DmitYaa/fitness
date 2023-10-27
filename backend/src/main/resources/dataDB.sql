INSERT INTO person(name, password, role) VALUES ('admin', '$2a$10$NzE3.ehopU.LN6eqFby/nOtDNHq/x7XPjmxPqDoRKY2DDEqRlJl56', 'ROLE_ADMIN');
INSERT INTO person(name, password, role) VALUES ('trainer', '$2a$10$ziRMsJUXmUgTLSPenkWY4udkty4NOGmWuw/dyqjhZF3ky0mAAZsDK', 'ROLE_TRAINER');
INSERT INTO person(name, password, role) VALUES ('user', '$2a$10$DVx9f1djz1PI/SK3J4uhseGbaGu0XuDZIJD/p8Zwthix.DvyXFtvm', 'ROLE_USER');
INSERT INTO person(name, password, role) VALUES ('premium', '$2a$10$2eAE6E/J1HXSw1EiQAukYeyxRdnzU73sbzbV1QvK.gNMdYwi43g.S', 'ROLE_PREMIUM');

INSERT INTO trainer(person_id, rating) VALUES (2, 1);
INSERT INTO Exercise(name, muscle, description, video, image, trainer_id) VALUES ('прес от пола', 'прес', 'Классическоеупражнение на мышцы преса', null, null, 1);
INSERT INTO Exercise(name, muscle, description, video, image, trainer_id) VALUES ('классическое приседание', 'квадрицепсы', 'основа, которой нужно овладеть перед тем, как приступать к другим разновидностям этого приседаний', null, null, 1);
INSERT INTO Exercise(name, muscle, description, video, image, trainer_id) VALUES ('классическое отжимание', 'большая грудная', 'Примите упор лежа на руках и ногах. Ноги должны быть вместе, а ваши ладони направьте вперед, так чтобы они были на ширине плеч. Затем необходимо выпрямить руки, при этом важно чтобы шея, спина и ноги были прямыми. Сгибайте руки в локтях, опускаясь к полу.', null, null, 1);