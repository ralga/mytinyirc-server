package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection extends Thread{

    private static final int TIMEOUT = 2000;	//2s to Time out
    private static final int SIZE = 1024;	    //Send/Receive suffer size

    //Fields
    private final String ip;
    private final int port;
    private final Socket sock;
    private final InputStream in;
    private final OutputStream out;

    public Connection(Socket sock) throws IOException, UnknownHostException {
        this.ip = sock.getInetAddress().getHostAddress();
        this.port = sock.getPort();
        this.sock = sock;
        this.out = sock.getOutputStream();
        this.in = sock.getInputStream();
    }

    @Override
    public void run() {
        super.run();
        do {
            read();
        } while (!sock.isInputShutdown());	//Tant que le flux d'entrée est ouvert
        try {
            if (sock.isOutputShutdown()) {	//Si tous les flux sont fermés, on peut stoper la connexion
                sock.close();
                /*clients.remove(this);	//Suppression du client de la liste des connectés
                for (Client c : Client.clients) {
                    if (c.isAuthenticated) {
                        Message.send(c, Command.INFO);
                    }
                }*/
                System.out.println("### TCP # Déconnexion : " + sock.getInetAddress().getHostAddress() + " #");
            }
        } catch (IOException ex) {
            System.err.println("Impossible de fermer correctement la connexion !");
            System.exit(1);
        }
    }

    public void closeOutput() {
        try {
            sock.shutdownOutput(); //On ferme UNIQUEMENT le flux de sortie (Envoie d'un FIN)
            //quit = true;
        } catch (IOException ex) {
            System.err.println("Le flux de sortie est déjà fermé !");
        }
    }

    //Lit sur le flux d'entrée, ferme celui-ci si un end of file est reçu.
    public void read() {
        byte[] data = new byte[SIZE];
        try {
            int n = in.read(data);
            if (n != -1) {
                //Message.receive(this, data);	//Traitement des données reçus
                closeOutput();
            } else {	//End of file, un FIN vient d'être envoyé par le client
                sock.shutdownInput();	//Ferme le flux d'entrée, met fin au Thread
                /*if (!quit) {
                    closeOutput();
                }*/
            }
        } catch (IOException ex) {
            System.err.println("Impossible de lire les données reçus !");
            closeOutput();
        }
    }

    public static void newConnection(Socket soc) {
        Connection c;
        try {
            c = new Connection(soc); //Instanciation
            c.start();	//Démarrage d'un Thread par client
            //clients.add(c);	//Ajout à la liste des clients connectés
            System.out.println("### TCP # Connexion : " + soc.getInetAddress().getHostAddress() + " #");
        } catch (IOException ex) {
            System.err.println("Impossible d'instancier le nouveau client !");
            System.exit(1);
        }
    }
}
