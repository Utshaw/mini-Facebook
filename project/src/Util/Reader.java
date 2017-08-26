/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import javafx.scene.control.TextArea;

/**
 * @author user
 */
public class Reader implements Runnable {
    TextArea incomingmessage;
    public NetworkConnection netConnection;
    String name;
    String receiverName;

    public Reader(String name, String receiverName, TextArea incomingmessage, NetworkConnection netConnection) {
        this.incomingmessage = incomingmessage;
        this.netConnection = netConnection;
        this.name=name;
        this.receiverName=receiverName;
    }


    @Override
    public void run() {
        while (true) {
//            netConnection.write("textArea");
//            netConnection.write(name);
//            netConnection.write(receiverName);
//            Object obj = netConnection.read();
//            Data dataObj = (Data) obj;
            String line = (String)netConnection.read();
//            incomingmessage.appendText(line+"\n");
            System.out.println(line);

        }
    }

}

