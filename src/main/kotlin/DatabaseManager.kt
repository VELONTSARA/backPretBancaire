import java.sql.Connection
import java.sql.DriverManager

object DatabaseManager {
    fun getConnection(): Connection? { // Supprimé java.sql. ici
        val dbUrl = System.getenv("DATABASE_URL")

        return try {
            Class.forName("org.postgresql.Driver")

            if (dbUrl != null) {
                val jdbcUrl = dbUrl.replace("postgres://", "jdbc:postgresql://")
                DriverManager.getConnection(jdbcUrl) // Supprimé java.sql. ici
            } else {
                DriverManager.getConnection( // Supprimé java.sql. ici
                    "jdbc:postgresql://localhost:5432/gestion_prets_db",
                    "postgres",
                    "BLANDIN4"
                )
            }
        } catch (e: Exception) {
            println("Erreur : ${e.message}")
            null
        }
    }
}