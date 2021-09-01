package edu.escuelaing.arep.networking.sockets;

import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket("127.0.0.1", 35000);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host!");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + "" + "the connection to: localhost.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        String userInput, serverResponse;

        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            serverResponse = in.readLine();
            System.out.println("echo: " + serverResponse);
            if (serverResponse.equals("Respuesta: Bye."))
                break;
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}
