import java.sql.Connection
import java.sql.DriverManager

object DatabaseManager {
    fun getConnection(): Connection? {
        // CORRECTION : On utilise exactement les noms vus sur ta photo Render
        val url = System.getenv("DATABASE_URL") // <--- Changé de DB_URL à DATABASE_URL
        val user = System.getenv("DB_USER")
        val pass = System.getenv("DB_PASSWORD")

        return try {
            Class.forName("org.postgresql.Driver")

            // On vérifie si la variable principale existe
            if (url != null) {
                println("✅ Connexion à Render en cours...")
                // Note : Si ton DATABASE_URL contient déjà le user/pass,
                // JDBC peut l'utiliser seul, mais passer user/pass en plus ne fait pas de mal.
                DriverManager.getConnection(url, user, pass)
            } else {
                println("⚠️ DATABASE_URL non trouvée, repli sur localhost")
                DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/gestion_prets_db",
                    "postgres",
                    "BLANDIN4"
                )
            }
        } catch (e: Exception) {
            println("❌ Erreur de connexion : ${e.message}")
            e.printStackTrace()
            null
        }
    }
}