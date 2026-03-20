import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

// L'URL sera : http://ton_ip:8080/api/prets
@Path("/prets")
class PretResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getTousLesPrets(): Response {
        val liste = mutableListOf<Map<String, Any>>()
        val conn = DatabaseManager.getConnection()

        if (conn != null) {
            val query = "SELECT * FROM pret_bancaire"
            val statement = conn.createStatement()
            val resultSet = statement.executeQuery(query)

            while (resultSet.next()) {
                val pret = mapOf(
                    "num_compte" to resultSet.getString("num_compte"),
                    "nom_client" to resultSet.getString("nom_client"),
                    "nom_banque" to resultSet.getString("nom_banque"),
                    "montant" to resultSet.getDouble("montant"),
                    "date_pret" to resultSet.getString("date_pret"),
                    "taux_de_pret" to resultSet.getDouble("taux_de_pret")
                )
                liste.add(pret)
            }
            conn.close()
            return Response.ok(liste).build()
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build()
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun ajouterPret(pret: Pret): Response {
        val conn = DatabaseManager.getConnection()
        return try {
            if (conn != null) {
                val query = "INSERT INTO pret_bancaire (num_compte, nom_client, nom_banque, montant, date_pret, taux_de_pret) VALUES (?, ?, ?, ?, ?, ?)"
                val pstmt = conn.prepareStatement(query)

                pstmt.setString(1, pret.num_compte)
                pstmt.setString(2, pret.nom_client)
                pstmt.setString(3, pret.nom_banque)
                pstmt.setDouble(4, pret.montant)

                // --- CORRECTION SÉCURISÉE POUR LA DATE ---
                try {
                    // On essaie de convertir la date envoyée par Android
                    pstmt.setDate(5, java.sql.Date.valueOf(pret.date_pret))
                } catch (e: Exception) {
                    // Si le format est mauvais (ex: vide ou slash), on met la date du jour
                    pstmt.setDate(5, java.sql.Date(System.currentTimeMillis()))
                }
                // -----------------------------------------

                pstmt.setDouble(6, pret.taux_de_pret)

                pstmt.executeUpdate()
                conn.close()
                Response.status(Response.Status.CREATED).entity(pret).build()
            } else {
                Response.status(500).entity("Erreur de connexion DB").build()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Response.status(400).entity("Erreur : ${e.message}").build()
        }
    }

    // --- SUPPRIMER UN PRÊT ---
   @DELETE
    @Path("{num_compte}") // On utilise le numéro de compte comme identifiant unique
    fun supprimerPret(@PathParam("num_compte") numCompte: String): Response {
        val conn = DatabaseManager.getConnection()
        return try {
            val query = "DELETE FROM pret_bancaire WHERE num_compte = ?"
            val pstmt = conn?.prepareStatement(query)
            pstmt?.setString(1, numCompte)
            val rows = pstmt?.executeUpdate()

            conn?.close()
            if (rows != null && rows > 0) {
                Response.ok("Supprimé avec succès").build()
            } else {
                Response.status(Response.Status.NOT_FOUND).entity("Prêt non trouvé").build()
            }
        } catch (e: Exception) {
            Response.status(500).entity(e.message).build()
        }
    }

    // --- MODIFIER UN PRÊT ---
    @PUT
    @Path("/{num_compte}")
    @Consumes(MediaType.APPLICATION_JSON)
    fun modifierPret(@PathParam("num_compte") numCompte: String, pret: Pret): Response {
        val conn = DatabaseManager.getConnection()
        return try {
            val query = "UPDATE pret_bancaire SET nom_client=?, nom_banque=?, montant=?, date_pret=?, taux_de_pret=? WHERE num_compte=?"
            val pstmt = conn?.prepareStatement(query)

            pstmt?.setString(1, pret.nom_client)
            pstmt?.setString(2, pret.nom_banque)
            pstmt?.setDouble(3, pret.montant)
            pstmt?.setDate(4, java.sql.Date.valueOf(pret.date_pret))
            pstmt?.setDouble(5, pret.taux_de_pret)
            pstmt?.setString(6, numCompte)

            pstmt?.executeUpdate()
            conn?.close()
            Response.ok(pret).build()
        } catch (e: Exception) {
            Response.status(500).entity(e.message).build()
        }
    }
}