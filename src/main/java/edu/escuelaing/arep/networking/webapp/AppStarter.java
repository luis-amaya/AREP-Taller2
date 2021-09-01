package edu.escuelaing.arep.networking.webapp;

import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.LoggerFinder;
import java.net.URISyntaxException;
import java.util.logging.Level;

import edu.escuelaing.arep.networking.httpServer.HttpServer;

public class AppStarter {

    public static void main(String[] args) throws IOException, URISyntaxException {
        try {
            HttpServer.getInstance().startServer(args);
        } catch (IOException ex) {
            Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
