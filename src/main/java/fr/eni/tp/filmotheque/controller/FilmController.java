package fr.eni.tp.filmotheque.controller;

import fr.eni.tp.filmotheque.bll.FilmService;
import fr.eni.tp.filmotheque.bll.GenreService;
import fr.eni.tp.filmotheque.bll.MembreService;
import fr.eni.tp.filmotheque.bll.ParticipantService;
import fr.eni.tp.filmotheque.bo.Film;
import fr.eni.tp.filmotheque.bo.Genre;
import fr.eni.tp.filmotheque.bo.Participant;
import fr.eni.tp.filmotheque.dto.FilmDto;
import fr.eni.tp.filmotheque.dto.MembreInscriptionDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FilmController {

    private final Logger logger = LoggerFactory.getLogger(FilmController.class);

    private FilmService filmService;
    private ParticipantService participantService;
    private GenreService genreService;
    private MembreService membreService;

    public FilmController(FilmService filmService, ParticipantService participantService, GenreService genreService,
                          MembreService membreService) {
        this.filmService = filmService;
        this.participantService = participantService;
        this.genreService = genreService;
        this.membreService = membreService;
    }

    @GetMapping({"/", "accueil"})
    public String accueil() {
        return "accueil";
    }

    @GetMapping("/films/inscription")
    public String inscriptionMembre(Model model) {
        if (!model.containsAttribute("membre")) {
            model.addAttribute("membre", new MembreInscriptionDto());
        }
        return "view-inscription";
    }

    @PostMapping("/films/inscription")
    public String inscriptionMembre(@Valid @ModelAttribute("membre") MembreInscriptionDto membreDto,
                                    BindingResult resultat, Model model, RedirectAttributes redirectAttr) {

        logger.info("Tentative inscription membre : {}", membreDto.getPseudo());

        //vérifier les erreurs de validation
        if (resultat.hasErrors()) {
            logger.warn("Erreur d'inscription membre : {}", membreDto.getPseudo());
            redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.membre", resultat);
            redirectAttr.addFlashAttribute("membre", membreDto);
            return "redirect:/films/inscription";
        }

        //véfirifier que le pseudo n'existe pas
        if (membreService.existsMembreByPseudo(membreDto.getPseudo())) {
            redirectAttr.addFlashAttribute("erreurInscription", "Ce pseudo est déjà utilisé");
            redirectAttr.addFlashAttribute("membre", membreDto);
            return "redirect:/films/inscription";
        }

        //inscire le membre
        try {
            membreService.inscrireMembre(membreDto);
            logger.info("Membre créé avec succès : {}", membreDto.getPseudo());
            //message pour l'utilisateur dans le HTML
            redirectAttr.addFlashAttribute("success", "Inscription réussie");
            return "redirect:/films/login";

        } catch (Exception e) {
            logger.error("Erreur d'inscription membre :{}", membreDto.getPseudo(), e);
            //en cas d'erreur envoyer un message à l'utilisateur
            redirectAttr.addFlashAttribute("error", "Inscription échouée");
            redirectAttr.addFlashAttribute("membre", membreDto);

            return "redirect:/films/inscription";
        }
    }

    @GetMapping("films/login")
    public String connectionMembre() {
        return "view-login";
    }

    @GetMapping("/films/detail")
    public String afficherUnFilm(@RequestParam(name = "id") int identifiant, Model model) {

        Film film = this.filmService.consulterFilmParId(identifiant);
        System.out.println(film);

        model.addAttribute("film", film);
        return "view-film-detail";
    }


    @GetMapping("/films")
    public String afficherFilms(Model model) {
        logger.debug("debut afficherFilms -niveau debug");
        logger.info("debut afficherFilms - niveau info");
        logger.warn("debut afficherFilms - niveau warning");
        logger.error("debut afficherFilms - niveau error");

        List<Film> films = this.filmService.consulterFilms();
        for (Film film : films) {
            //System.out.println(film);
            logger.info("film : {}", film);
        }

        model.addAttribute("films", films);
        logger.debug("fin afficherFilms - niveau debug");

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
    public String creerFilm(@Valid @ModelAttribute("film") FilmDto filmDto, BindingResult resultat,
                            RedirectAttributes redirectAttr) {

        //log d'information
        logger.info("Tentative de création de film :{}", filmDto.getTitre());

        //vérifier les erreurs avant de copier dans le BO
        if (resultat.hasErrors()) {
            logger.warn("Erreurs de validation pour le film : {}", filmDto.getTitre());
            // ✅ Sauvegarder les erreurs
            redirectAttr.addFlashAttribute("org.springframework.validation.BindingResult.film", resultat);
            // ✅ Sauvegarder les données saisies
            //attention a bien mettre filmDto et non film
            redirectAttr.addFlashAttribute("film", filmDto);

            return "redirect:/films/creer";
        }

        //try catch
        try {
            //CONVERSION DTO => BO
            //on créé une nouvelle instance film avec le BO Film
            Film film = new Film();
            //pour ensuite copier les données de l'objet DTO dans l'objet BO film
            BeanUtils.copyProperties(filmDto, film);

            //associer le genre
            if (filmDto.getIdGenre() != null) {
                Genre genre = genreService.findGenreById(filmDto.getIdGenre());
                film.setGenre(genre);
            }

            //associer le réalisteur
            if (filmDto.getIdRealisateur() != null) {
                Participant realisateur = participantService.consulterParticipantById(filmDto.getIdRealisateur());
                film.setRealisateur(realisateur);
            }

            //associer les acteurs
            //gestion d'une liste d'acteur, donc il faut lier seulement les acteurs sélectionnés dans le html
            List<Participant> acteurs = new ArrayList<>();
            if (filmDto.getIdsActeurs() != null && !filmDto.getIdsActeurs().isEmpty()) {
                for (Integer idActeur : filmDto.getIdsActeurs()) {
                    Participant acteur = participantService.consulterParticipantById(idActeur);
                    if (acteur != null) {
                        acteurs.add(acteur);
                    }
                }
            }
            film.setActeurs(acteurs);

            //CREER LE FILM
            //ensuite on crée le film avec la fonction creerFilm de l'impl dans le BO film
            this.filmService.creerFilm(film);
            logger.info("Film : {}", film.getTitre());
            //ne sert à rien car le model est perdu avec la redirection
            //model.addAttribute("film", film);

            return "redirect:/films/detail?id=" + film.getId();
        } catch (Exception ex) {
            //message pour le dev en console
            logger.error("Erreur lors de la création du film", ex);
            //message pour l'utilisateur, affichage en html
            //En cas d'erreur, renvoyer vers le formulaire avec message = message d'erreur système dans le html
            redirectAttr.addFlashAttribute("erreur", "Erreur lors de la création du film");
            //Préserve les données saisies par l'utilisateur pour qu'il ne perde pas son travail
            redirectAttr.addFlashAttribute("film", filmDto);

            return "redirect:/films/creer";
        }
    }
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

