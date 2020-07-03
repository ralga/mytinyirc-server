package com.company;

import com.company.net.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final int PORT = 4157;	//Port d'écoute du serveur
    private static final int SIZE = 1024;	    //Send/Receive suffer size

    private static Main instance = null;
    private ServerSocket serSoc;	//Socket d'écoute

    private Main(){

    }

    private void startServer() {
        try {
            serSoc = new ServerSocket(PORT);	//Lancement de la socket d'écoute
        } catch (IOException ex) {
            System.err.println("Impossible de démarrer le serveur !");
            System.exit(1);
        }
        System.out.println("Serveur prêt ! En attente de clients...");
        //Boucle d'écoute : attente de nouveaux clients
        while (true) {
            try {
                Socket soc = serSoc.accept();	//Méthode bloquante : attend un client
                Connection.newConnection(soc);
            } catch (IOException ex) {
                System.err.println("Impossible de créer la socket client !");
            }
        }
    }

    public static void main(String[] args) {
        getInstance().startServer();
    }

    private static final Main getInstance(){
        if(instance == null)
            instance = new Main();

        return instance;
    }
}
