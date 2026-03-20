import java.sql.Connection
import java.sql.DriverManager
import java.net.URI

object DatabaseManager {
    fun getConnection(): Connection? {
        val dbUrl = System.getenv("DATABASE_URL")

        return try {
            // Chargement du driver PostgreSQL
            Class.forName("org.postgresql.Driver")

            if (dbUrl != null) {
                // Utilisation de la classe URI pour découper l'URL de Render proprement
                // Render donne : postgresql://user:password@hostname:port/dbname
                val dbUri = URI(dbUrl)

                val username = dbUri.userInfo.split(":")[0]
                val password = dbUri.userInfo.split(":")[1]

                // On reconstruit l'URL au format JDBC : jdbc:postgresql://hostname:port/dbname
                val jdbcUrl = "jdbc:postgresql://" + dbUri.host + ":" + dbUri.port + dbUri.path

                DriverManager.getConnection(jdbcUrl, username, password)
            } else {
                // Connexion locale pour ton ASUS
                DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/gestion_prets_db",
                    "postgres",
                    "BLANDIN4"
                )
            }
        } catch (e: Exception) {
            println("Erreur de connexion : ${e.message}")
            e.printStackTrace()
            null
        }
    }
}