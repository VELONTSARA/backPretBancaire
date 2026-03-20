import java.sql.Connection
import java.sql.DriverManager

object DatabaseManager {
    fun getConnection(): Connection? { // Supprimé java.sql. ici
        val dbUrl = System.getenv("DATABASE_URL")

        return try {
            Class.forName("org.postgresql.Driver")

            if (dbUrl != null) {
                // On gère les deux cas : "postgres://" ou "postgresql://"
                var jdbcUrl = dbUrl
                if (dbUrl.startsWith("postgres://")) {
                    jdbcUrl = dbUrl.replace("postgres://", "jdbc:postgresql://")
                } else if (dbUrl.startsWith("postgresql://")) {
                    jdbcUrl = dbUrl.replace("postgresql://", "jdbc:postgresql://")
                }
                DriverManager.getConnection(jdbcUrl)
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