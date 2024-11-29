CREATE SEQUENCE artist_id_generator_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE spotify.artist (
  id BIGINT NOT NULL,
   name VARCHAR(255),
   CONSTRAINT pk_artist PRIMARY KEY (id)
);

INSERT INTO spotify.artist (id, name) VALUES (NEXT VALUE FOR artist_id_generator_sequence, 'John Doe');
INSERT INTO spotify.artist (id, name) VALUES (NEXT VALUE FOR artist_id_generator_sequence, 'Jane Smith');
INSERT INTO spotify.artist (id, name) VALUES (NEXT VALUE FOR artist_id_generator_sequence, 'Michael Brown');

