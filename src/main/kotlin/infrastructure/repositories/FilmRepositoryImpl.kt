package infrastructure.repositories

import application.responses.ResponseType
import core.models.Film
import core.repositories.FilmRepository
import infrastructure.data.DataContext
import infrastructure.data.utils.FILM
import infrastructure.data.utils.writeToJsonFilms

class FilmRepositoryImpl(private val dataContext: DataContext) : FilmRepository {
    override fun changeNameOfFilm(filmId: Int, newName: String): ResponseType {
        val films = dataContext.getAllFilms()

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
        val films = dataContext.getAllFilms()

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
        val films = dataContext.getAllFilms()
        films.add(film)

        writeToJsonFilms(films, dataContext.connectionString + FILM)
    }

    override fun getAllFilms(): MutableList<Film> {
        return dataContext.getAllFilms()
    }

    override fun getFilmById(id: Int): Film? {
        return dataContext.getAllFilms().find { it -> it.id == id }
    }
}