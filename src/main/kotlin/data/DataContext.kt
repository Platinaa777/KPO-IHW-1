package data

import models.Session

class DataContext {

    val connectionString = "./data/cinema.json"
    var isCached = false; // for not fetching data from file

    var listOfSessions = mutableListOf<Session>()

    fun getSessionId(id: Int) : Session? {
        if (isCached) {
            if (id < 0 || id >= listOfSessions.size) {
                return null
            }

            return listOfSessions[id]
        }

        listOfSessions = readAllFromJson(connectionString).map{ Mapper().map(it) }.toMutableList()
        if (id < 0 || id >= listOfSessions.size) {
            return null
        }

        isCached = true
        return listOfSessions[id]
    }

    fun getAllSessions() : MutableList<Session> {
        if (isCached) {
            return listOfSessions
        }
        listOfSessions = readAllFromJson(connectionString).map{ Mapper().map(it) }.toMutableList()
        isCached = true
        return listOfSessions
    }

    fun insert(session: Session) {
        isCached = false
        listOfSessions.add(session)

        clearJsonFile(connectionString)

        for (item in listOfSessions) {
            appendSessionToJson(createSessionJSON(item.film, item.startingHour), connectionString)
        }
        isCached = true
    }

    fun getSizeOfStorage() : Int {
        if (isCached) {
            return listOfSessions.size
        }

        listOfSessions = readAllFromJson(connectionString).map{ Mapper().map(it) }.toMutableList()
        isCached = true
        return listOfSessions.size
    }

    fun saveChangesForSession(id: Int, newSession: Session) {
        listOfSessions[id] = newSession

        clearJsonFile(connectionString)

        for (item in listOfSessions) {
            appendSessionToJson(createSessionJSON(item.film, item.startingHour), connectionString)
        }
        isCached = true
    }

}

