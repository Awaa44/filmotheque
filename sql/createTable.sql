create table genres (
    id int primary key,
    libelle varchar(50) not null unique
);

create table participants(
    id int not null primary key identity,
    prenom varchar(50) not null,
    nom varchar(50) not null
);

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


create table acteurs(
                        filmId int not null,
                        participantId int not null
                    );

alter table acteurs add primary key (filmId, participantId);

alter table acteurs add constraint fk_acteurs_filmId foreign key(filmId)
    references films(id);
alter table acteurs add constraint fk_acteurs_participantId foreign key (participantId)
    references participants(id);

drop table membres

CREATE TABLE membres(    id INT IDENTITY CONSTRAINT PK_Membres PRIMARY KEY,
                         prenom VARCHAR(50) NOT NULL,
                         nom VARCHAR(50) NOT NULL,
                         pseudo VARCHAR(50) NOT NULL UNIQUE,
                         motDePasse VARCHAR(255) NOT NULL,
                         admin BIT NOT NULL default 0);
