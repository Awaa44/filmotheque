package fr.eni.tp.filmotheque.controller;

import fr.eni.tp.filmotheque.bll.FilmService;
import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.dto.FilmDto;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FilmController {

    private FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    @GetMapping("/films/detail")
    public String afficherUnFilm(@RequestParam(name="id") long identifiant, Model model) {

        Film film = this.filmService.consulterFilmParId(identifiant);
        System.out.println(film);

        model.addAttribute("film", film);
        return "view-film-detail";
    }


    @GetMapping("/films")
    public String afficherFilms(Model model) {

        List<Film> films = this.filmService.consulterFilms();
        for (Film film : films) {
            System.out.println(film);
        }

        model.addAttribute("films", films);

        return "view-films";
    }

    @GetMapping("/films/creer")
    public String creerFilm(Model model) {
        //attention avec le redirect, le get écrase les données, et donc le FlashAttribute qui remonte les messages
        //d'erreur, donc il faut vérfier si film existe déja dans le Model, si oui, ne pas le remplacer
        if (!model.containsAttribute("film")) {
            model.addAttribute("film", new FilmDto());
        }
        return "view-film-form";
    }

    @PostMapping("/films/creer")
    //ne pas oublier @Valid devant @ModelAttribute sinon les erreurs ne s'affichent pas
    public String creerFilm(@Valid @ModelAttribute("film") FilmDto filmDto, BindingResult resultat, Model model,
                            RedirectAttributes redirectAttr) {

        //vérifier les erreurs avant de copier dans le BO
        if(resultat.hasErrors()) {

            redirectAttr.addFlashAttribute( "org.springframework.validation.BindingResult.film", resultat);
            //attention a bien mettre filmDto et non film
            redirectAttr.addFlashAttribute("film", filmDto);
            return "redirect:/films/creer";
        }

        //on créé une nouvelle instance film avec le BO Film
        Film film = new Film();
        //pour ensuite copier les données de l'objet DTO dans l'objet BO film
        BeanUtils.copyProperties(filmDto, film);

        // Copier manuellement le genre (noms différents)
        //on ne peut pas utiliser BeanUtils car le nom de l'attribut dans le DTO est différent de celui
        //qui est dans Genre, donc on passe en paramère le genre choisi / La méthode cherche dans la liste
        // de tous les genres disponibles / Elle retourne le genre complet qui correspond à cet ID
        Genre genre = filmService.consulterGenreParId(filmDto.getIdGenre());
        film.setGenre(genre);

        //associer le réalisteur
        Participant participant = filmService.consulterParticipantParId(filmDto.getIdRealisateur());
        film.setRealisateur(participant);

        //gestion d'une liste d'acteur, donc il faut lier seulement les acteurs sélectionnés dans le html
        List<Participant> acteurs = new ArrayList<>();
        if(filmDto.getIdsActeurs() != null && !filmDto.getIdsActeurs().isEmpty()) {
            for (Long idActeur : filmDto.getIdsActeurs()) {
                Participant acteur = filmService.consulterParticipantParId(idActeur);
                if(acteur != null) {
                    acteurs.add(acteur);
                }
            }
        }
        film.setActeurs(acteurs);

        //ensuite on crée le film avec la fonction creerFilm de l'impl dans le BO film
        this.filmService.creerFilm(film);
        //ne sert à rien car le model est perdu avec la redirection
        //model.addAttribute("film", film);

        return "redirect:/films/detail?id=" + film.getId();
    }

    //POSSIBLE DE METTRE @ApplicationScope ici pour remplacer la session par application pour les données stable
    //identique à aller chercher directement service dans le html pour genre

    //plus besoin car on appelle directement les genre en scope à partir de la liste des genre dans le bean service
    //ne pas oublier de renommer le service car après il sera supprimé et remplacé
   /* //on charge les genres en session
    @ModelAttribute("genresEnSession")
    @ApplicationScope
    public List<Genre> chargerGenres(){
        System.out.println("Chargement en Session - GENRES");
        return filmService.consulterGenres();
    }*/
}
