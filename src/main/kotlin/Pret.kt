// Dans IntelliJ

// DANS INTELLIJ (BACKEND)
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Pret {
    var num_compte: String = ""
    var nom_client: String = ""
    var nom_banque: String = ""
    var montant: Double = 0.0
    var date_pret: String = ""
    var taux_de_pret: Double = 0.0

    // Constructeur vide obligatoire pour Jersey/Jackson
    constructor()
}