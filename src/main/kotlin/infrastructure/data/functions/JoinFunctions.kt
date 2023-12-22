package infrastructure.data.functions

import application.models.SessionWithFilmData
import core.models.Film
import core.models.Session
import infrastructure.data.entities.MergeSessionsWithFilms

class JoinFunctions {

    fun JoinSessionAndFilm(
        sessions: MutableList<Session>,
        films: MutableList<Film>): MutableList<MergeSessionsWithFilms> {
        var joinedData = mutableListOf<MergeSessionsWithFilms>()
        for (session in sessions) {
            for (film in films) {
                if (session.Id == film.sessionId) {
                    joinedData.add(MergeSessionsWithFilms(session.Id, session.startingHour, film.durationMinutes))
                }
            }
        }

        return joinedData
    }

    fun JoinGettingFullData(
        sessions: MutableList<Session>,
        films: MutableList<Film>): MutableList<SessionWithFilmData> {

        var joinedData = mutableListOf<SessionWithFilmData>()
        for (session in sessions) {
            for (film in films) {
                if (session.Id == film.sessionId) {
                    joinedData.add(SessionWithFilmData(
                        session.Id,
                        session.startingHour,
                        session.seats,
                        film.name,
                        film.description,
                        film.rating,
                        film.durationMinutes))
                }
            }
        }

        return joinedData
    }
}