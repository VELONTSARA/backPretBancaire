import java.sql.Connection
import java.sql.DriverManager

object DatabaseManager {
    private const val URL = "jdbc:postgresql://localhost:5432/gestion_prets_db"
    private const val USER = "postgres"
    private const val PASSWORD = "BLANDIN4" // <--- Change ici !

    fun getConnection(): Connection? {
        return try {
            Class.forName("org.postgresql.Driver")
            DriverManager.getConnection(URL, USER, PASSWORD)
        } catch (e: Exception) {
            println("Erreur de connexion : ${e.message}")
            null
        }
    }
}