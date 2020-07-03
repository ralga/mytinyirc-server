package com.company.net;

import com.company.net.protocols.*;

//Chaque message envoyé et reçu a son premier octet qui correspond à l'une des commandes ci-dessous
public enum Flag {

    //SEND_FILE(5), ASK_ROOM(6), SEND_ROOM(7);
    //SEND_TEXT(0x00), SEND_FILE(5), SEND_INFO(6), ASK_INFO(7);

    //Protocole de récupération des informations du serveur
    //INFO(0x00, new InfoProtocol()),

    //Protocole d'authentification
    AUTH(0x10, new Auth()),

    //Protocole d'inscription
    //REGI(0x20, new RegiProtocol()),

    //Protocole d'échange client-client
    CLNT_TXT(0x30, new ClientTxt()); //Envoie/Réception de texte destiné à un client
    //CLNT_FILE(0x31), //SERVER/CLIENT : Envoie de fichier à un client

    //Protocole d'échange client-salon
    //ROOM_TXT(0x40, new RoomTxtProtocol()); //Envoie/Réception de texte destiné à un salon
    //ROOM_FILE(0x41);	//SERVER/CLIENT : Envoie de fichier à un salon*/

    private final byte b;
    private final Protocols p;

    private Flag(int b, Protocols p) {
        this.b = (byte) b;
        this.p = p;
    }

    public byte getByte() {
        return b;
    }

    public Protocols getProtocol() {
        return p;
    }

    public static Flag getCommand(byte b) throws Exception {
        for (Flag c : Flag.values()) {
            if (c.getByte() == b) {
                return c;
            }
        }
        throw new Exception("Aucune commande ne correspond à cette octet");
    }
}