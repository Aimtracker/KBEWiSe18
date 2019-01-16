SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE list_song;
TRUNCATE TABLE songs;
SET FOREIGN_KEY_CHECKS = 1;
ALTER TABLE SONGS ALTER COLUMN ID RESTART WITH 3;
INSERT INTO songs (id, album, artist, released, title) VALUES
(1, 'Lateralus', 'Tool', 2001, 'Ticks And Leeches'),
(2, 'Jar Of Flies', 'Alice In Chains', 1994, 'Rotten Apple');