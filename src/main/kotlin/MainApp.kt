import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import java.net.URI

object MainApp {
    // Utilise "0.0.0.0" pour que ton téléphone puisse se connecter via le Wi-Fi
    @JvmStatic
    val BASE_URI = URI.create("http://0.0.0.0:8080/")

    fun startServer(): HttpServer {
        // On dit à Jersey de chercher tes ressources (PretResource) dans ce package
        // On enregistre la classe directement au lieu de scanner un dossier
        val rc = ResourceConfig(PretResource::class.java)
        // Note: Change "com.eni.resource" par le package où se trouve ton PretResource.kt

        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc)
    }

    @JvmStatic
    fun main(args: Array<String>) {
        // 1. On récupère le PORT dynamique du serveur
        val port = System.getenv("PORT") ?: "8080"

        // 2. On écoute sur 0.0.0.0 (obligatoire pour le Cloud)
        val baseUri = URI.create("http://0.0.0.0:$port/")

        val rc = ResourceConfig(PretResource::class.java)
        val server = GrizzlyHttpServerFactory.createHttpServer(baseUri, rc)

        println("🚀 Serveur démarré sur le port $port")

        // 3. IMPORTANT : Ne pas utiliser System.in.read() en production !
        // Le serveur doit rester allumé indéfiniment.
        Thread.currentThread().join()
    }
}