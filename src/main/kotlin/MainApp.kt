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
        val server = startServer()
        println("🚀 Serveur démarré sur ${BASE_URI}")
        println("Appuie sur Entrée pour l'arrêter...")
        System.`in`.read()
        server.shutdownNow()
    }
}