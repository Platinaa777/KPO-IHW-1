package auth

import core.UserAuthOption
import java.security.MessageDigest
import java.util.*

class AuthServer {
    private val passwords = HashMap<String, String>()

    fun tryToLogin(user: String, password: String): Boolean {
        if (!passwords.containsKey(user) || user.isEmpty() || password.isEmpty())
            return false

        val hashedPassword = encryptPassword(password)
        val storedPassword = passwords[user]

        return storedPassword == hashedPassword
    }

    fun signUp(user: String, password: String): Boolean {
        if (passwords.containsKey(user) || user.isEmpty() || password.isEmpty()) {
            return false
        }

        val hashedPassword = encryptPassword(password)
        passwords[user] = hashedPassword

        return true
    }

    private fun encryptPassword(password: String): String {
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        return Base64.getEncoder().encodeToString(digest)
    }
}

fun activateAuthentication(authServer: AuthServer, currentSession: UserAuthOption) : UserAuthOption {
    var choice: String = ""

    if (!authServer.tryToLogin(currentSession.userName, currentSession.password)) {
        while (true) {
            println("Session does not exist, try to log in or sign up")
            println("1) sign up")
            println("2) log in")
            print("Push button - ")
            choice = readln()

            if (choice == "1") {
                var data = inputUserData()
                if (authServer.signUp(data.first, data.second)) {
                    return UserAuthOption(data.first, data.second)
                } else {
                    println("User with this name is already exist")
                }
            } else if (choice == "2") {
                var data = inputUserData()
                if (authServer.tryToLogin(data.first, data.second)) {
                    return UserAuthOption(data.first, data.second)
                } else {
                    println("User does not exist")
                }
            } else {
                println("Invalid input")
            }
        }
    }
    return currentSession
}

fun inputUserData() : Pair<String, String> {
    print("Input user name: ")
    val name = readln()
    print("Input password: ")
    val password = readln()

    return name to password
}