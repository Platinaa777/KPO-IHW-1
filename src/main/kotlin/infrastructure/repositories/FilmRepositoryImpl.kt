package infrastructure.repositories

import application.responses.ResponseType
import core.models.Film
import core.repositories.FilmRepository
import infrastructure.data.DataContext
import infrastructure.data.utils.FILM
import infrastructure.data.utils.writeToJsonFilms

class FilmRepositoryImpl(val dataContext: DataContext) : FilmRepository {
    override fun changeNameOfFilm(filmId: Int, newName: String): ResponseType {
        var films = dataContext.getAllFilms()

        for (film in films) {
            if (film.id == filmId) {
                film.name = newName
                dataContext.saveChangesFilms(films)
                return ResponseType.SUCCESS
            }
        }

        return ResponseType.FILM_IS_NOT_EXIST
    }

    override fun changeDescriptionOfFilm(filmId: Int, newDescription: String): ResponseType {
        var films = dataContext.getAllFilms()

        for (film in films) {
            if (film.id == filmId) {
                film.description = newDescription
                dataContext.saveChangesFilms(films)
                return ResponseType.SUCCESS
            }
        }

        return ResponseType.FILM_IS_NOT_EXIST
    }

    override fun addFilm(film: Film) {
        var films = dataContext.getAllFilms()
        films.add(film)

        writeToJsonFilms(films, dataContext.connectionString + FILM)
    }
}