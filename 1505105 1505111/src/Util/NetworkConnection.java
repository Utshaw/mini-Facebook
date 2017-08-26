/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkConnection {
    public Socket socket;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    
    public NetworkConnection(Socket sock) throws IOException{
        socket=sock;
        oos=new ObjectOutputStream(socket.getOutputStream());
        ois=new ObjectInputStream(socket.getInputStream());
    }
    
    public NetworkConnection(String ip,int port) throws IOException{
        socket=new Socket(ip, port);
        oos=new ObjectOutputStream(socket.getOutputStream());
        ois=new ObjectInputStream(socket.getInputStream());
    }

    public NetworkConnection(String ip,int port,Label label){
        try {
            socket=new Socket(ip, port);
            oos=new ObjectOutputStream(socket.getOutputStream());
            ois=new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            label.setTextFill(Color.RED);
            label.setText("SERVER IS DOWN");
            Image image = new Image("/Client/error3.png",140,100,false,true,true);
            Notifications notificationBuilder = Notifications.create()
                    .title("ERROR!!")
                  .text("Couldn't reach "+ip)
                    .graphic(new ImageView(image))
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("CLicked");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
        }

    }

    public void stopConnection()
    {
        try {
            socket.close();
            System.out.println("Connection Closed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void write(Object obj){
        try {
            oos.writeObject(obj);
        } catch (IOException ex) {
            System.out.println(ex);
            ex.printStackTrace();

            //throw ex;
        }
    }
    
    public Object read(){
        Object obj;
        try {
            obj = ois.readObject();
        } catch (Exception ex) {
            System.out.println("Failed to read");
            ex.printStackTrace();
            return null;
        }
        return obj;
    }
}