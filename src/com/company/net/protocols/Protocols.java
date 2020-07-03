package com.company.net.protocols;

import com.company.net.Message;

public interface Protocols {
    Message sending();	//Ce qu'il se passe lorsque l'on envoie un message
    void receiving(Message m);	//Ce qu'il se passe lorsque l'on reçoit un message
}
