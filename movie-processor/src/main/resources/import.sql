INSERT INTO director(id, firstName, lastName, country) VALUES (nextval('seq_director'), 'Ozi', 'Dobur', 'NL');
INSERT INTO movie(id, title, description, country,director_id) VALUES (nextval('seq_movie'), 'Cherry', 'Dram', 'NL',1);