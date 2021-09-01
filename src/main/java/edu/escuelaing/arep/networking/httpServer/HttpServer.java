package edu.escuelaing.arep.networking.httpServer;

import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;

import edu.escuelaing.arep.networking.springplus.Service;

import java.awt.Component;
import java.io.*;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HttpServer {
    private static final HttpServer _instance = new HttpServer();
    private static final Integer PORT = 35000;

    private HashMap<String, Method> services = new HashMap<>();

    public static HttpServer getInstance() {
        return _instance;
    }

    private HttpServer() {
    }

    public void startServer(String[] args) throws IOException, URISyntaxException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35001);
        } catch (Exception e) {
            System.err.println("Could not listen on port: 35000. ");
            System.exit(1);
        }

        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (Exception e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            manageConnection(clientSocket);
        }
        serverSocket.close();
    }

    public void manageConnection(Socket clientSocket) throws IOException, URISyntaxException {

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine, outputLine;
        ArrayList<String> request = new ArrayList<String>();
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            request.add(inputLine);
            if (!in.ready()) {
                break;
            }
        }

        String uriStr = request.get(0).split(" ")[1];
        URI resourceURI = new URI(uriStr);
        System.out.println("URI Path: " + resourceURI.getPath());
        System.out.println("URI query: " + resourceURI.getQuery());
        if (resourceURI.toString().startsWith("/appuser")) {
            outputLine = getComponentResource(resourceURI);
            out.println(outputLine);
        } else {
            outputLine = getHTMLResource(resourceURI);
            out.println(outputLine);
        }

        out.println(outputLine);

        out.close();
        in.close();
        clientSocket.close();
    }

    private String getComponentResource(URI resourceURI) {
        String response = "";
        try {
            String classPath = resourceURI.toString().replaceAll("/appuser/", "");
            Class component = Class.forName(classPath);
            for (Method m : component.getDeclaredMethods()) {
                if (m.isAnnotationPresent(Service.class)) {
                    response = m.invoke(null).toString();
                }
            }
        } catch (ClassNotFoundException e) {
            Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
            response = default404HTMLResponse();
        } catch (IllegalAccessException e) {
            Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    private String getHTMLResource(URI resourceURI) {
        String response = default404HTMLResponse();
        try {
            String serviceURI = resourceURI.getPath().toString().replaceAll("/appuser", "");
            Method m = services.get(serviceURI);
            response = m.invoke(null).toString();
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

        }
        return response;
    }

    private String default404HTMLResponse() {
        String outputLine = "HTPP/1.1 404 Not found\r\n" + "Content-Type: text/html\r\n" + "\r\n" + "<!DOCTYPE html>"
                + "<html>" + " <head>" + "     <title>404 Not Found </title>" + "     <meta charset=\"UTF-8\""
                + "     <meta name=\"viewport\"" + " </head>" + "<body>" + "     <div><h1>Error 404</h1></div>"
                + " </body>" + "</html>";
        return outputLine;
    }

    private String defaultHttpMessage() {
        String outputLine = "HTTP/1.1 200 ok\r\n" + "Content-Type: text/html\r\n" + "\r\n" + "<!DOCTYPE html>"
                + " <html>" + "     <head>" + "         <title> TODO supply a title </title>"
                + "         <meta charset=\"UTF-8\""
                + "         <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"" + "     </head>"
                + "     <body>" + "         <div><h1>My first page.</h1></div>"
                + "         <img src=\"https://labyes.com/feline/wp-content/uploads/2020/08/28Jul_LabyesNotaWeb1_2-1920x1283.jpg.webp"
                + "</html>";
        return outputLine;
    }

    public String getResource(URI resourceURI) {
        System.out.println("Received URI path: " + resourceURI.getPath());
        System.out.println("Received URI query: " + resourceURI.getQuery());
        return computeDefaultResponse();
    }

    public String computeDefaultResponse() {
        String outputLine = "HTTP/1.1 200 OK\r\n" + "Content-Type: text/html\r\n" + "\r\n" + "<!DOCTYPE html>\n"
                + "<html>\n" + "<head>\n" + "<meta charset=\"UTF-8\">\n" + "<title> Title of the document</title>\n"
                + "</head>\n" + "<body>\n" + "My Web Site\n" + "</body>\n" + "</html>\n";
        return outputLine;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        try {
            HttpServer.getInstance().startServer(args);
        } catch (IOException e) {
            Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
