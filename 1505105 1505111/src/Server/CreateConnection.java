/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import ServerFileOperation.RecieveFileServerPart;
import Util.Information;
import Util.NetworkConnection;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author user
 */
public class CreateConnection implements Runnable {

    HashMap<String, Information> clientList;
    HashMap<String, File> accounts;
    File[] files;
    File file;
    NetworkConnection nc;
    Thread thread;
    String clientName = null;

    /*
    HashMap<String,File> accounts = new HashMap<String,File>();

        File[] files = new File(System.getProperty("user.dir")+"\\facebook\\accounts").listFiles();


     */
    public CreateConnection(HashMap<String, Information> cList, NetworkConnection nConnection) {
        clientList = cList;
        accounts = new HashMap<String, File>();
        nc = nConnection;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        Object userObj = nc.read();
        String getText = (String) userObj;
        String[] arrayString = getText.split(" ");
        int counter = arrayString.length;
        switch (counter) {
            case 1:
                break;
            case 2:

                break;
            case 3:
                if (arrayString[2].equals("signin")) {
                    try {
                        signIn(arrayString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (arrayString[2].equals("signup")) {
                    clientName = arrayString[0];
                    try {
                        signUp(arrayString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:

        }

    }

    private void signIn(String[] txt) throws IOException {

        files = new File(System.getProperty("user.dir") + "\\facebook\\accounts").listFiles();
        for (File file : files) {
            accounts.put(file.getName(), file);
        }

        if (clientList.containsKey(txt[0])) {
            nc.write("alrdy");
        } else if (!accounts.containsKey(txt[0])) {
            nc.write("no");
        } else {
            boolean value = false;
            for (Map.Entry<String, File> entry : accounts.entrySet()) {
                if (entry.getKey().equals(txt[0])) {

                    File file = entry.getValue();
                    File file1 = new File(file.toString() + "\\info" + "\\info.txt");
                    BufferedReader bufferedReader = null;
                    try {
                        bufferedReader = new BufferedReader(new FileReader(file1));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    String infoText = bufferedReader.readLine();
                    String texts[] = infoText.split(" ");
                    String password = texts[1];
                    if (txt[1].equals(password)) {
                        value = true;
                    }
                    break;
                }
            }
            if (!value) {
                nc.write("passError");
            } else {
                nc.write("true");
                System.out.println("User : " + txt[0] + " connected");
                clientList.put(txt[0], new Information(txt[0], nc));
                new Thread(new ReaderWriterServer(txt[0], nc, accounts, clientList, files)).start();

            }

        }
    }

    private void signUp(String[] arrayString) throws IOException {
        File file = new File(System.getProperty("user.dir") + "\\facebook" + "\\accounts" + "\\" + arrayString[0]);
        if (file.exists()) {
            nc.write("false");
        } else {
            nc.write("true");

            File file1 = new File(System.getProperty("user.dir")
                    + "\\facebook" + "\\accounts" + "\\"
                    + arrayString[0] + "\\" + "status");
            file1.mkdirs();
            File file2 = new File(System.getProperty("user.dir")
                    + "\\facebook" + "\\accounts" + "\\"
                    + arrayString[0] + "\\" + "messages");
            file2.mkdir();
            File file3 = new File(System.getProperty("user.dir")
                    + "\\facebook" + "\\accounts" + "\\"
                    + arrayString[0] + "\\" + "info");
            file3.mkdir();
            File file4 = new File(System.getProperty("user.dir") + "\\facebook" + "\\accounts" + "\\"
                    + arrayString[0] + "\\" + "friends"
            );
            file4.mkdir();
            File infoFile = new File(System.getProperty("user.dir")
                    + "\\facebook" + "\\accounts" + "\\"
                    + arrayString[0] + "\\" + "info" + "\\" + "info.txt");
            if (!infoFile.exists()) {
                infoFile.createNewFile();
            }
            File propic = new File(System.getProperty("user.dir") + "\\facebook" + "\\accounts" + "\\" + arrayString[0] + "\\" + "info" + "\\" + "propic");
            propic.mkdir();
            File coverphoto = new File(System.getProperty("user.dir") + "\\facebook" + "\\accounts" + "\\" + arrayString[0] + "\\" + "info" + "\\" + "coverphoto");
            coverphoto.mkdir();
            File friendRequFile = new File(System.getProperty("user.dir")
                    + "\\facebook" + "\\accounts" + "\\"
                    + arrayString[0] + "\\" + "friends" + "\\" + "request.txt");
            if (!friendRequFile.exists()) {
                friendRequFile.createNewFile();
            }
            File myFriendsFile = new File(System.getProperty("user.dir")
                    + "\\facebook" + "\\accounts" + "\\"
                    + arrayString[0] + "\\" + "friends" + "\\" + "accepted.txt");
            if (!myFriendsFile.exists()) {
                myFriendsFile.createNewFile();
                File myLikesDir = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"+arrayString[0]+"\\likes");
                myLikesDir.mkdir();
                FileWriter fileWriter;
      //////////////////I THINK THIS BLO0CK WILL NOT BE IN IF CLAUSE
                fileWriter = new FileWriter(infoFile, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter printWriter = new PrintWriter(bufferedWriter);
                printWriter.print(arrayString[0] + " " + arrayString[1] + " ");
                printWriter.close();
                files = new File(System.getProperty("user.dir") + "\\facebook\\accounts").listFiles();
                for (File clientFile : files) {
                    accounts.put(clientFile.getName(), clientFile);
                }
     ////////////////////////////////////////////////////////////////////
            }
            //sending propic
            if (((String) nc.read()).equals("profilepic")) {
                nc.write("sendprofilepic");
                new RecieveFileServerPart(nc, arrayString[0], "profilepicture");
            }
            //sending profile Data
            if(((String) nc.read()).equals("wantTosendProfileData")){
                nc.write("sendProfileData");
                sendProfileData();
            }
            
    // creating reader writer server
        signinNewAccount();

    
    
    
            // reding for sign in with new account
        /*    String event = (String) nc.read();
            System.out.println("event in createconnection :" + event);
            String[] arrayString_for_sigin_with_new_account = event.split(" ");

            if (arrayString_for_sigin_with_new_account[2].equals("SignInWithNewAccount")) {
                try {
                    signIn(arrayString_for_sigin_with_new_account);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            */
            
        }

    }
    
    
    private void signinNewAccount(){
        files = new File(System.getProperty("user.dir") + "\\facebook\\accounts").listFiles();
        for (File anotherfile : files) {
            accounts.put(anotherfile.getName(), file);
        }       
        clientList.put(clientName , new Information(clientName, nc));
    // creating reader writer server
        new Thread(new ReaderWriterServer(clientName,  nc, accounts,clientList, files)).start();
    }
    
    
    
    private void sendProfileData(){
        System.out.println("I am in send sendProfileData");
        String str = (String)nc.read();
        String[] data = str.split("@#");

        File client_data = new File(System.getProperty("user.dir") + "\\facebook\\accounts\\" + clientName + "\\profile data");
        client_data.mkdir();
        System.out.println("I am after mkdir");
        File client_data_text = new File((System.getProperty("user.dir") + "\\facebook\\accounts\\" + clientName + "\\profile data\\"+ "data.txt"));
        if(!client_data_text.exists()){
            try {
                client_data_text.createNewFile();
                BufferedWriter BF_writer = new BufferedWriter(new FileWriter(client_data_text));
                PrintWriter PW_writer = new PrintWriter(BF_writer);
                System.out.println(data.length);
                for(int i=0; i<data.length; i++){
                    PW_writer.println(data[i]);
                }
                PW_writer.close();
                System.out.println("print writer closed");
                System.out.println("file writin complete");
             } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
