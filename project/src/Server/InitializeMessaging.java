package Server;

import Util.Data;
import Util.NetworkConnection;

import java.io.*;
import java.util.HashMap;

/**
 * Created by Utshaw on 12/7/2016.
 */
public class InitializeMessaging implements  Runnable{
    NetworkConnection nc;
    HashMap<String, File> accounts;
    File[] files;

    public InitializeMessaging(NetworkConnection nc, HashMap<String, File> accounts, File[] files) {
        this.nc = nc;
        this.accounts = accounts;
        this.files = files;
    }

    @Override
    public void run() {
        Object objMessage = nc.read();
        Data dataObj = (Data) objMessage;
        String senderName = dataObj.senderName;
        String toUser = dataObj.receiverName;
        String sendMsg = dataObj.message;
        files = new File(System.getProperty("user.dir") + "\\facebook\\accounts").listFiles();
        for (File clientFile : files) {
            accounts.put(clientFile.getName(), clientFile);
        }

        //***
        File file = new File(accounts.get(toUser) + "\\messages\\" + senderName + ".txt");
        File file2 = new File(System.getProperty("user.dir") + "\\facebook\\accounts\\" + senderName + "\\messages\\" + toUser + ".txt");
        if (!file2.exists())
            try {
                file2.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        System.out.println(senderName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter fileWriter;
        FileWriter fileWriter2;
        try {
            fileWriter = new FileWriter(file, true);
            fileWriter2 = new FileWriter(file2, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            PrintWriter printWriter2 = new PrintWriter(bufferedWriter2);
            System.out.println(sendMsg);
            printWriter.println(senderName + ": " + sendMsg);
            printWriter2.println(senderName + ": " + sendMsg);
            printWriter.close();
            printWriter2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
