package com.company.net.protocols;

import com.company.net.Message;

public class ClientTxt implements Protocols {
    /* This class should be used to define how standard TXT data is sent */
    @Override
    public Message sending() {
        return null;
    }

    @Override
    public void receiving(Message m) {

    }

}
