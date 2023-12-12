package main

import data.DataContext
import repository.ICinemaRepository
import services.ICinemaService

class App() {

    private var cinemaService: ICinemaService? = null
    private var cinemaRepository: ICinemaRepository? = null
    private var dbContext : DataContext? = null

    fun addCinemaService(cinemaService: ICinemaService) {
        this.cinemaService = cinemaService
    }

    fun addCinemaRepository(cinemaRepository: ICinemaRepository) {
        this.cinemaRepository = cinemaRepository
    }

    fun addDbContext(dbContext: DataContext) {
        this.dbContext = dbContext
    }

    fun getDbContext() : DataContext {
        return dbContext!!
    }

    fun getCinemaService() : ICinemaService {
        return cinemaService!!
    }

    fun getCinemaRepository() : ICinemaRepository {
        return cinemaRepository!!
    }
}