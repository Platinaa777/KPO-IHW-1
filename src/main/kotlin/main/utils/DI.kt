package main.utils

import infrastructure.data.DataCinemaContext
import core.repositories.ICinemaRepository
import application.services.interfaces.ICinemaService

class App() {

    private var cinemaService: ICinemaService? = null
    private var cinemaRepository: ICinemaRepository? = null
    private var dbContext : DataCinemaContext? = null

    fun addCinemaService(cinemaService: ICinemaService) {
        this.cinemaService = cinemaService
    }

    fun addCinemaRepository(cinemaRepository: ICinemaRepository) {
        this.cinemaRepository = cinemaRepository
    }

    fun addDbContext(dbContext: DataCinemaContext) {
        this.dbContext = dbContext
    }

    fun getDbContext() : DataCinemaContext {
        return dbContext!!
    }

    fun getCinemaService() : ICinemaService {
        return cinemaService!!
    }

    fun getCinemaRepository() : ICinemaRepository {
        return cinemaRepository!!
    }
}