/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import ClientFileOperation.RecieveFileClientPart;
import ServerFileOperation.RecieveFileServerPart;
import ServerFileOperation.SendFileServerpart;
import Util.Data;
import Util.Information;
import Util.NetworkConnection;
import Util.Status;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author user
 */
public class ReaderWriterServer implements Runnable {
    String username;
    File messageFile;
    NetworkConnection nc;
    HashMap<String, File> accounts;
    File[] files;
    HashMap<String, Information> clientList;
    TextAreaReaderFromFile textAreaReaderFromFile;

    public ReaderWriterServer(String user, NetworkConnection netConnection, HashMap<String, File> accounts, HashMap<String, Information> cList, File[] files) {
        username = user;
        textAreaReaderFromFile = null;
        nc = netConnection;
        accounts = new HashMap<String, File>();
        this.accounts = accounts;
        clientList = cList;
        this.files = files;
    }

    @Override
    public void run() {
        while (true) {
            String event = (String) nc.read();
            
            switch (event) {
                case "friend_profilepicture":
                    nc.write("sendingprofilepic");
                    
                    friendProfilePicture();
                    
                    break;
                case "home":
                    try {
                        home();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "unlike":
                    try {
                        unlike();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "like":
                    like();
                    break;
                case "profile":
                    System.out.println("command get :" + event);
                    nc.write("sendingprofile");
                    sendprofile();
                    break;
               
                case "unfriend":
                    try {
                        unfriend();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "acceptRequest":
                    try {
                        acceptRequest();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "cancelfriendRequ":
                    try {
                        cancelfriendRequ();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "requests":
                    try {
                        requests();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "sendfriendRequ":
                    try {
                        sendfriendRequ();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "peoples":
                    System.out.println("This is Utshaw");
                    peoples();
                    break;
                case "clientsProfile":
                    try {
                        clientsProfile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "settings":
                    System.out.println("This is00");
                    break;
                case "changePassword":
                    changePassword();
                    break;

                case "status":
                    try {
                        initializeStatus();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "message":
                    initializeMessaging();
                    break;
                case "refreshAccountsAgain":
                    sendAccountsAgain();
                    break;
                case "signout":
                    String signOutName = (String) nc.read();
                    signOutEntry(signOutName);
                    break;
                case "textArea":
                    String receiverName = (String) nc.read();
                    String senderName = (String) nc.read();
                    System.out.println("SENDER " + senderName);
                    System.out.println("RECEIVER: " + receiverName);
                    textAreaReaderFromFile = new TextAreaReaderFromFile(senderName, receiverName, nc);
                    break;
                case "stopReading":
                    if (textAreaReaderFromFile != null)
                        textAreaReaderFromFile.kill();
                    break;
                case "want_to_change_coverphoto":
                    nc.write("send_cover_photo");
                    changeCoverphoto();
                    break;
                case "is_changed_coverphoto?":
                    File cover_photo_check  = new File(System.getProperty("user.dir") + "\\facebook" + "\\accounts" + "\\" + username + "\\" + "info" + "\\" + "coverphoto" + "\\" + username + ".jpg");
                    if(cover_photo_check.exists()){
                        nc.write("yes_coverphoto_changed");
                        new SendFileServerpart(nc, username, "changed_coverphoto");
                    }
                    else{
                        nc.write("no_coverphoto_not_changed");
                    } 
                    break;
//                case "textAreaRepeat":
//                    nc.write(clientList.clone());
//                    String receiverName1  = (String)nc.read();
//                    String senderName1=(String) nc.read();
//                    System.out.println("SENDER "+senderName1);
//                    System.out.println("RECEIVER: "+receiverName1);
//                    new TextAreaReaderFromFile(senderName1,receiverName1,nc);
//                    break;
            }

//            if(!accounts.containsKey(toUser))
//                nc.write("noReceiverFound");


//            Information info=clientList.get(toUser);
//
//            String messageToSend=username+" -> "+sendMsg;
//            Data data=new Data();
//            data.message=messageToSend;
//
//            info.netConnection.write(data);


        }

    }
    private void friendProfilePicture(){
        String friend_name = (String)nc.read();
        new SendFileServerpart(nc, username, friend_name , "friend_profile_pic");
    }
    private void home() throws FileNotFoundException {

        String name =(String ) nc.read();
        File[] file = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"+name+"\\status").listFiles();
        nc.write(file.length);
        Status[] statuses = new Status[file.length];
        for(int i=0;i<statuses.length;i++)
        {
            statuses[i] = new Status();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File((System.getProperty("user.dir"))+"\\facebook\\accounts\\"
                    +name+"\\status\\"+Integer.toString(i+1)+".txt")));
            try {
                statuses[i].status=bufferedReader.readLine();
                statuses[i].likes=Integer.parseInt(bufferedReader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        nc.write(statuses);
    }
    
    private void changeCoverphoto(){
        new RecieveFileServerPart(nc, username, "cover_photo_change");
    }
    
    private void unlike() throws IOException {
        String name = (String) nc.read();
        String fromName = (String) nc.read();
        Integer statusNo = (Integer) nc.read();
        File file = new File(System.getProperty("user.dir") + "\\facebook\\accounts\\" + name + "\\status\\" + Integer.toString(statusNo + 1) + ".txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String statusText = "", oldLikes = "";
            statusText = bufferedReader.readLine();
            oldLikes = bufferedReader.readLine();
            bufferedReader.close();
            String newLike = Integer.toString(Integer.parseInt(oldLikes) - 1);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(statusText);
            printWriter.println(newLike);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file_fromLikes = new File(System.getProperty("user.dir")
                +"\\facebook\\accounts\\"+fromName+"\\likes\\"+name+".txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file_fromLikes));
        String line = "", oldtext = "";
        while((line = bufferedReader.readLine()) != null)
        {
            if(line.equals(Integer.toString(statusNo)))
                continue;;
            oldtext += line + "\r\n";
        }
        bufferedReader.close();



        FileWriter writer = new FileWriter(System.getProperty("user.dir")
                +"\\facebook\\accounts\\"+fromName+"\\likes\\"+name+".txt");
        writer.write(oldtext);


        writer.close();
//        String line;
//        String newLines = "";
//        while((line=bufferedReader.readLine())!=null)
//        {
//            if(line.equals(Integer.toString(statusNo)))
//                continue;
//            newLines+=line;
//            newLines+='\n';
//        }
//        bufferedReader.close();
//        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file_fromLikes)));
//        printWriter.println(newLines);
//        printWriter.close();
        /*
        File inputFile = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"+fromName
                + "\\likes\\"+name + ".txt");
        File tempFile = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"+fromName
                + "\\likes\\"+"temp.txt");
        System.out.println(inputFile);
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        PrintWriter printWriter = new PrintWriter(writer);

        String lineToRemove = Integer.toString(statusNo);
        String currentLine;
        String putThisLine = "";
        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
            System.out.println(currentLine);
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        boolean successful =  forceRename(tempFile,inputFile);
        System.out.println(successful);
        */
    }
private void like() {
        String name = (String) nc.read();
        String fromName = (String)nc.read();
        Integer statusNo =(Integer) nc.read();
        File file = new File(System.getProperty("user.dir") + "\\facebook\\accounts\\" + name + "\\status\\" + Integer.toString(statusNo + 1) + ".txt");
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String statusText = "" , oldLikes = "";
            statusText = bufferedReader.readLine();
            oldLikes = bufferedReader.readLine();
            bufferedReader.close();
            String newLike = Integer.toString(Integer.parseInt(oldLikes)+1);
            FileWriter  fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(statusText);
            printWriter.println(newLike);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File myLikesAddFile  = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"
        +fromName + "\\likes\\" + name + ".txt");
        System.out.println(myLikesAddFile);
        if(!myLikesAddFile.exists())
        {
            try {
                myLikesAddFile.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileWriter  myLikesFileWritter = null;
        try {
            myLikesFileWritter = new FileWriter(myLikesAddFile,true);
            BufferedWriter filesBufferedWriter = new BufferedWriter(myLikesFileWritter);
            PrintWriter likesPrintWriter = new PrintWriter(filesBufferedWriter);
            likesPrintWriter .println(statusNo);
            likesPrintWriter .close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendprofile(){
       System.out.println("Server sendprofile is called");
       //sending profile picture
        new SendFileServerpart(nc, username, "profilepic");
   //sending cover photo
        // WHEN CLIENT WILL CHOSE A COVER PHOTO TRHEN THIS FUCTION NEDDED
        //  new SendFileServerpart(nc, username, "coverphoto");

    //sending client data
        File file = null;
        try {
            file = new File((System.getProperty("user.dir") + "\\facebook\\accounts\\" + username + "\\profile data\\" + "data" +".txt"));

       }catch (Exception ex){
            System.out.println("Exception file opening in reeader wqrieter server" + ex);
       }
        String  data = "";
       try{
           BufferedReader BF_reader = new BufferedReader(new FileReader(file));
            while(true){
                String s = BF_reader.readLine();
                if(s==null)break;
                data+=s;
                data+="@#";
                System.out.println(s);
            }
           System.out.println(data);

       }catch (Exception ex){
           System.out.println("Exception when Bufferred reader reading" + ex);
       }

        nc.write(data);

    }

    private void unfriend() throws IOException {
        String fromName = (String)nc.read();
        String toName = (String)nc.read();

        File file = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"+fromName+"\\friends\\accepted.txt");
        File tempFile = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"+fromName+"\\friends\\accepted.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String lineToRemove = toName;
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        boolean successful = tempFile.renameTo(file);


        File file2 = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"+toName+"\\friends\\accepted.txt");
        File tempFile2 = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"+toName+"\\friends\\accepted.txt");
        BufferedReader reader2 = new BufferedReader(new FileReader(file2));
        BufferedWriter writer2 = new BufferedWriter(new FileWriter(tempFile2));
        String lineToRemove2 = fromName;
        String currentLine2;

        while((currentLine2 = reader2.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine2.trim();
            if(trimmedLine.equals(lineToRemove2)) continue;
            writer2.write(currentLine2 + System.getProperty("line.separator"));
        }
        writer2.close();
        reader2.close();
        boolean successful2 = tempFile2.renameTo(file2);

    }

    private void acceptRequest() throws IOException {
        String fromName = (String)nc.read();
        String acceptedName = (String)nc.read();
        File myFriendsFile = new File(System.getProperty("user.dir") +
                "\\facebook" + "\\accounts" + "\\"
                + fromName + "\\" + "friends" + "\\" + "accepted.txt");
        FileWriter  fileWriter = new FileWriter(myFriendsFile, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);
        printWriter.println(acceptedName);
        printWriter.close();

        File myFriendsFile2 = new File(System.getProperty("user.dir") +
                "\\facebook" + "\\accounts" + "\\"
                + acceptedName + "\\" + "friends" + "\\" + "accepted.txt");
        FileWriter  fileWriter2 = new FileWriter(myFriendsFile2, true);
        BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
        PrintWriter printWriter2 = new PrintWriter(bufferedWriter2);
        printWriter2.println(fromName);
        printWriter2.close();



        File file = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"+fromName+"\\friends\\request.txt");
        File tempFile = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"+fromName+"\\friends\\request.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        String lineToRemove = acceptedName;
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close();
        reader.close();
        boolean successful = tempFile.renameTo(file);

    }

    private void cancelfriendRequ() throws IOException {
        String toName = (String)nc.read();
        String fromName = (String)nc.read();
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"+toName+"\\friends\\request.txt");
        BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ReaderWriterServer.class.getName()).log(Level.SEVERE, null, ex);
                }
        String line=null;
        String[] requestString = null;
                try {
                    requestString = new String[countLines(file)];
                } catch (IOException ex) {
                    Logger.getLogger(ReaderWriterServer.class.getName()).log(Level.SEVERE, null, ex);
                }
        int counter=0;
                try {
                    while((line=bufferedReader.readLine())!=null)
                    {
                        if(line.equals(fromName))
                            continue;
                        requestString[counter++]=line;
                    }       } catch (IOException ex) {
                    Logger.getLogger(ReaderWriterServer.class.getName()).log(Level.SEVERE, null, ex);
                }
        FileWriter fileWriter = null;
                try {
                    fileWriter = new FileWriter(file, false);
                } catch (IOException ex) {
                    Logger.getLogger(ReaderWriterServer.class.getName()).log(Level.SEVERE, null, ex);
                }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);
        for(int i=0;i<counter;i++)
        {
            printWriter.println(requestString[i]);
        }
        printWriter.close();
            }
        }).start();

    }

    private void requests() throws IOException {
        String name = (String) nc.read();
        File file = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"+name+"\\friends\\request.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line = null;
        String[] requestString =  new String[countLines(file)];
        int counter=0;
        while((line=bufferedReader.readLine())!=null)
        {
            requestString[counter++]=line;
        }
        nc.write(requestString);

    }

    public  int countLines(File filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }


    private void sendfriendRequ() throws IOException {
        String toName =(String) nc.read();
        String fromName = (String)nc.read();
        File file = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"+toName+"\\friends\\request.txt");
        FileWriter  fileWriter = new FileWriter(file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);
        printWriter.println(fromName);
        printWriter.close();

    }

    private boolean changePassword() {
        boolean result = false;
        String name = (String) nc.read();
        String newPassword = (String) nc.read();
        File file = new File(System.getProperty("user.dir") + "\\facebook\\accounts\\" + name + "\\info\\info.txt");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String line = bufferedReader.readLine();
            StringTokenizer stringTokenizer = new StringTokenizer(line);
            String userName = stringTokenizer.nextToken();
            String password = stringTokenizer.nextToken();
            FileWriter fileWriter = new FileWriter(file, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            printWriter.println(userName + " " + newPassword + " ");
            printWriter.close();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result)
            nc.write("true");
        else
            nc.write("false");
        return result;


    }

    private void peoples() {
        String removeName = (String) nc.read();
        File[] files = new File(System.getProperty("user.dir") + "\\facebook\\accounts").listFiles();
        System.out.println(files.length);
        int counter = 0;
        String[] accountsList = new String[files.length];
        for (File file : files) {
            if (file.getName().equals(removeName))
                continue;
            accountsList[counter++] = file.getName();
            System.out.println(file.getName());
        }
        nc.write(accountsList);
    }

    private void clientsProfile() throws IOException {
        String clientName = (String) nc.read();
        System.out.println(clientName);
        String fromName = (String)nc.read();
        File requFile = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"+fromName+"\\friends\\request.txt");
        BufferedReader bufferedReader1 = new BufferedReader(new FileReader(requFile));
        String line = null;
        String requButotnValue = "notRequested";
        while((line=bufferedReader1.readLine())!=null)
        {
            if(line.equals(clientName))
            {
                requButotnValue = "requested";
                break;
            }
        }
        if(requButotnValue.equals("requested") )
        {
            nc.write(requButotnValue);
        }
        else if(requButotnValue.equals("notRequested") )
        {
            File file = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"+clientName+"\\friends\\request.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line2=null;
            while((line2=bufferedReader.readLine())!=null)
            {
                if(line2.equals(fromName))
                {
                    requButotnValue = "requestSent";
                    break;
                }
            }

            if(!requButotnValue.equals("requestSent"))
            {
                File accpetFile = new File(System.getProperty("user.dir")+"\\facebook\\accounts\\"+fromName+"\\friends\\accepted.txt");
                BufferedReader acceptBufferedReader = new BufferedReader(new FileReader(accpetFile));
                String line3=null;
                while((line3=acceptBufferedReader.readLine())!=null)
                {
                    if(line3.equals(clientName))
                    {
                        requButotnValue = "friends";
                        break;
                    }
                }
            }
            nc.write(requButotnValue);
        }
        if(requButotnValue.equals("friends"))
        {
            File[] clientFiles = new File(System.getProperty("user.dir") + "\\facebook\\accounts\\" + clientName + "\\status").listFiles();

            nc.write(clientFiles.length);
             File fromLikes = new File(System.getProperty("user.dir")
                    + "\\facebook" + "\\accounts" + "\\"
                    + fromName + "\\" + "likes\\" +clientName + ".txt" );
            boolean[] myLikeArray = new boolean[clientFiles.length];
            if(fromLikes.exists())
            {
                BufferedReader likeReader = new BufferedReader(new FileReader(fromLikes));
                String likeLine=null;
                while((likeLine=likeReader.readLine())!=null)
                {
                    System.out.println(likeLine);
                    int index = Integer.parseInt(likeLine);
                    myLikeArray[index]=true;
                }
            }
            nc.write(myLikeArray.clone());
            int counter = clientFiles.length;
            for (int i = 0; i < counter; i++) {
                File file = new File(System.getProperty("user.dir") + "\\facebook\\accounts\\" + clientName + "\\status\\" + Integer.toString(i + 1) + ".txt");
                System.out.println(file);
                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new FileReader(file));
                    String textStatus = (bufferedReader.readLine());
                    String like = bufferedReader.readLine();
                    Status status = new Status();
                    status.likes=Integer.parseInt(like);
                    status.status=textStatus;
                    nc.write(status);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }




    }

    private void changeName() {
        String previousName = (String) nc.read();
        System.out.println(previousName);
        String changedName = (String) nc.read();
        System.out.println(changedName);
        File infoDirectory = new File(System.getProperty("user.dir") +
                "\\facebook" + "\\accounts" + "\\"
                + previousName);
        System.out.println(infoDirectory);
        boolean result = infoDirectory.renameTo(new File(System.getProperty("user.dir") + "\\facebook\\accounts\\" + changedName));
        System.out.println(new File(System.getProperty("user.dir") + "\\facebook\\accounts\\" + changedName));
        System.out.println(result);
        if (result) {
            nc.write("true");
            File infoFile = new File(System.getProperty("user.dir") +
                    "\\facebook" + "\\accounts" + "\\"
                    + changedName + "\\" + "info" + "\\" + "info.txt");
            FileWriter fileWriter;
            try {
                fileWriter = new FileWriter(infoFile, false);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter printWriter = new PrintWriter(bufferedWriter);
                BufferedReader bufferedReader = new BufferedReader(new FileReader(infoFile));
                String line = bufferedReader.readLine();
                String newLine = line.replaceAll(previousName, changedName);
                printWriter.print(newLine);
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            nc.write("false");


    }


    //Masfiq editted
    public void initializeStatus() throws IOException {
        String name = (String) nc.read();
        File[] statusFiles = new File(System.getProperty("user.dir") + "\\facebook\\accounts\\" + name + "\\status").listFiles();
        Integer numberOfStatuses = statusFiles.length;
        File newStatusFile = new File(System.getProperty("user.dir") + "\\facebook\\accounts\\" + name + "\\status\\" + Integer.toString(numberOfStatuses + 1) + ".txt");
        if (!newStatusFile.exists()) {
            newStatusFile.createNewFile();
        }
        Status statusObj = (Status) nc.read();
        String status = statusObj.status;
        int likes = statusObj.likes;
        FileWriter fileWriter;
        fileWriter = new FileWriter(newStatusFile);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);
        printWriter.println(status);
        printWriter.println(likes);
        printWriter.close();
//        FileWriter fileWriter;
//        fileWriter = new FileWriter(file,true);
//        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//        PrintWriter printWriter = new PrintWriter(bufferedWriter);
//        System.out.println(message);
//        printWriter.println(message);
//        printWriter.close();

    }
    /*
//Masfiq editted
    public void iteratefile(File [] Dir_accounts) throws IOException{
        for(File F_iterating : Dir_accounts){
            if(F_iterating.isDirectory() ){
                System.out.println("Directory :" + F_iterating.getName());
                iteratefile(F_iterating.listFiles());
            }
            else if(F_iterating.getName().equals("my status.txt") ){
                System.out.println("text File :" + F_iterating.getName());
                BufferedWriter Buff_writer = new BufferedWriter(new FileWriter(F_iterating , true));
                PrintWriter Print_writer = new PrintWriter(Buff_writer);
                Print_writer.println(client_status + "\n");
                Print_writer.close();
            }
        }
    }

     */

    private void signOutEntry(String signOutName) {
        clientList.remove(signOutName);
    }

    private void sendAccountsAgain() {
        String removeName = (String) nc.read();
        files = new File(System.getProperty("user.dir") + "\\facebook\\accounts").listFiles();
        String[] accountsList = new String[files.length];
        int counter = 0;
        for (File clientFile : files) {
            accounts.put(clientFile.getName(), clientFile);
            if (clientFile.getName().equals(removeName))
                continue;
            accountsList[counter++] = clientFile.getName();
        }
        nc.write(accountsList);
    }


    private void initializeMessaging() {
        Object objMessage = nc.read();
        Data dataObj = (Data) objMessage;
        String senderName = dataObj.senderName;
        String toUser = dataObj.receiverName;
        String sendMsg = dataObj.message;
        files = new File(System.getProperty("user.dir") + "\\facebook\\accounts").listFiles();
        for (File clientFile : files) {
            accounts.put(clientFile.getName(), clientFile);
        }
        try {
            writeMessageOnText(senderName, accounts.get(toUser), toUser, sendMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMessageOnText(String senderName, File file1, String receiverName, String message) throws IOException {

        File file = new File(file1 + "\\messages\\" + senderName + ".txt");
        File file2 = new File(System.getProperty("user.dir") + "\\facebook\\accounts\\" + senderName + "\\messages\\" + receiverName + ".txt");
        if (!file2.exists())
            file2.createNewFile();
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
        fileWriter = new FileWriter(file, true);
        fileWriter2 = new FileWriter(file2, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);
        PrintWriter printWriter2 = new PrintWriter(bufferedWriter2);
        System.out.println(message);
        printWriter.println(senderName + ": " + message);
        printWriter2.println(senderName + ": " + message);
        printWriter.close();
        printWriter2.close();
    }

}
