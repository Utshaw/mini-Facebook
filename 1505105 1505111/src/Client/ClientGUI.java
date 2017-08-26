/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Util.NetworkConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.transitions.JFXFillTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

//import org.controlsfx.control.textfield.TextFields;
//import sun.plugin.dom.core.Text;

/**
 * @author Utshaw
 */
public class ClientGUI  {

    Stage window;
    Home homePage ;
    NetworkConnection nc;
    SignUp_2 goToSignUp;
    private String clientName;
    public static final String APPLICATION_ICON = "file:src/Client/logo-3.png";
    private static String server_IP = "127.0.0.1";

    public ClientGUI() {
        this.window = new Stage();
        window.getIcons().add(new Image(
                APPLICATION_ICON
        ));

    }


    public void start() throws Exception {

        window.setTitle("Messenger");
        //Pane
        BorderPane borderPane = new BorderPane();



        //BackGroundImage
        Image backImage = new Image("/Client/landscape4.jpg",564,1002,true,true,true);
        ImageView backImageView = new ImageView(backImage);
        borderPane.getChildren().add( backImageView );

        //ForeGroundImage
        Image openingImage = new Image("/chat6.png", 350, 250, true, true, true);
        ImageView openingImageView = new ImageView(openingImage);
        openingImageView.setOnMouseEntered(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                openingImageView.setScaleX(1.1);
                openingImageView.setScaleY(1.1);
            }
        });
        openingImageView.setOnMouseExited(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                openingImageView.setScaleX(1);
                openingImageView.setScaleY(1);
            }
        });


        StackPane anchorPane=new StackPane();
        Group group = new Group();
        HBox openingImageBox = new HBox();
        group.getChildren().add(openingImageBox);
        openingImageBox.setAlignment(Pos.CENTER);
        openingImageBox.getChildren().add(openingImageView);
//        anchorPane.setTop(openingImageView);
//        borderPane.setAlignment(anchorPane,Pos.CENTER);
        borderPane.setTop(group);
        borderPane.setAlignment(group,Pos.CENTER);


        //Transition On ImageView
        JFXFillTransition transition1 = new JFXFillTransition(Duration.millis(1000),openingImageBox,Color.VIOLET,Color.INDIGO);
        JFXFillTransition transition2 = new JFXFillTransition(Duration.millis(1000),openingImageBox,Color.INDIGO,Color.BLUE);
        JFXFillTransition transition3 = new JFXFillTransition(Duration.millis(1000),openingImageBox,Color.BLUE,Color.GREEN);
        JFXFillTransition transition4 = new JFXFillTransition(Duration.millis(1000),openingImageBox,Color.GREEN,Color.YELLOW);
        JFXFillTransition transition5 = new JFXFillTransition(Duration.millis(1000),openingImageBox,Color.YELLOW,Color.ORANGE);
        JFXFillTransition transition6 = new JFXFillTransition(Duration.millis(1000),openingImageBox,Color.ORANGE,Color.RED);
        JFXFillTransition transition7 = new JFXFillTransition(Duration.millis(1000),openingImageBox,Color.RED,Color.VIOLET);
        transition1.play();
        transition1.setOnFinished(e->
        {
            transition2.play();
        });
        transition2.setOnFinished(e->
        {
            transition3.play();
        });
        transition3.setOnFinished(e->
        {
            transition4.play();
        });
        transition4.setOnFinished(e->
        {
            transition5.play();
        });
        transition5.setOnFinished(e->
        {
            transition6.play();
        });

        transition6.setOnFinished(e->
        {
            transition7.play();
        });
        transition7.setOnFinished(e->
        {
            transition1.play();
        });




        //DropShadow
        DropShadow dropShadow = new DropShadow();


        /*
        Image backImage = new Image("/Client/landscape.png",400,500,true,true,true);
        ImageView backImageView = new ImageView(backImage);
        borderPane.getChildren().add(backImageView);
         */




        borderPane.setAlignment(openingImageView, Pos.TOP_CENTER);
        borderPane.setPadding(new Insets(10, 10, 10, 10));



        //VBox for USer name Input  +Password
        VBox vBox = new VBox();
        Label namelabel = new Label("Username:");
        namelabel.setFont(Font.font("Comic Sans", 16));
        namelabel.setStyle("-fx-font-weight: bold;");
        namelabel.setTextFill(Color.BLANCHEDALMOND);
//        JFXTextField nameInput = new JFXTextField();
        TextField nameInput = new TextField();
        nameInput.setStyle("-fx-text-inner-color: black;");
        nameInput.setEffect(dropShadow);
//        nameInput.setFocusColor(Color.CYAN);

        nameInput.setFont(new Font(18));
        nameInput.setPrefSize(250, 50);
        nameInput.setPromptText("Enter Your Name Here");


        //HBox for Welcome Text
        HBox hBox = new HBox();
        Label welcomeLabel = new Label("Welcome to FACEBOOK ");
        welcomeLabel.setStyle("-fx-font-weight:bold");
        welcomeLabel.setTextFill(Color.AZURE);
        welcomeLabel.setFont(new Font(18));
        Label userLabel = new Label();
        userLabel.setFont(new Font(14));
        userLabel.setTextFill(Color.AZURE);
        userLabel.setFont(new Font(18));
        hBox.getChildren().addAll(welcomeLabel,userLabel);
        hBox.setAlignment(Pos.CENTER);
        userLabel.textProperty().bind(nameInput.textProperty());



        //Password label + Field

        Label passLabel= new Label("Password:");
        passLabel.setFont(Font.font("Comic Sans", 16));
        passLabel.setStyle("-fx-font-weight:bold");
        passLabel.setTextFill(Color.BLANCHEDALMOND);
//        TextField passInput = TextFields.createClearablePasswordField();
//        JFXPasswordField passInput = new JFXPasswordField();
        PasswordField passInput = new PasswordField();
        passInput.setEffect(dropShadow);
//        passInput.setFocusColor(Color.CYAN);
        passInput.setStyle("-fx-text-inner-color: red;");


        passInput.setFont(new Font(18));
        passInput.setPrefSize(250, 50);
        passInput.setPromptText("password Here");

        vBox.setSpacing(10);
        vBox.getChildren().addAll(hBox,namelabel, nameInput,passLabel,passInput);

        //Label UserName already exist
        Label label = new Label("");
        label.setFont(Font.font("Cambria", 32));
        label.setTextFill(Color.DARKGOLDENROD);
        label.setStyle("-fx-font-weight:bold");


        passInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    clientName = nameInput.getText();
                    if (clientName.isEmpty() == false && passInput.getText().isEmpty() == false) {

                            nc = new NetworkConnection(server_IP, 12345,label);
                            nc.write(clientName +" " +passInput.getText()+ " signin");
                            String getReply = (String) nc.read();
                            if(getReply.equals("alrdy"))
                            {
                                label.setText("Already signed in");
                            }
                            else if(getReply.equals("no"))
                            {
                                label.setText("No account found with this name");
                            }
                            else if(getReply.equals("passError"))
                            {
                                label.setText("Wrong Password");
                            }
                            else if(getReply.equals("true"))
                            {
                                System.out.println(getReply);
                                System.out.println("i am " + clientName);
                                homePage = new Home(nc,window,clientName);
                            }


//                    new MessageBox(nc,window,clientName);


                    }

                }
            }
        });


        //Scene
        Scene scene = new Scene(borderPane, 500, 750);



        //Button for sign up
        JFXButton signUp = new JFXButton("Sign Up");
        signUp.setEffect(dropShadow);
        signUp.setButtonType(JFXButton.ButtonType.RAISED);
        signUp.setRipplerFill(Color.WHITE);
        signUp.setTextFill(Color.GHOSTWHITE);
//        Button signUp = new Button("Sign Up");
        signUp.setStyle("-fx-background-color: #2196f3;-fx-font-weight: bold");
        signUp.setPadding(new Insets(10, 10, 10, 10));
        signUp.setOnAction(e->
        {
            goToSignUp = new SignUp_2();
            try {
                goToSignUp.show(window);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        signUp.setOnMouseEntered(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                signUp.setScaleX(1.1);
                signUp.setScaleY(1.1);
            }
        });
        signUp.setOnMouseExited(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                signUp.setScaleX(1);
                signUp.setScaleY(1);
            }
        });


        //Connect butconnectButtonton
        JFXButton connectButton = new JFXButton("Connect!");
        connectButton.setEffect(dropShadow);
        connectButton.setButtonType(JFXButton.ButtonType.RAISED);
        connectButton.setRipplerFill(Color.WHITE);
        connectButton.setTextFill(Color.GHOSTWHITE);
        connectButton.setStyle("-fx-background-color: #2196f3;-fx-font-weight: bold");
//        Button connectButton = new Button("Connect!");
        //gotoFriends.setStyle("-fx-font: 30 Verdana; -fx-base: #ee2211;");
        connectButton.setPadding(new Insets(10, 10, 10, 10));
        connectButton.setOnAction(e ->
        {

            clientName = nameInput.getText();
            if (clientName.isEmpty() == false && passInput.getText().isEmpty() == false) {
                try {
                    nc = new NetworkConnection(server_IP, 12345);
                    nc.write(clientName +" " +passInput.getText()+ " signin");
                    String getReply = (String) nc.read();
                    if(getReply.equals("alrdy"))
                    {
                        label.setText("Already signed in");
                    }
                    else if(getReply.equals("no"))
                    {
                        label.setText("No account found with this name");
                    }
                    else if(getReply.equals("passError"))
                    {
                        label.setText("Wrong Password");
                    }
                    else if(getReply.equals("true"))
                    {
                        System.out.println(getReply);
                        System.out.println("i am " + clientName);
                        homePage = new Home(nc,window,clientName);
                    }


//                    new MessageBox(nc,window,clientName);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }


        });
        connectButton.setOnMouseEntered(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                connectButton.setScaleX(1.1);
                connectButton.setScaleY(1.1);
            }
        });
        connectButton.setOnMouseExited(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                connectButton.setScaleX(1);
                connectButton.setScaleY(1);
            }
        });


        //Container (nameInput+connectbutton+lavel(USerName exists))
        VBox container = new VBox(10);
        container.setAlignment(Pos.CENTER);

        container.getChildren().add(vBox);
        container.getChildren().add(connectButton);
        container.getChildren().add(signUp);
        container.getChildren().add(label);
        container.setPadding(new Insets(15, 15, 15, 15));
        borderPane.setCenter(container);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(borderPane);
        borderPane.setEffect(dropShadow);



        window.setScene(scene);
        window.setMaximized(false);
        window.setResizable(false);
        window.show();

    }
}
