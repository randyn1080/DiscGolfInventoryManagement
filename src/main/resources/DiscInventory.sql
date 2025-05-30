CREATE TABLE discs (
   disc_id INT PRIMARY KEY AUTO_INCREMENT,
   manufacturer VARCHAR(100) NOT NULL,
   mold VARCHAR(100) NOT NULL,
   color VARCHAR(100) NOT NULL,
   weight INT NOT NULL,
   speed INT NOT NULL,
   glide INT NOT NULL,
   turn INT NOT NULL,
   fade INT NOT NULL,
   special_notes TEXT
);

INSERT INTO discs (manufacturer, mold, color, weight, speed, glide, turn, fade, special_notes)
VALUES ('Discraft', 'Luna', 'blue swirl', 171, 3, 3, 0, 2, 'little luna emblem stamp');

INSERT INTO discs (manufacturer, mold, color, weight, speed, glide, turn, fade, special_notes)
VALUES ('Innova', 'Destroyer', 'red', 175, 12, 5, -1, 3, 'star plastic');

INSERT INTO discs (manufacturer, mold, color, weight, speed, glide, turn, fade, special_notes)
VALUES ('MVP', 'Volt', 'green', 168, 8, 5, -1, 2, 'cosmic neutron plastic');

