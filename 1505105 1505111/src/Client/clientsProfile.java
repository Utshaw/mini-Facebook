package Client;

import ClientFileOperation.RecieveFileClientPart;
import ClientFileOperation.SendFileClientpart;
import Util.NetworkConnection;
import Util.Status;
import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sun.nio.ch.Net;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Utshaw on 12/5/2016.
 */
public class clientsProfile {

    String name;
    BorderPane borderPane;
    NetworkConnection networkConnection;
    VBox vBox;
    String fromName;
    JFXButton likeButton[];
    boolean[] myLikeArray;
    Text[] likeCounter;

    public clientsProfile(String fromName, String name, BorderPane borderPane, NetworkConnection networkConnection) {
        this.fromName = fromName;
        this.name = name;
        this.borderPane = borderPane;
        this.networkConnection = networkConnection;
    }

    public void showProfile() {
     //show friend profile picture
        networkConnection.write("friend_profilepicture");
        if (((String) networkConnection.read()).equals("sendingprofilepic")) {
            System.out.println(" i am in sendprofilepic call");
            networkConnection.write(name);
            new RecieveFileClientPart(networkConnection, fromName, name, "friend_pro_pic");
        }

        networkConnection.write("clientsProfile");

        networkConnection.write(name);
        networkConnection.write(fromName);

        String requButotnValue = (String) networkConnection.read();

        //Vbox for friends Section
        VBox friendsBox = new VBox();
        HBox nameAndReqBox = new HBox(400);
        nameAndReqBox.setAlignment(Pos.CENTER);
        friendsBox.getChildren().add(nameAndReqBox);
        Image friend_image = new Image("file:ClientImages\\friendphoto\\"+ name + ".jpg", 400, 600,true, true, false);
        ImageView IMGVIEW_friend_image = new ImageView(friend_image);
        friendsBox.getChildren().add(IMGVIEW_friend_image);
        if (requButotnValue.equals("notRequested")) {
            Text T_sendRequ = new Text("If you want to see\n"
                    + "what he shares with his friends\n"
                    + "send him a friend request");
            T_sendRequ.setStyle("-fx-font: 50 verdana; ");
            friendsBox.getChildren().add(T_sendRequ);
        } else if (requButotnValue.equals("friends")) {
            Integer numberOfStatuses = (Integer) networkConnection.read();
            myLikeArray = (boolean[]) networkConnection.read();
            ScrollPane scrollPane = new ScrollPane();
            likeButton = new JFXButton[numberOfStatuses];
            likeCounter = new Text[numberOfStatuses];
            vBox = new VBox(10);
            for (int i = 0; i < numberOfStatuses; i++) {
                Text text = new Text(name);
                Text statusText = new Text();
                if (myLikeArray[i] == false) {
                    likeButton[i] = new JFXButton("LIKE");
                } else {
                    likeButton[i] = new JFXButton("LIKED");
                }
                likeButton[i].setButtonType(JFXButton.ButtonType.RAISED);
                likeButton[i].setRipplerFill(Color.WHITE);
                likeButton[i].setTextFill(Color.GHOSTWHITE);
                likeButton[i].setStyle("-fx-background-color: #2196f3;-fx-font-weight: bold");
                likeButton[i].setPadding(new Insets(10, 10, 10, 10));
                Integer finalI = i;
                Status statusObj = (Status) networkConnection.read();
                statusText.setText(statusObj.status);
                likeCounter[i] = new Text();
                likeCounter[i].setText(String.valueOf(statusObj.likes) + " people like this");
                likeButton[i].setOnAction(e
                        -> {
                    if (likeButton[finalI].getText().equals("Unlike")) {
                        if (myLikeArray[finalI] == false) {
                            likeCounter[finalI].setText(String.valueOf(Integer.toString(statusObj.likes)) + " people like this");
                        } else {
                            likeCounter[finalI].setText(String.valueOf(Integer.toString(statusObj.likes - 1)) + " people like this");
                        }
                        likeButton[finalI].setText("LIKE");
                        networkConnection.write("unlike");
                        networkConnection.write(name);
                        networkConnection.write(fromName);
                        networkConnection.write(finalI);
                    } else {
                        likeCounter[finalI].setText(String.valueOf(Integer.toString(statusObj.likes + 1)) + " people like this");
                        likeButton[finalI].setText("LIKED");
                        networkConnection.write("like");
                        networkConnection.write(name);
                        networkConnection.write(fromName);
                        networkConnection.write(finalI);
                    }

                });
                likeButton[i].setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        if (likeButton[finalI].getText().equals("LIKED")) {
                            likeButton[finalI].setText("Unlike");
                        }
                    }
                });
                likeButton[i].setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        if (likeButton[finalI].getText().equals("Unlike")) {
                            likeButton[finalI].setText("LIKED");
                        }
                    }
                });

                HBox likeAndCounter = new HBox(10);
                likeAndCounter.setAlignment(Pos.CENTER);
//                TextField comment = new TextField();
//                Button commentButton = new Button();
                likeAndCounter.getChildren().addAll(likeButton[i], likeCounter[i]);

//                comment.setPromptText("Comment Here");
//                commentButton.setText("Comment");
                vBox.getChildren().addAll(text, statusText, likeAndCounter);
                scrollPane.setContent(vBox);

            }
            friendsBox.getChildren().add(scrollPane);
        }

        /*
        //Vbox for friends Section
        VBox friendsBox = new VBox();
        HBox nameAndReqBox = new HBox(50);
        Text nameText = new Text(clientName);
        nameText.setStyle("-fx-font: 30 verdana; ");
        JFXButton reqButton = new JFXButton("SEND FRIEND REQUEST");
        DropShadow dropShadow = new DropShadow();
        reqButton.setEffect(dropShadow);
        reqButton.setButtonType(JFXButton.ButtonType.RAISED);
        reqButton.setRipplerFill(Color.WHITE);
        reqButton.setTextFill(Color.GHOSTWHITE);
        reqButton.setStyle("-fx-background-color: #2196f3");
        nameAndReqBox.getChildren().addAll(nameText,reqButton);
        friendsBox.getChildren().addAll(nameAndReqBox,listView);
        borderPane.setCenter(friendsBox);

         */
        Text nameText = new Text(name);
        nameText.setStyle("-fx-font: 100 verdana; ");
        JFXButton reqButton = new JFXButton("SEND FRIEND REQUEST");
        if (requButotnValue.equals("requested")) {
            reqButton.setText("ACCEPT REQUEST");
        } else if (requButotnValue.equals("requestSent")) {
            reqButton.setText("Friend Request Sent");
        } else if (requButotnValue.equals("notRequested")) {
            reqButton.setText("SEND FRIEND REQUEST");
        } else if (requButotnValue.equals("friends")) {
            reqButton.setText("Friends");
        }
        DropShadow dropShadow = new DropShadow();
        reqButton.setEffect(dropShadow);
        reqButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                reqButton.setScaleX(1.1);
                reqButton.setScaleY(1.1);
            }
        });
        reqButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                reqButton.setScaleX(1);
                reqButton.setScaleY(1);
            }
        });
        reqButton.setOnAction(e
                -> {
            if (reqButton.getText().equals("SEND FRIEND REQUEST")) {
                networkConnection.write("sendfriendRequ");
                networkConnection.write(name);
                networkConnection.write(fromName);
                reqButton.setText("Friend Request Sent");
            } else if (reqButton.getText().equals("Friend Request Sent")) {
                networkConnection.write("cancelfriendRequ");
                networkConnection.write(name);
                networkConnection.write(fromName);
                reqButton.setText("SEND FRIEND REQUEST");
            } else if (reqButton.getText().equals("ACCEPT REQUEST")) {
                networkConnection.write("acceptRequest");
                networkConnection.write(fromName);
                networkConnection.write(name);
                reqButton.setText("FRIENDS");
            } else if (reqButton.getText().equals("Friends")) {
                networkConnection.write("unfriend");
                networkConnection.write(fromName);
                networkConnection.write(name);
                reqButton.setText("SEND FRIEND REQUEST");
            }

        });

        reqButton.setButtonType(JFXButton.ButtonType.RAISED);
        reqButton.setMaxSize(150, 50);
        reqButton.setRipplerFill(Color.WHITE);
        reqButton.setTextFill(Color.GHOSTWHITE);
        reqButton.setStyle("-fx-background-color: #2196f3; -fx-font-weight: bold");
        nameAndReqBox.getChildren().addAll(nameText, reqButton);
//        friendsBox.getChildren().addAll(nameAndReqBox,scrollPane);
        borderPane.setCenter(friendsBox);

    }

}
