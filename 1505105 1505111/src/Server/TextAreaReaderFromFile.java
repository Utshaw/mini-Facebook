package Server;

import Util.NetworkConnection;
import javafx.scene.control.TextArea;

import java.io.*;

/**
 * Created by Utshaw on 11/30/2016.
 */
public class TextAreaReaderFromFile implements Runnable,Serializable {

    Thread thread;
    String senderName;
    String receiverName;
    NetworkConnection networkConnection;
    private volatile  boolean keepRunning;

    public TextAreaReaderFromFile(String senderName, String receiverName, NetworkConnection networkConnection) {
        thread = new Thread(this);
        this.receiverName = receiverName;
        this.senderName = senderName;
        keepRunning=true;
        this.networkConnection = networkConnection;
        thread.start();
    }

    public void kill()
    {
        keepRunning=false;
    }
    @Override
    public void run() {

        File file = new File(System.getProperty("user.dir") + "\\facebook\\accounts\\" + receiverName + "\\messages\\" + senderName + ".txt");
        if (!file.exists())
            networkConnection.write("");
        else {
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String text = "";
            String message = "";

            try {
                while ((text = bufferedReader.readLine()) != null) {
                    message += text;
                    message += "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            networkConnection.write(message);
            /*

            while (keepRunning) {
                String line ;

                try {
                    if ((line = bufferedReader.readLine()) != null) {
                        networkConnection.write((Object)line);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
            */


        }


    }
}
