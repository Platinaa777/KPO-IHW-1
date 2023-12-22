package main.utils

import infrastructure.data.DataContext
import core.repositories.CinemaRepository
import application.services.interfaces.CinemaService

class App() {

    private var cinemaService: CinemaService? = null
    private var cinemaRepository: CinemaRepository? = null
    private var dbContext : DataContext? = null

    fun addCinemaService(cinemaService: CinemaService) {
        this.cinemaService = cinemaService
    }

    fun addCinemaRepository(cinemaRepository: CinemaRepository) {
        this.cinemaRepository = cinemaRepository
    }

    fun addDbContext(dbContext: DataContext) {
        this.dbContext = dbContext
    }

    fun getDbContext() : DataContext {
        return dbContext!!
    }

    fun getCinemaService() : CinemaService {
        return cinemaService!!
    }

    fun getCinemaRepository() : CinemaRepository {
        return cinemaRepository!!
    }
}