package Client;

import Util.NetworkConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.Image;

/**
 * Created by Utshaw on 12/5/2016.
 */
public class Settings  {
    Stage stage;
    String clientName;
    NetworkConnection networkConnection;
    BorderPane borderPane;
    Settings(Stage stage,String name,NetworkConnection networkConnection,BorderPane borderPane)
    {
        clientName=name;
        this.stage=stage;
        this.networkConnection=networkConnection;
        this.borderPane=borderPane;
    }
    public void showSettings()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        javafx.scene.image.Image backImage = new javafx.scene.image.Image("/Client/fb1.png");
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        BackgroundSize bSize = new BackgroundSize(width, height, false, false, true, false);
        Background background2 = new Background(new BackgroundImage(backImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bSize));
        borderPane.setBackground(background2);
        networkConnection.write("settings");
        String password,name;
        stage.setTitle("Settings");
//        borderPane.setTop(null);

        DropShadow dropShadow = new DropShadow();



        //VBox for USer name Change
        VBox containerName = new VBox();
        Label label = new Label("");
        label.setFont(Font.font("Cambria", 32));
        label.setStyle("-fx-font-weight: bold;");
        label.setTextFill(Color.WHITE);
        Label passLabel = new Label("New Password:");
        passLabel.setFont(Font.font("Comic Sans", 32));
        passLabel.setStyle("-fx-font-weight: bold;");
        passLabel.setTextFill(Color.WHITE);
//        JFXTextField passInput = new JFXTextField();
        PasswordField passInput = new PasswordField();
        passInput.setStyle("-fx-text-inner-color: black;");
        passInput.setEffect(dropShadow);
//        passInput.setFocusColor(Color.CYAN);
        passInput.setFont(new Font(18));
        passInput.setPrefSize(250, 50);
        passInput.setPromptText("Enter Your new Password Here");
        //ChangeName Button
        //Button for sign up
        JFXButton changePassword = new JFXButton("Change Password");
        changePassword.setEffect(dropShadow);
        changePassword.setButtonType(JFXButton.ButtonType.RAISED);
        changePassword.setRipplerFill(Color.WHITE);
        changePassword.setTextFill(Color.GHOSTWHITE);
        changePassword.setOnMouseEntered(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                changePassword.setScaleX(1.1);
                changePassword.setScaleY(1.1);
            }
        });
        changePassword.setOnMouseExited(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                changePassword.setScaleX(1);
                changePassword.setScaleY(1);
            }
        });
//        Button changePassword = new Button("Sign Up");
        changePassword.setStyle("-fx-background-color: #2196f3;-fx-font-weight: bold");
        changePassword.setPadding(new Insets(10, 10, 10, 10));
        changePassword.setFont(new Font("Consolas", 14));
        changePassword.setOnAction(e->
        {
            if(passInput.getText().isEmpty()==false )
            {
                networkConnection.write("changePassword");
                networkConnection.write(clientName);
                networkConnection.write(passInput.getText());
                String result = (String)networkConnection.read();
                if(result.equals("true"))
                {
                    label.setText("Password Changed Successfully");
                }
                else
                    label.setText("Failure");
            }
        });
        containerName.getChildren().addAll(passLabel,passInput,changePassword,label);
        containerName.setAlignment(Pos.CENTER);
        containerName.setSpacing(10);
        borderPane.setCenter(containerName);


/*
        //Password Change
        VBox vBox2 = new VBox(10);
        Label passLabel = new Label("New Password:");
        passLabel.setFont(Font.font("Comic Sans", 16));
        passLabel.setStyle("-fx-font-weight: bold;");
        passLabel.setTextFill(Color.BROWN);
//        JFXTextField passInput = new JFXTextField();
        TextField passInput = new TextField();
        passInput.setStyle("-fx-text-inner-color: black;");
        passInput.setEffect(dropShadow);
//        passInput.setFocusColor(Color.CYAN);

        passInput.setFont(new Font(18));
        passInput.setPrefSize(250, 50);
        passInput.setPromptText("Enter Your new Password Here");
        vBox2.getChildren().addAll(passLabel,passInput);


*/



/*

        //changeButton
        //Button for sign up
        JFXButton changeEdits = new JFXButton("Proceed");
        changeEdits.setEffect(dropShadow);
        changeEdits.setButtonType(JFXButton.ButtonType.RAISED);
        changeEdits.setRipplerFill(Color.WHITE);
        changeEdits.setTextFill(Color.GHOSTWHITE);
//        Button changeEdits = new Button("Sign Up");
        changeEdits.setStyle("-fx-background-color: #2196f3");
        changeEdits.setPadding(new Insets(10, 10, 10, 10));
        changeEdits.setFont(new Font("Consolas", 14));
        changeEdits.setOnAction(e->
        {
            if(passInput.getText().isEmpty()==false &&
                    passInput.getText().isEmpty()==false)
            {
                networkConnection.write("settings");
                networkConnection.write(clientName);
                networkConnection.write(passInput.getText());
                networkConnection.write(passInput.getText());
                String result = (String)networkConnection.read();
                if(result.equals("false"))
                {
                    label.setText("Username Already Exists");
                }
                else
                    label.setText("Saved Changes Successfully");
            }
        });


*/


    }

}
