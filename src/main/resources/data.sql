-- Insérer des données de test
INSERT INTO genres (id, libelle) VALUES (1, 'Animation');
INSERT INTO genres (id, libelle) VALUES (2, 'Science-fiction');
INSERT INTO genres (id, libelle) VALUES (3, 'Documentaire');
INSERT INTO genres (id, libelle) VALUES (4, 'Action');
INSERT INTO genres (id, libelle) VALUES (5, 'Comédie');
INSERT INTO genres (id, libelle) VALUES (6, 'Drame');


--PARTICIPANTS
INSERT INTO participants (nom, prenom) VALUES ('Spielberg', 'Steven');
INSERT INTO participants (nom, prenom) VALUES ('Cronenberg', 'David');
INSERT INTO participants (nom, prenom) VALUES ('Boon', 'Dany');

INSERT INTO participants (nom, prenom) VALUES ('Attenborough', 'Richard');
INSERT INTO participants (nom, prenom) VALUES ('Goldblum', 'Jeff');
INSERT INTO participants (nom, prenom) VALUES ('Davis', 'Geena');
INSERT INTO participants (nom, prenom) VALUES ('Rylance', 'Mark');
INSERT INTO participants (nom, prenom) VALUES ('Barnhill', 'Ruby');
INSERT INTO participants (nom, prenom) VALUES ('Merad', 'Kad');


--FILMS
INSERT INTO films(titre, annee, duree, synopsis, genreId, realisateurId)
VALUES ('Jurassic Park', 1993, 128,
        'Le film raconte l''histoire d''un milliardaire et son équipe de généticiens parvenant à ramener à la vie des dinosaures grâce au clonage.',
        1, 1);

INSERT INTO films(titre, annee, duree, synopsis, genreId, realisateurId)
VALUES ('The Fly', 1986, 95,
        'Il s''agit de l''adaptation cinématographique de la nouvelle éponyme de l''auteur George Langelaan.',
        1, 2);

INSERT INTO films(titre, annee, duree, synopsis, genreId, realisateurId)
VALUES ('The BFG', 2016, 117,
        'Le Bon Gros Géant est un géant bien différent des autres habitants du Pays des Géants.',
        4, 1);

INSERT INTO films(titre, annee, duree, synopsis, genreId, realisateurId)
VALUES ('Bienvenue chez les Chtis', 2008, 106,
        'Philippe Abrams est directeur de la poste de Salon-de-Provence. Il est marié à Julie, dont le caractère dépressif lui rend la vie impossible. Pour lui faire plaisir, Philippe fraude afin d''obtenir une mutation sur la Côte d''Azur. Mais il est démasqué: il sera muté à Bergues, petite ville du Nord.',
        4, 3);


--ACTEURS
INSERT INTO acteurs(filmId, participantId) VALUES (1,4);
INSERT INTO acteurs(filmId, participantId) VALUES (1,5);

INSERT INTO acteurs(filmId, participantId) VALUES (2,5);
INSERT INTO acteurs(filmId, participantId) VALUES (2,6);

INSERT INTO acteurs(filmId, participantId) VALUES (3,7);
INSERT INTO acteurs(filmId, participantId) VALUES (3,8);

INSERT INTO acteurs(filmId, participantId) VALUES (4,3);
INSERT INTO acteurs(filmId, participantId) VALUES (4,9);