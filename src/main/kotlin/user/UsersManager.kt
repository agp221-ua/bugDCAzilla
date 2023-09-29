package user

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.security.spec.KeySpec
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


class UsersManager {
    companion object {
        private val FILE_NAME = "users.bd";
        private const val ALGORITHM = "PBKDF2WithHmacSHA512"
        private const val ITERATIONS = 120_000
        private const val KEY_LENGTH = 256
        private const val SECRET = "DCA_nice_subject"
        val usersManager: UsersManager = UsersManager()
    }

    private val users: MutableMap<String, User> = mutableMapOf()

    var currentUser: User? = null

    init {
        loadUsers()
    }

    private fun loadUsers() {
        val file = File(FILE_NAME)
        if (!file.exists())
            file.createNewFile()

        file.inputStream().bufferedReader().forEachLine {
            val user = parseLine(it) ?: return@forEachLine
            users[user.name] = user
        }
    }

    private fun parseLine(line: String): User? {
        val fields = line.split("#")
        if (fields.size != 4)
            return null
        val admin = fields[3].toBooleanStrictOrNull() ?: return null
        return User(fields[1], fields[2], admin)
    }

    private fun parseUser(user: User): String {
        return "${user.name}#${user.passHash}#${user.admin}"
    }

    fun login(name: String, pass: String): Boolean {
        if(!users.containsKey(name)) return false
        if(users[name]!!.passHash != hashPass(pass)) return false
        currentUser = users[name]
        return true
    }

    fun register(name: String, pass: String): Boolean {
        if(users.containsKey(name)) return false
        val user = User(name, hashPass(pass), false)
        users[name] = user
        addUser(user)
        currentUser = user
        return true
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun hashPass(pass: String): String {
        val factory: SecretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM)
        val spec: KeySpec = PBEKeySpec(pass.toCharArray(), byteArrayOf(0), ITERATIONS, KEY_LENGTH)
        val key: SecretKey = factory.generateSecret(spec)
        val hash: ByteArray = key.encoded
        return hash.toHexString()
    }

    private fun addUser(user: User) {
        try {
            val file = FileWriter(FILE_NAME, true)
            val writer = BufferedWriter(file)

            writer.write(parseUser(user))
            writer.newLine() // Agregar un salto de línea después de la línea que se quiere agregar.

            writer.close()

        } catch (_: IOException) {
        }
    }

}