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

    public Connection(String ip, int port) throws IOException, UnknownHostException {
        this.ip = ip;
        this.port = port;
        sock = new Socket();
        sock.connect(new InetSocketAddress(ip, port), TIMEOUT);	//Tentative de connexion au serveur
        out = sock.getOutputStream();
        in = sock.getInputStream();
    }

    @Override
    public void run() {
        super.run();
    }
}
