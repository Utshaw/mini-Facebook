package Client;

import Server.TextAreaReaderFromFile;
import Util.Data;
import Util.Information;
import Util.NetworkConnection;
import Util.Reader;
import com.jfoenix.controls.JFXListView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Utshaw on 11/28/2016.
 */
public class MessageBox {

    NetworkConnection nc;
    Data messageData;
    Stage stage;
    String name;
    String receiverName;
    HashMap<String, File> accounts;
    String[] accountsList = null;
    HashMap<String, Information> clientList;
    BorderPane borderPane;
    Thread thread;
    Timer timer = null;
    TimerTask timerTask;
    private StringProperty textMessage = new SimpleStringProperty();

    public MessageBox(NetworkConnection nc, Stage stage, String name, BorderPane borderPane) {
        this.nc = nc;
        timer = new Timer();
        this.stage = stage;
        this.name = name;
        this.borderPane = borderPane;
    }

    public Timer clientsBox() {
//        accounts = (HashMap<String, File>) nc.read();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Image backImage = new Image("/Client/message4.png");
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        BackgroundSize bSize = new BackgroundSize(width, height, false, false, true, false);
        Background background2 = new Background(new BackgroundImage(backImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize));
        borderPane.setBackground(background2);

        accountsList = (String[]) nc.read();
        stage.setTitle(name + "'s" + "ChatBox");

        //TextArea for Received Messages
        TextArea receivedMessage = new TextArea();
        receivedMessage.setMaxHeight(400);
        receivedMessage.setMinHeight(400);
        receivedMessage.setFont(new Font("Verdana", 14));
        receivedMessage.setEditable(false);

//        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setContent(receivedMessage);
        //Creating list view from map
        JFXListView<String> listView = new JFXListView<>();
        listView.setMaxSize(200,550);
        listView.setMinSize(200,550);
//masfiq editted
//        listView.setExpanded(false);
//        listView.depthProperty().set(1);

//        ListView<String> listView = new ListView<>();
//        for (String client : accountsList) {
//            listView.getItems().add(client);
//        }
        listView.getItems().addAll(accountsList);

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if(timer!=null)
//                {
//                    timer.cancel();
//                    timer.purge();
//                }
                receiverName = newValue;//This is receiverName for outgoing message
                nc.write("textArea");
                nc.write(name);
                nc.write(receiverName);//In case of showing previous messages it is the selected client
                String message = (String) nc.read();
                textMessage.setValue(message);
//                receivedMessage.setText(message);
//                receivedMessage.appendText("");
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        nc.write("textArea");
                        nc.write(name);
                        nc.write(receiverName);//In case of showing previous messages it is the selected client
                        String message = (String) nc.read();
                        textMessage.setValue(message);
//                        receivedMessage.setText(message);
//                        receivedMessage.appendText("");
                    }
                };
                timer.scheduleAtFixedRate(timerTask, 500, 500);
//                timer.scheduleAtFixedRate(new TimerTask() {
//                    @Override
//                    public void run() {
//                        nc.write("textArea");
//                        nc.write(name);
//                        nc.write(receiverName);//In case of showing previous messages it is the selected client
//                        String message = (String)nc.read();
//                        receivedMessage.setText(message);
//                    }
//                }, 2000, 500);
            }
        });
        receivedMessage.textProperty().bind(textMessage);
        textMessage.addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                    Object newValue) {
                // from stackoverflow.com/a/30264399/1032167
                // for some reason setScrollTop will not scroll properly
                //consoleTextArea.setScrollTop(Double.MAX_VALUE);
                receivedMessage.selectPositionCaret(receivedMessage.getLength());
                receivedMessage.deselect();
            }
        });

        /*
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (timer != null) {
                    timer.cancel();
                    timer.purge();
                }
                receiverName = newValue;//This is receiverName for outgoing message
                nc.write("textArea");
                nc.write(name);
                nc.write(receiverName);//In case of showing previous messages it is the selected client
                String message = (String) nc.read();
                receivedMessage.setText(message);


//                nc.write("setTextAgain");
//                String newLine = (String)nc.read();
//                receivedMessage.appendText(newLine+"\n");
//                while(true)
//                {
//                    String line = (String)nc.read();
//                    receivedMessage.appendText(line+"\n");
//                    System.out.println(line);
//                }

                timer = new Timer();
                timer.scheduleAtFixedRate(timerTask,1000,500);

                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        nc.write("textArea");
                        nc.write(name);
                        nc.write(receiverName);//In case of showing previous messages it is the selected client
                        String message = (String) nc.read();
                        receivedMessage.setText(message);
                    }
                }, 1000, 500);



            }

        });


        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                nc.write("textArea");
                nc.write(name);
                nc.write(receiverName);//In case of showing previous messages it is the selected client
                String message = (String) nc.read();
                receivedMessage.setText(message);

            }
        }, 1000, 500);


        /*
        timerTask = new TimerTask() {
            @Override
            public void run() {
                nc.write("textArea");
                nc.write(name);
                nc.write(receiverName);//In case of showing previous messages it is the selected client
                String message = (String) nc.read();
                receivedMessage.setText(message);
                receivedMessage.positionCaret(message.length());

            }
        };
         */
//        ReaderThread
//        Thread readerThread = new Thread(new Reader(name,receiverName,receivedMessage, nc));
//        readerThread.start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//
//                nc.write("textArea");
//                nc.write(name);
//                nc.write(receiverName);//In case of showing previous messages it is the selected client
//                String message = (String)nc.read();
//                receivedMessage.setText(message);
//            }
//        });
/*
        receivedMessage.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue,
                                Object newValue) {
//                receivedMessage.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
//                receivedMessage.appendText("");
                receivedMessage.selectPositionCaret(receivedMessage.getLength());
                receivedMessage.deselect();
                //use Double.MIN_VALUE to scroll to the top
            }
        });


         */
//        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                new TextAreaReaderFromFile(receivedMessage,newValue);
//            }
//        });
        //TextFiled for outgoing messages
        TextArea outgoingMessage = new TextArea();
        outgoingMessage.setPromptText("Outgoing message");
        outgoingMessage.setFont(new Font("Verdana", 14));
        outgoingMessage.setMaxHeight(150);
        outgoingMessage.setMinHeight(150);
//        outgoingMessage.setPrefSize(250, 50);
        outgoingMessage.setEditable(true);

        //TextField Press Enter
        outgoingMessage.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (outgoingMessage.getText().isEmpty() == false && event.getCode().equals(KeyCode.ENTER)) {
                    //masfiq editted


                    nc.write("message");
                    messageData = new Data();
                    System.out.println(outgoingMessage.getText());
                    messageData.receiverName = receiverName;
                    messageData.senderName = name;

                    messageData.setMessage(outgoingMessage.getText());
                    try {
                        nc.write(messageData.clone());
                    } catch (CloneNotSupportedException e1) {
                        e1.printStackTrace();
                    }

                    outgoingMessage.setText("");
                }
                // if shift pressed a new line will be appended 
                if(event.getCode().equals(KeyCode.SHIFT)){
                    outgoingMessage.appendText("\n");
                }

            }
            

        });

        //SEND Button
        Button sendButton = new Button();
        sendButton.setText("SEND");
        sendButton.setOnAction(e
                -> {

            if (outgoingMessage.getText().isEmpty() == false) {
                //nc.write(receiver.getText());
                nc.write("message");
                messageData = new Data();
                System.out.println(outgoingMessage.getText());
                messageData.receiverName = receiverName;
                messageData.senderName = name;

                messageData.setMessage(outgoingMessage.getText());
                try {
                    nc.write(messageData.clone());
                } catch (CloneNotSupportedException e1) {
                    e1.printStackTrace();
                }
            }

            outgoingMessage.setText("");

        });

        //All Chat Users
        TextArea onlineClients = new TextArea();
        onlineClients.setFont(new Font("Verdana", 14));
        onlineClients.setEditable(false);
        //        onlineClients.setPrefSize(250, 300);
        ScrollPane scrollPane1 = new ScrollPane();
        scrollPane1.setContent(onlineClients);

        //refresh Button
        Button refreshButton = new Button();
        Image image = new Image(getClass().getResourceAsStream("refresh.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        refreshButton.setGraphic(imageView);
        DropShadow shadow = new DropShadow();
        refreshButton.setEffect(shadow);
        refreshButton.setOnAction(e
                -> {
            listView.getItems().clear();
            nc.write("refreshAccountsAgain");
            accounts = (HashMap<String, File>) nc.read();
            for (Map.Entry<String, File> entry : accounts.entrySet()) {
                listView.getItems().add(entry.getKey());
            }
            for (Map.Entry<String, File> entry : accounts.entrySet()) {
                System.out.println(entry.getKey());
            }

        });

        //All users + refresh Button
        VBox vBox = new VBox();
        vBox.getChildren().
                add(listView);
//                addAll(listView, refreshButton);

        //VBox (Received Messages+outgoing messages)
        VBox roMessage = new VBox(10);
//        roMessage.setMaxSize(400, 600);
//        roMessage.setMinSize(400, 600);
        roMessage.getChildren().
                addAll(receivedMessage, outgoingMessage, sendButton);

        //BorderPane
        BorderPane localBorderPane = new BorderPane();
        localBorderPane.setLeft(roMessage);
        localBorderPane.setRight(vBox);//vBox->listView + refresh Button

        //OnClose Request
        stage.setOnCloseRequest(e
                -> {
            e.consume();
            AlertBox.alert(stage, nc, name, timer);

        });
        borderPane.setCenter(localBorderPane);
//        Scene scene = new Scene(localBorderPane, 500, 750);
//        stage.setScene(scene);
//        stage.show();
        return timer;

    }

}
