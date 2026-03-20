import org.glassfish.grizzly.http.server.HttpServer
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import java.net.URI
import org.glassfish.jersey.jackson.JacksonFeature

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

        val rc = ResourceConfig()
            .register(PretResource::class.java)
            .register(JacksonFeature::class.java) // <--- C'EST CETTE LIGNE QUI RÉPARE L'ERREUR 415
        val server = GrizzlyHttpServerFactory.createHttpServer(baseUri, rc)

        println("🚀 Serveur démarré sur le port $port avec support JSON")

        Thread.currentThread().join()
    }
}