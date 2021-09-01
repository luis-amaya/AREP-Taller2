package edu.escuelaing.arep.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore.Entry;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import java.util.Scanner;

public class WebPageReader {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        String site = input.nextLine();
        input.close();
        // Crea el objeto que representa una URL
        URL siteURL = new URL(site);
        // Crea el objeto que URLConnection
        URLConnection urlConnection = siteURL.openConnection();
        // Obtiene los campos del encabezado y los almacena en una estructura map
        Map<String, List<String>> headers = urlConnection.getHeaderFields();
        // Obtiene una vista del mapa como conjunto de pares <K,V> para poder navegarlo
        Set<Map.Entry<String, List<String>>> entrySet = headers.entrySet();
        // REcorre la lista de campos e imprime los valores
        for (Map.Entry<String, List<String>> entry : entrySet) {
            String headerName = entry.getKey();
            // Si el nombre es nulo, significa que es la linea de estado
            if (headerName != null) {
                System.out.print(headerName + ":");
            }
            List<String> headerValues = entry.getValue();
            for (String value : headerValues) {
                System.out.print(value);
            }
            System.out.println("");
        }
        System.out.println("-------message-body-------");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
            String inputLine = null;
            String outputLine = "";
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                outputLine = outputLine + "\n" + inputLine;
            }
            fileWriter(outputLine);

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void fileWriter(String fileLines) {
        try {
            String ruta = "C:/Users/Luis/Documents/Octavo Semestre/AREP/Semana 2/Taller Network/Networking/src/main/java/edu/escuelaing/arep/networking/resultado.html";
            String contenido = fileLines;

            File file = new File(ruta);
            // Si el archivo existe no es creado
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
