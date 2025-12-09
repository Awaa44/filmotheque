-- =====================================
-- SUPPRESSION DES TABLES
-- =====================================
DROP TABLE IF EXISTS acteurs;
DROP TABLE IF EXISTS films;
DROP TABLE IF EXISTS participants;
DROP TABLE IF EXISTS genres;

-- =====================================
-- CRÉATION DES TABLES
-- =====================================

-- Genres AVEC IDENTITY (auto-increment)
create table genres (
                        id int primary key,
                        libelle varchar(50) not null unique
);

-- Participants AVEC IDENTITY
create table participants(
                             id int not null primary key identity,
                             prenom varchar(50) not null,
                             nom varchar(50) not null
);

-- Films AVEC IDENTITY
create table films(
                      id int primary key identity not null,
                      titre varchar(50) not null,
                      annee int not null,
                      duree int not null,
                      synopsis varchar(500) not null,
                      genreId int not null,
                      realisateurId int not null
);

alter table films add constraint fk_films_genre_id foreign key(genreId)
    references genres(id);

alter table films add constraint fk_films_realisateur_id foreign key(realisateurId)
    references participants(id);

-- Table acteurs
create table acteurs(
                filmId int not null,
                participantId int not null
);

alter table acteurs add primary key (filmId, participantId);

alter table acteurs add constraint fk_acteurs_filmId foreign key(filmId)
    references films(id);
alter table acteurs add constraint fk_acteurs_participantId foreign key (participantId)
    references participants(id);