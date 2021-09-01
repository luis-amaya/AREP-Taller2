package edu.escuelaing.arep.networking;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class URLExamples {
    public static void main(String[] args) {
        try {
            Scanner input = new Scanner(System.in);
            String site = input.nextLine();
            input.close();
            URL firstSite = new URL(site);
            System.out.println("La URL es: " + firstSite.toString());
            System.out.println("El host es: " + firstSite.getHost());
            System.out.println("El protocolo es: " + firstSite.getProtocol());
            System.out.println("Autority: " + firstSite.getAuthority());
            System.out.println("EL puerto es: " + firstSite.getPort());
            System.out.println("El path es: " + firstSite.getPath());
            System.out.println("Query: " + firstSite.getQuery());
            System.out.println("File: " + firstSite.getFile());
            System.out.println("Ref: " + firstSite.getRef());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
