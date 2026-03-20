fun main() {
    println("Tentative de connexion à PostgreSQL...")

    val connection = DatabaseManager.getConnection()

    if (connection != null && !connection.isClosed) {
        println("✅ BRAVO ! La connexion a réussi.")
        println("Base de données : " + connection.metaData.databaseProductName)

        // On teste si on peut lire la table qu'on a créée tout à l'heure
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery("SELECT count(*) FROM pret_bancaire")

        if (resultSet.next()) {
            val nbLignes = resultSet.getInt(1)
            println("Nombre de prêts trouvés en base : $nbLignes")
        }

        connection.close()
    } else {
        println("❌ ÉCHEC : Impossible de se connecter. Vérifie ton mot de passe ou si PostgreSQL est lancé.")
    }
}