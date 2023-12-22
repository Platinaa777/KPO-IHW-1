package infrastructure.auth

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
