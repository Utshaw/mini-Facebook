/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Util.Information;
import Util.NetworkConnection;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


/**
 *
 * @author user
 */
public class ServerMain {
    public static void main(String[] args) throws IOException {
        
        ServerSocket serverSocket=new ServerSocket(12345);        
        
        HashMap<String,Information> clientList=new HashMap<String,Information>();
        HashMap<String,File> accounts = new HashMap<String,File>();

        File[] files = new File(System.getProperty("user.dir")+"\\facebook\\accounts").listFiles();
        for(File file:files)
        {
            accounts.put(file.getName(),file);
        }
//        for(File file:files)
//        {
//            if(file.isDirectory())
//            {
//
//            }
//            else
//            {
//                accounts.put(file.getName().replace(".txt",""),file);
//            }
//        }



        
        
        while(true){
            Socket socket=serverSocket.accept();
            NetworkConnection nc=new NetworkConnection(socket);
            System.out.println("connection established in Server" );
            new CreateConnection(clientList,nc);
        }

        
    }

}
//    public  void showFiles(File[] files) {
//        for (File file : files) {
//            if (file.isDirectory()) {
//                System.out.println("Directory: " + file.getName());
//                showFiles(file.listFiles()); // Calls same method again.
//            } else {
//                System.out.println("File: " + file.getName());
//
//            }
//        }
//    }
