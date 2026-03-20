import java.sql.Connection
import java.sql.DriverManager

object DatabaseManager {
    fun getConnection(): Connection? {
        // On récupère les 3 infos séparément
        val url = System.getenv("DB_URL")
        val user = System.getenv("DB_USER")
        val pass = System.getenv("DB_PASSWORD")

        return try {
            Class.forName("org.postgresql.Driver")

            if (url != null && user != null && pass != null) {
                // Connexion Render (Paramètres séparés = Zéro erreur de parsing)
                DriverManager.getConnection(url, user, pass)
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