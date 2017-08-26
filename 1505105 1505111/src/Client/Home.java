package Client;

import ClientFileOperation.RecieveFileClientPart;
import Client.AlertBox;
import Client.MessageBox;
import Client.Settings;
import Client.clientsProfile;
import Util.NetworkConnection;
import Util.Status;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * Created by Utshaw on 11/28/2016.
 */
public class Home {
//binding textarea

    public int count = 1;
    int previous_numb_of_line;
    int max_numb_of_line = 5;
    double font = 30;
//binding textarea
    NetworkConnection networkConnection;
    String[] accountsList;
    Stage stage;
    BorderPane B_pane;
    String clientName;
    Timer timer;
    TranslateTransition transition_EASE_OUT;
    Text[] likeCounter;

    public Home(NetworkConnection networkConnection, Stage stage, String name) {
        this.networkConnection = networkConnection;
        this.stage = stage;
        clientName = name;
        timer = null;
        show();
    }


    public void show() {
        VBox VB_3 = new VBox(10);
        networkConnection.write("home");
        networkConnection.write(clientName);
        Integer numberOfStatuses = (Integer) networkConnection.read();
        Status[] statuses = (Status[]) networkConnection.read();
        likeCounter = new Text[numberOfStatuses];
        for (int i = 0; i < numberOfStatuses; i++) {
            Text text = new Text(clientName);
            text.setFont(Font.font("Verdana", 20));
            text.setFill(Color.CRIMSON);
            Text statusText = new Text();
            statusText.setText(statuses[i].status);
            statusText.setFont(Font.font("Verdana", 15));
            statusText.setFill(Color.BLACK);
            likeCounter[i] = new Text();
            likeCounter[i].setText(Integer.toString(statuses[i].likes));
            likeCounter[i].setFont(Font.font("Verdana", 15));
            likeCounter[i].setText(Integer.toString(statuses[i].likes) + " people like this");
            VB_3.getChildren().addAll(text,statusText,likeCounter[i]);
        }

        B_pane = new BorderPane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Image image = new Image("/Client/social3.png");
        BackgroundSize bSize = new BackgroundSize(width, height, false, false, true, false);
        B_pane.setBackground(new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));



        HBox HB_top_gap = new HBox();
        HB_top_gap.setMaxHeight(15);
        HB_top_gap.setMinHeight(15);
        
        B_pane.setTop(HB_top_gap);
        /* EDITING WAS LAST HERRE */
        /*

        //ImageView for banner
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Image image = new Image("/Client/back.png",width,height,false,true,true);
        ImageView imageView = new ImageView(image);
        borderPane.getChildren().add(imageView);
         */
        DropShadow dropShadow = new DropShadow();

        TextArea TA_status = new TextArea();

        TA_status.setEffect(dropShadow);
        //TA_status.setFont(new Font(14));
        //TF_status.setPrefSize(250,50);
        TA_status.setPromptText("Enter status here");

        TA_status.setMinSize(400, 60);
        TA_status.setMaxSize(400, 60);

        TA_status.setStyle(""
                + "-fx-font-size: 30px;"
                + "-fx-font-style: normal;"
                + "-fx-font-weight: normal;"
                + "-fx-font-family: serif;"
                + "-fx-text-fill: green;"
                + "-fx-background-color: blue");
//        TA_status.setLayoutX(350);
//        TA_status.setLayoutY(50);
        TA_status.setEditable(true);
//binding textarea autometically
        TA_status.setFont(new Font(font));
        TA_status.setOnKeyPressed(ae -> {
            if (ae.getCode() == KeyCode.ENTER) {

                if (count < max_numb_of_line) {  //count global, 5= max line
                    count++;
                }
                TA_status.setMaxSize(400, 60 * count);
                TA_status.setMinSize(400, 60 * count);
                previous_numb_of_line = TA_status.getText().split("\n").length + 1;   //prev length global
// /*will think later*/                     ta.setFont(new Font(--font));

            }
            if (ae.getCode() == KeyCode.BACK_SPACE) {
                int length = TA_status.getText().split("\n").length;
                if (length < previous_numb_of_line) {
                    if (previous_numb_of_line <= max_numb_of_line) {
                        count--;
                    }
                    TA_status.setMaxSize(400, 60 * count);
                    TA_status.setMinSize(400, 60 * count);

                    previous_numb_of_line = length;
///*will think later*/                    ta.setFont(new Font(++font));
                }

            }

        });

// binding end
        Button B_post = new Button("POST");
        B_post.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                B_post.setScaleX(1.1);
                B_post.setScaleY(1.1);
            }
        });
        B_post.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                B_post.setScaleX(1);
                B_post.setScaleY(1);
            }
        });
        B_post.setEffect(dropShadow);
        B_post.setTextFill(Color.GHOSTWHITE);
        B_post.setStyle("-fx-background-color: #2196f3;-fx-font-weight: bold");
        /*  COVERPHOTO AND PROFILE PIC WILL BE IN PROFILE PAGE

        // Image coverphoto
        Image IMG_coverphoto = new Image("file:src/Images/Cover photo.jpg", 300, 200, false, true, true);
        ImageView IMGVIEW_coverphoto = new ImageView(IMG_coverphoto);
        IMGVIEW_coverphoto.setLayoutX(350);
        IMGVIEW_coverphoto.setLayoutY(5);
        //IMGVIEW_propic.sets
// image propic   
        Image IMG_propic = new Image("file:src/Images/Pro pic.jpg", 200, 125, false, true, true);
        ImageView IMGVIEW_propic = new ImageView(IMG_propic);
        IMGVIEW_propic.setLayoutX(5);
        IMGVIEW_propic.setLayoutY(5);
         */
        VBox VB_1 = new VBox(5, TA_status, B_post);
        VBox VB_4 = new VBox(5);
      
        VB_3.setMaxWidth(400);
        VB_3.setMinWidth(400);
   //     VB_3.setStyle("-fx-background-color: #44fff5");

        // after giving stauts
        ScrollPane scrollPane = new ScrollPane(VB_3);
 
//        scrollPane.setStyle("-fx-background-color: #44fff5");
//        scrollPane.fitToWidthProperty().set(true);        
//        scrollPane.fitToHeightProperty().set(true);

        scrollPane.setMinSize(410, 450);
        scrollPane.setMaxSize(410, 450);
        scrollPane.setVmax(1000);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        B_post.setOnAction(e
                -> {
            if (!TA_status.getText().trim().equals("")) {

                networkConnection.write("status");
                networkConnection.write(clientName);
                Text text = new Text(clientName);
                text.setFont(Font.font("Verdana", 20));
                text.setFill(Color.CRIMSON);
                String your_status = TA_status.getText();
                Text text1 = new Text(your_status);
                Status status = new Status();
                status.status=your_status;
                networkConnection.write(status);

                TA_status.setText("");
                TA_status.setMinSize(400, 60);
                TA_status.setMaxSize(400, 60);

                text1.setFont(Font.font("Verdana", 15));
                text1.setFill(Color.BLACK);
                Text likeText = new Text();
            likeText.setText("0 people like this");
            likeText.setFont(Font.font("Verdana", 15));
            
//                JFXButton likeButton = new JFXButton("LIKE");
//                likeButton.setButtonType(JFXButton.ButtonType.RAISED);
//                likeButton.setRipplerFill(Color.WHITE);
//                likeButton.setTextFill(Color.GHOSTWHITE);
//                likeButton.setStyle("-fx-background-color: #2196f3;-fx-font-weight: bold");
//                likeButton.setPadding(new Insets(10, 10, 10, 10));
//                TextField TF_comment = new TextField();
//                Button commentButton = new Button("Comment");







                VB_3.getChildren().addAll(text, text1, likeText);
//                scrollPane.setContent(VB_3);

//                commentButton.setOnAction(actionEvent
//                        -> {
//                    if (TF_comment.getText().isEmpty() == false) {
//                        TextArea TA_comment_show = new TextArea();
//
//                        TA_comment_show.setEditable(false);
//                        TA_comment_show.setText(TF_comment.getText());
//                        TA_comment_show.setFont(new Font(10));
//                        TA_comment_show.textProperty().addListener(new ChangeListener<String>() {
//                            @Override
//                            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                                //TA_comment_show.setPrefWidth(.length() * 7); // why 7? Totally trial number.
//                                TA_comment_show.setMaxSize(600, 100);
//                                TA_comment_show.setMinSize(600, 100);
//
//                            }
//                        });
//                        TF_comment.setText("");
//                        VB_3.getChildren().add(TA_comment_show);
//                    }
//
//                });
            }
        });

        //        scrollPane.setContent(VB_4);
        //       VBox VB_5 = new VBox(VB_1, VB_4);
//        VB_4.setMaxSize(600, 750);
//        VB_4.setMaxSize(600, 750);
//        VB_4.setLayoutX(350);
//        VB_4.setLayoutY(5);
        VB_4.getChildren().addAll(VB_1, scrollPane);
        BorderPane centerPane = new BorderPane();


// THIS IS OK
        //VBox for Left Panel
        VBox VB_leftpanel = new VBox(10);
        /*
        rect.setArcHeight(50);
        rect.setArcWidth(50);
        rect.setFill(Color.VIOLET);
        TranslateTransition tt = new TranslateTransition(Duration.millis(2000), rect);
        tt.setByX(100f);
        tt.setCycleCount(2);
        tt.setAutoReverse(false);
         */
        transition_EASE_OUT  = createTransition_EASE_OUT(VB_leftpanel);
        transition_EASE_OUT.play();
        /*
        TranslateTransition tt = new TranslateTransition(Duration.millis(2000),VB_leftpanel);
        tt.setByX(10f);
        tt.setCycleCount(2);
        tt.setAutoReverse(true);
        tt.play();
        */




//        VB_leftpanel.setLayoutX(5);
//        VB_leftpanel.setLayoutY(50);
        VB_leftpanel.setMaxSize(340, 800);
        VB_leftpanel.setMinSize(340, 800);
        // VB_leftpanel.setStyle("-fx-background-color: #2196f3");
        // Profile
        JFXButton B_profile = new JFXButton(clientName);
        B_profile.setTextAlignment(TextAlignment.CENTER);
//        gotoNewsFeed.setStyle("-fx-font: 30 Verdana; -fx-base: #ee2211;");
        B_profile.setStyle("-fx-font: 24 Verdana ; -fx-background-color: #D3D3D3");
        Image imageprofile = new Image("/Client/profile button.png",150,80,true,true,true);
        B_profile.setGraphic(new ImageView(imageprofile));
        B_profile.setButtonType(JFXButton.ButtonType.RAISED);
        B_profile.setTextFill(Color.BLACK);
        B_profile.setOnMouseEntered(ae->{
            B_profile.setScaleX(1.2);
            B_profile.setScaleY(1.2);
        });
        B_profile.setOnMouseExited(ae->{
            B_profile.setScaleX(1);
            B_profile.setScaleY(1);
        });
        B_profile.setOnAction(ae -> {
             networkConnection.write("profile");
            if (networkConnection.read().equals("sendingprofile")) {
                String client_data = showprofile();
                System.out.println("Iam after showprofile() call :"+ client_data);
                new ProfilePage(stage, B_pane, clientName, client_data, networkConnection);
            }
        });
        //NewsFeed
        JFXButton gotoNewsFeed = new JFXButton();
        gotoNewsFeed.setStyle("-fx-font: 24 Verdana ; -fx-background-color: #D3D3D3");
        Image imageNews = new Image("/Client/news2.png",150,80,true,true,true);
        gotoNewsFeed.setOnMouseEntered(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                gotoNewsFeed.setScaleX(1.2);
                gotoNewsFeed.setScaleY(1.2);
            }
        });
        gotoNewsFeed.setOnMouseExited(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                gotoNewsFeed.setScaleX(1);
                gotoNewsFeed.setScaleY(1);
            }
        });
        gotoNewsFeed.setGraphic(new ImageView(imageNews));
        gotoNewsFeed.setOnAction(e->
        {
//            transition_EASE_OUT  = createTransition_EASE_OUT(VB_leftpanel);
//            transition_EASE_OUT.play();
            new Home(networkConnection,stage,clientName);
        });
//        gotoNewsFeed.setStyle("-fx-font: 30 Verdana; -fx-base: #ee2211;");
//        gotoNewsFeed.setStyle("-fx-font: 24 Verdana ; -fx-background-color: #d00dd3");
        gotoNewsFeed.setButtonType(JFXButton.ButtonType.RAISED);
        gotoNewsFeed.setRipplerFill(Color.WHITE);
//        gotoNewsFeed.setTextFill(Color.BLACK);
//        gotoNewsFeed.setMaxSize(175, 50);
//        gotoNewsFeed.setMinSize(175, 50);
        /*
        connectButton.setButtonType(JFXButton.ButtonType.RAISED);
        connectButton.setRipplerFill(Color.WHITE);
        connectButton.setTextFill(Color.GHOSTWHITE);
        connectButton.setStyle("-fx-background-color: #2196f3");
         */
//        VB_leftpanel.getChildren().add(gotoNewsFeed);

        //message
        JFXButton gotoMessage = new JFXButton();
        Image imageMessage = new Image("/Client/envelope2.png",150,80,true,true,true);
        gotoMessage.setButtonType(JFXButton.ButtonType.RAISED);
        gotoMessage.setStyle("-fx-font: 24 Verdana ; -fx-background-color: #D3D3D3");
        gotoMessage.setRipplerFill(Color.WHITE);
        gotoMessage.setOnMouseEntered(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                gotoMessage.setScaleX(1.2);
                gotoMessage.setScaleY(1.2);
            }
        });
        gotoMessage.setOnMouseExited(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                gotoMessage.setScaleX(1);
                gotoMessage.setScaleY(1);
            }
        });
        gotoMessage.setGraphic(new ImageView(imageMessage));
//        gotoMessage.setStyle("-fx-font: 24 Verdana ; -fx-background-color: #d00dd3");
//        gotoMessage.setTextFill(Color.BLACK);
//        gotoMessage.setMaxSize(175, 50);
//        gotoMessage.setMinSize(175, 50);
        gotoMessage.setOnAction(e
                -> {
            transition_EASE_OUT  = createTransition_EASE_OUT(VB_leftpanel);
            transition_EASE_OUT.play();
            networkConnection.write("refreshAccountsAgain");
            networkConnection.write(clientName);
            timer = new MessageBox(networkConnection, stage, clientName, B_pane).clientsBox();
        });
//        VB_leftpanel.getChildren().add(gotoMessage);

        //Friends Section
        JFXButton gotoPeoples = new JFXButton();
        gotoPeoples.setButtonType(JFXButton.ButtonType.RAISED);
        gotoPeoples.setRipplerFill(Color.WHITE);
        Image peoplesImage = new Image("/Client/person7.png",150,80,true,true,true);
        gotoPeoples.setOnMouseEntered(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                gotoPeoples.setScaleX(1.2);
                gotoPeoples.setScaleY(1.2);
            }
        });
        gotoPeoples.setOnMouseExited(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                gotoPeoples.setScaleX(1);
                gotoPeoples.setScaleY(1);
            }
        });
        gotoPeoples.setGraphic(new ImageView(peoplesImage));
//        gotoPeoples.setStyle("-fx-font: 24 Verdana ; -fx-background-color: #d00dd3");
//        gotoPeoples.setTextFill(Color.BLACK);
//        gotoPeoples.setMaxSize(175, 50);
//        gotoPeoples.setMinSize(175, 50);
//        VB_leftpanel.getChildren().add(gotoPeoples);
        gotoPeoples.setOnAction(e
                -> {
            if (timer != null) {
                timer.cancel();
                timer.purge();
            }
            transition_EASE_OUT  = createTransition_EASE_OUT(VB_leftpanel);
            transition_EASE_OUT.play();
            peoples();
        });

        //Friend Request
        JFXButton friendRquest = new JFXButton("");
        friendRquest.setButtonType(JFXButton.ButtonType.RAISED);
        friendRquest.setRipplerFill(Color.WHITE);
        friendRquest.setStyle("-fx-font: 24 Verdana ; -fx-background-color: #D3D3D3");
        Image requestImage = new Image("/Client/request.png",150,80,true,true,true);
        friendRquest.setOnMouseEntered(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                friendRquest.setScaleX(1.2);
                friendRquest.setScaleY(1.2);
            }
        });
        friendRquest.setOnMouseExited(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                friendRquest.setScaleX(1);
                friendRquest.setScaleY(1);
            }
        });
        friendRquest.setGraphic(new ImageView(requestImage));
        gotoPeoples.setStyle("-fx-font: 24 Verdana ; -fx-background-color: #D3D3D3");
//        gotoPeoples.setTextFill(Color.BLACK);
//        gotoPeoples.setMaxSize(175, 50);
//        gotoPeoples.setMinSize(175, 50);
//        VB_leftpanel.getChildren().add(gotoPeoples);
        gotoPeoples.setOnAction(e
                -> {
            if (timer != null) {
                timer.cancel();
                timer.purge();
            }
            transition_EASE_OUT  = createTransition_EASE_OUT(VB_leftpanel);
            transition_EASE_OUT.play();
            peoples();
        });

//        friendRquest.setStyle("-fx-font: 24 Verdana ; -fx-background-color: #d00dd3");
//        friendRquest.setTextFill(Color.BLACK);
//        friendRquest.setMaxSize(175, 50);
//        friendRquest.setMinSize(175, 50);
        friendRquest.setOnAction(e
                -> {
            if (timer != null) {
                timer.cancel();
                timer.purge();
            }
            networkConnection.write("requests");
            networkConnection.write(clientName);
            transition_EASE_OUT  = createTransition_EASE_OUT(VB_leftpanel);
            transition_EASE_OUT.play();
            friendRequests();
        });
//        VB_leftpanel.getChildren().add(friendRquest);
//        VB_leftpanel.setLayoutX(5);
//        VB_leftpanel.setLayoutY(150);
        //Settings Button
        JFXButton settingsButton = new JFXButton();
        settingsButton.setStyle("-fx-font: 24 Verdana ; -fx-background-color: #D3D3D3");
        settingsButton.setButtonType(JFXButton.ButtonType.RAISED);
        settingsButton.setRipplerFill(Color.WHITE);
//        settingsButton.setStyle("-fx-font: 24 Verdana ; -fx-background-color: #d00dd3");
//        settingsButton.setTextFill(Color.BLACK);
//        settingsButton.setMaxSize(175, 50);
//        settingsButton.setMinSize(175, 50);
        Image settingsImage = new Image("/Client/settings.png",150,80,true,true,true);
        settingsButton.setOnMouseEntered(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                settingsButton.setScaleX(1.2);
                settingsButton.setScaleY(1.2);
            }
        });
        settingsButton.setOnMouseExited(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                settingsButton.setScaleX(1);
                settingsButton.setScaleY(1);
            }
        });
        settingsButton.setGraphic(new ImageView(settingsImage));
//        VB_leftpanel.getChildren().add(settingsButton);

        settingsButton.setOnAction(e
                -> {
            if (timer != null) {
                timer.cancel();
                timer.purge();
            }
            transition_EASE_OUT  = createTransition_EASE_OUT(VB_leftpanel);
            transition_EASE_OUT.play();
            new Settings(stage, clientName, networkConnection, B_pane).showSettings();
        });
        JFXButton signOutButton = new JFXButton("");
        signOutButton.setButtonType(JFXButton.ButtonType.RAISED);
        signOutButton.setRipplerFill(Color.WHITE);
        signOutButton.setStyle("-fx-font: 24 Verdana ; -fx-background-color: #D3D3D3");
        Image signOutImage = new Image("/Client/logout.png",135,80,true,true,true);
        signOutButton.setOnMouseEntered(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                signOutButton.setScaleX(1.2);
                signOutButton.setScaleY(1.2);
            }
        });
        signOutButton.setOnMouseExited(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                signOutButton.setScaleX(1);
                signOutButton.setScaleY(1);
            }
        });
        signOutButton.setGraphic(new ImageView(signOutImage));
        signOutButton.setStyle("-fx-font: 24 Verdana ; -fx-background-color: #D3D3D3");
        signOutButton.setOnAction(e->
        {
            AlertBox.alert(stage, networkConnection, clientName, timer);
        });

        VB_leftpanel.getChildren().addAll(B_profile, gotoNewsFeed, gotoMessage, gotoPeoples, friendRquest, settingsButton,signOutButton);



        VBox VB_top = new VBox();
        VB_top.setMaxHeight(120);
        VB_top.setMinHeight(75);

        Scene scene = new Scene(B_pane, 1300, 750);


        B_pane.setLeft(VB_leftpanel);
        centerPane.setTop(VB_top);
        centerPane.setCenter(VB_4);
        B_pane.setCenter(centerPane);

//        B_pane.getChildren().addAll(VB_4, VB_leftpanel);

        stage.setTitle("HOME");
        stage.setScene(scene);

        //AlertBox
        stage.setOnCloseRequest(e
                -> {
            e.consume();
            AlertBox.alert(stage, networkConnection, clientName, timer);

        });
        stage.setFullScreen(true);
        stage.show();

    }

    private String showprofile() {
        System.out.println("client show profile is called");
    //receiving profile picture
        new RecieveFileClientPart(networkConnection, clientName, "profilepicture");
    //receiving coverohoto
        // WHEN CLIENT WILL CHOSE A COVER PHOTO TRHEN THIS FUCTION NEDDED OTHERWISE,

       // new RecieveFileClientPart(networkConnection, clientName, "coverphoto");

        //receiving client datas
        String client_data = (String) networkConnection.read();
        System.out.println("client data read :" + client_data);
        return  client_data;     
    }

    private void friendRequests() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        Image image = new Image("/Client/mic1.png");
        BackgroundSize bSize = new BackgroundSize(width, height, false, false, true, false);
        B_pane.setBackground(new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize)));
        String[] list = (String[]) networkConnection.read();
        JFXListView<String> listView = new JFXListView<>();
        listView.setExpanded(false);
        listView.depthProperty().set(1);
        listView.getItems().addAll(list);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                new clientsProfile(clientName, newValue, B_pane, networkConnection).showProfile();
            }
        });
        B_pane.setCenter(listView);
    }

    private void peoples() {
        networkConnection.write("peoples");
        networkConnection.write(clientName);

        accountsList = (String[]) networkConnection.read();

        JFXListView<String> listView = new JFXListView<>();
        listView.setExpanded(false);
        listView.depthProperty().set(1);
        listView.getItems().addAll(accountsList);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                new clientsProfile(clientName, newValue, B_pane, networkConnection).showProfile();
            }
        });
        B_pane.setCenter(listView);

    }
    TranslateTransition createTransition_EASE_OUT(final VBox iv){
        TranslateTransition transition = TranslateTransitionBuilder.create()
                .node(iv)
                .fromX(0)
                .toX(25)
                .duration(Duration.millis(750))
                .interpolator(Interpolator.EASE_OUT)
                .cycleCount(1)
                .build();

        return transition;
    }

}


