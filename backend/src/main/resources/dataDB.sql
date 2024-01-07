INSERT INTO person(name, password, role) VALUES ('admin', '$2a$10$NzE3.ehopU.LN6eqFby/nOtDNHq/x7XPjmxPqDoRKY2DDEqRlJl56', 'ROLE_ADMIN');
INSERT INTO person(name, password, role) VALUES ('trainer', '$2a$10$ziRMsJUXmUgTLSPenkWY4udkty4NOGmWuw/dyqjhZF3ky0mAAZsDK', 'ROLE_TRAINER');
INSERT INTO person(name, password, role) VALUES ('user', '$2a$10$DVx9f1djz1PI/SK3J4uhseGbaGu0XuDZIJD/p8Zwthix.DvyXFtvm', 'ROLE_USER');
INSERT INTO person(name, password, role) VALUES ('premium', '$2a$10$2eAE6E/J1HXSw1EiQAukYeyxRdnzU73sbzbV1QvK.gNMdYwi43g.S', 'ROLE_PREMIUM');

INSERT INTO trainer(person_id, rating) VALUES (2, 1);

INSERT INTO Exercise(name, muscle, description, video, image, trainer_id) VALUES ('прес от пола', 'прес', 'Классическоеупражнение на мышцы преса', 'D:\Project\db\pressVideo.MOV', 'D:\Project\db\press.jpg', 1);
INSERT INTO Exercise(name, muscle, description, video, image, trainer_id) VALUES ('классическое приседание', 'квадрицепсы', 'основа, которой нужно овладеть перед тем, как приступать к другим разновидностям этого приседаний', 'D:\Project\db\squatVideo.MOV', 'D:\Project\db\hands.jpg', 1);
INSERT INTO Exercise(name, muscle, description, video, image, trainer_id) VALUES ('классическое отжимание', 'большая грудная', 'Примите упор лежа на руках и ногах. Ноги должны быть вместе, а ваши ладони направьте вперед, так чтобы они были на ширине плеч. Затем необходимо выпрямить руки, при этом важно чтобы шея, спина и ноги были прямыми. Сгибайте руки в локтях, опускаясь к полу.', 'D:\Project\db\push-upsVideo.MOV', 'D:\Project\db\hands.jpg', 1);

INSERT INTO Task(name, description, exercise_id, count, type_count, trainer_id) VALUES ('пресс новичка', 'зачем нужно это поле?', 1, 10, 0, 1);
INSERT INTO Task(name, description, exercise_id, count, type_count, trainer_id) VALUES ('пресс продвинутого', 'зачем нужно это поле?', 1, 20, 0, 1);
INSERT INTO Task(name, description, exercise_id, count, type_count, trainer_id) VALUES ('пресс профи', 'зачем нужно это поле?', 1, 30, 0, 1);

INSERT INTO Task(name, description, exercise_id, count, type_count, trainer_id) VALUES ('приседание новичка', 'зачем нужно это поле?', 2, 20, 0, 1);
INSERT INTO Task(name, description, exercise_id, count, type_count, trainer_id) VALUES ('приседание продвинутого', 'зачем нужно это поле?', 2, 50, 0, 1);
INSERT INTO Task(name, description, exercise_id, count, type_count, trainer_id) VALUES ('приседание профи', 'зачем нужно это поле?', 2, 100, 0, 1);

INSERT INTO Task(name, description, exercise_id, count, type_count, trainer_id) VALUES ('отжимание новичка', 'зачем нужно это поле?', 3, 10, 0, 1);
INSERT INTO Task(name, description, exercise_id, count, type_count, trainer_id) VALUES ('отжимание супер новичка', 'зачем нужно это поле?', 3, 15, 0, 1);
INSERT INTO Task(name, description, exercise_id, count, type_count, trainer_id) VALUES ('отжимание продвинутого', 'зачем нужно это поле?', 3, 20, 0, 1);
INSERT INTO Task(name, description, exercise_id, count, type_count, trainer_id) VALUES ('отжимание супер продвинутого', 'зачем нужно это поле?', 3, 30, 0, 1);
INSERT INTO Task(name, description, exercise_id, count, type_count, trainer_id) VALUES ('отжимание профи', 'зачем нужно это поле?', 3, 40, 0, 1);
INSERT INTO Task(name, description, exercise_id, count, type_count, trainer_id) VALUES ('отжимание супер профи', 'зачем нужно это поле?', 3, 50, 0, 1);

INSERT INTO Task(name, description, exercise_id, count, type_count, trainer_id) VALUES ('тест', 'для теста типа', 1, 60, 1, 1);
INSERT INTO Task(name, description, exercise_id, count, type_count, trainer_id) VALUES ('тест', 'для теста типа', 1, 2, 2, 1);
