/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import ClientFileOperation.SendFileClientpart;
import Util.NetworkConnection;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.transitions.JFXFillTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFileChooser;

import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import sun.security.util.Password;

/**
 *
 * @author User
 */
public class SignUp_2 {

    File target_file;
    private static String server_IP = "127.0.0.1";


    Stage primaryStage;

    public SignUp_2() {

    }

    public boolean show(Stage primaryStage) {
        primaryStage.setTitle("Sign Up for facebook");
        primaryStage.setResizable(false);
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #36e7ff");
        VBox VB_body = new VBox(5);
        VB_body.setLayoutX(30);
        VB_body.setLayoutY(60);

// name label, textfield,         
        Label L_firstname = new Label("First Name");
        L_firstname.setTextFill(Color.AZURE);
        L_firstname.setStyle(
                "-fx-font-size: 15;-fx-font-weight:bold"
        );
        TextField TF_firstname = new TextField();
        TF_firstname.setMaxSize(200, 30);
        TF_firstname.setMinSize(200, 30);
        TF_firstname.setPromptText("your first name");
        TF_firstname.setStyle(
                "-fx-font-size: 13;"
        );
        VBox VB_firstname = new VBox(5, L_firstname, TF_firstname);
        Label L_lastname = new Label("Last Name");
        L_lastname.setTextFill(Color.AZURE);
        L_lastname.setStyle(
                "-fx-font-size: 15;-fx-font-weight:bold"
        );
        TextField TF_lastname = new TextField();
        TF_lastname.setMaxSize(200, 30);
        TF_lastname.setMinSize(200, 30);
        TF_lastname.setPromptText("your last name");
        TF_lastname.setStyle(
                "-fx-font-size: 13;"
        );
        VBox VB_lastname = new VBox(5, L_lastname, TF_lastname);

        HBox HB_1 = new HBox(10);
        HB_1.getChildren().addAll(VB_firstname, VB_lastname);

// email, country label, textfield,     
        Label L_email = new Label("Email Address");
        L_email.setTextFill(Color.AZURE);
        L_email.setStyle(
                "-fx-font-size: 15;-fx-font-weight:bold"
        );
        TextField TF_email = new TextField();
        TF_email.setMaxSize(200, 30);
        TF_email.setMinSize(200, 30);
        TF_email.setPromptText("your valid email address");
        TF_email.setStyle(
                "-fx-font-size: 13;"
        );
        VBox VB_email = new VBox(5, L_email, TF_email);
        Label L_country = new Label("Country");
        L_country.setTextFill(Color.AZURE);
        L_country.setStyle(
                "-fx-font-size: 15;-fx-font-weight:bold"
        );
        TextField TF_country = new TextField();
        TF_country.setMaxSize(200, 30);
        TF_country.setMinSize(200, 30);
        TF_country.setPromptText("Bangladesh");
        TF_country.setStyle(
                "-fx-font-size: 13;"
        );
        VBox VB_country = new VBox(5, L_country, TF_country);
        HBox HB_2 = new HBox(10);
        HB_2.getChildren().addAll(VB_email, VB_country);

//date of birth, phone number
        Label L_DateofBirth = new Label("Date of Birth");
        L_DateofBirth.setTextFill(Color.AZURE);
        L_DateofBirth.setStyle(
                "-fx-font-size: 15;-fx-font-weight:bold"
        );
        TextField TF_date = new TextField();
        TF_date.setMaxSize(50, 30);
        TF_date.setMinSize(50, 30);
        TF_date.setPromptText("date");
        TF_date.setStyle(
                "-fx-font-size: 13;"
        );
        TextField TF_month = new TextField();
        TF_month.setMaxSize(55, 30);
        TF_month.setMinSize(55, 30);
        TF_month.setPromptText("month");
        TF_month.setStyle(
                "-fx-font-size: 13;"
        );
        TextField TF_year = new TextField();
        TF_year.setMaxSize(50, 30);
        TF_year.setMinSize(50, 30);
        TF_year.setPromptText("year");
        TF_year.setStyle(
                "-fx-font-size: 13;"
        );
        HBox HB_DateofBirth = new HBox(25);
        HB_DateofBirth.getChildren().addAll(TF_date, TF_month, TF_year);
        VBox VB_dateofbirth = new VBox(5, L_DateofBirth, HB_DateofBirth);

        Label L_phoneNumber = new Label("Phone Number");
        L_phoneNumber.setTextFill(Color.AZURE);
        L_phoneNumber.setStyle(
                "-fx-font-size: 15;-fx-font-weight:bold"
        );
        TextField TF_phonenumber = new TextField();
        TF_phonenumber.setMaxSize(200, 30);
        TF_phonenumber.setMinSize(200, 30);
        TF_phonenumber.setPromptText("your phone number");
        TF_phonenumber.setStyle(
                "-fx-font-size: 13;"
        );
        VBox VB_Phonenumber = new VBox(5, L_phoneNumber, TF_phonenumber);

        HBox HB_3 = new HBox(10);
        HB_3.getChildren().addAll(VB_dateofbirth, VB_Phonenumber);

        //facebook username
        Label L_username = new Label("facebook Username");
        L_username.setTextFill(Color.AZURE);
        L_username.setStyle(
                "-fx-font-size: 15;-fx-font-weight:bold"
        );
        TextField TF_username = new TextField();
        TF_username.setMaxSize(200, 30);
        TF_username.setMinSize(200, 30);
        TF_username.setPromptText("your unique username");
        TF_username.setStyle(
                "-fx-font-size: 13;"
        );
        VBox VB_username = new VBox(5, L_username, TF_username);
        HBox HB_4 = new HBox(VB_username);
//facebook password + repeat password
        Label L_password = new Label("facebook Password");
        L_password.setTextFill(Color.AZURE);
        L_password.setStyle(
                "-fx-font-size: 15;-fx-font-weight:bold"
        );
        PasswordField PF_password = new PasswordField();
        PF_password.setMaxSize(200, 30);
        PF_password.setMinSize(200, 30);
        PF_password.setPromptText("chose a strong password");
        PF_password.setStyle(
                "-fx-font-size: 13;"
        );
        VBox VB_password = new VBox(5, L_password, PF_password);

        //repeat password
        Label L_repeatpassword = new Label("Re-peat Password");
        L_repeatpassword.setTextFill(Color.AZURE);
        L_repeatpassword.setStyle(
                "-fx-font-size: 15;-fx-font-weight:bold"
        );
        PasswordField PF_repeatpassword = new PasswordField();
        PF_repeatpassword.setMaxSize(200, 30);
        PF_repeatpassword.setMinSize(200, 30);
        PF_repeatpassword.setPromptText("carefully repeat the password");
        PF_repeatpassword.setStyle(
                "-fx-font-size: 13;"
        );
        VBox VB_repeatpassword = new VBox(5, L_repeatpassword, PF_repeatpassword);

        HBox HB_5 = new HBox(10, VB_password, VB_repeatpassword);

//Gender
        Label L_gender = new Label("Gender");
        L_gender.setTextFill(Color.AZURE);
        L_gender.setStyle(
                "-fx-font-size: 15;-fx-font-weight:bold"
        );
        TextField TF_gender = new TextField();
        TF_gender.setMaxSize(200, 30);
        TF_gender.setMinSize(200, 30);
        TF_gender.setPromptText("Male");
        TF_gender.setStyle(
                "-fx-font-size: 13;"
        );
        VBox VB_gender = new VBox(5, L_gender, TF_gender);
        HBox HB_6 = new HBox(VB_gender);
//radiobutton agree services
        JFXRadioButton RB_agree = new JFXRadioButton();
        RB_agree.setSelectedColor(Color.ANTIQUEWHITE);
        RB_agree.setUnSelectedColor(Color.YELLOW);

        Label L_agree = new Label("I agree to the terms of service of utsFacebooKmas ");
        L_agree.setTextFill(Color.AZURE);
        L_agree.setStyle(
                "-fx-font-size: 15;-fx-font-weight:bold"
        );
        HBox HB_agree = new HBox(5, RB_agree, L_agree);

//Button create
        JFXButton B_create = new JFXButton("Create My Accoount");
        B_create.setOnMouseEntered(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                B_create.setScaleX(1.1);
                B_create.setScaleY(1.1);
            }
        });
        B_create.setOnMouseExited(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                B_create.setScaleX(1);
                B_create.setScaleY(1);
            }
        });
        B_create.setButtonType(JFXButton.ButtonType.RAISED);
        B_create.setRipplerFill(Color.WHITE);
        B_create.setTextFill(Color.GHOSTWHITE);
        B_create.setStyle("-fx-background-color: #2196f3;-fx-font-weight: bold");
        B_create.setPadding(new Insets(10, 10, 10, 10));


//Label wrongInput
        Label L_wrongInput = new Label();
        L_wrongInput.setTextFill(Color.AZURE);
        L_wrongInput.setStyle(
                "-fx-font-size: 15;-fx-font-weight:bold"
        );

//vbox for  radiobutton + create + wronginput     
        VBox VB_radio_create = new VBox(5, HB_agree, B_create, L_wrongInput);
        VB_radio_create.setLayoutX(30);
        VB_radio_create.setLayoutY(500);

// chose profile pic 
        //image
        Image IMG_propic = new Image("file:ClientImages/samplepropic/sample profile pic.png", 150, 150, true, true, false);
        ImageView IMGVIEW_propic = new ImageView(IMG_propic);
        // choice label
        Label L_propic = new Label("Chose your own profile picture" + "\n" + "so that your friends can find you easily");
        L_propic.setTextFill(Color.AZURE);
        L_propic.setStyle(
                "-fx-font-size: 15;-fx-font-weight:bold"
        );
        //choice button
        JFXButton B_propic = new JFXButton("Chose");
        B_propic.setButtonType(JFXButton.ButtonType.RAISED);
        B_propic.setRipplerFill(Color.WHITE);
        B_propic.setTextFill(Color.GHOSTWHITE);
        B_propic.setStyle("-fx-background-color: #2196f3;-fx-font-weight: bold");
        B_propic.setPadding(new Insets(10, 10, 10, 10));
        B_propic.setOnMouseEntered(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                B_propic.setScaleX(1.1);
                B_propic.setScaleY(1.1);
            }
        });
        B_propic.setOnMouseExited(new EventHandler<MouseEvent>
                () {
            @Override
            public void handle(MouseEvent t) {
                B_propic.setScaleX(1);
                B_propic.setScaleY(1);
            }
        });
   //label for checking image size
        Label L_image_size = new Label();
        L_image_size.setTextFill(Color.RED);
        L_image_size.setStyle(
                "-fx-font-size: 15;-fx-font-weight:bold"
        );

        //wrap up label+button
        VBox VB_label_chosebtn = new VBox(5, L_propic, B_propic, L_image_size);
        //wrap up image and labi+button
        VBox VB_propic_label_chosebtn = new VBox(50, IMGVIEW_propic, VB_label_chosebtn);
        VB_propic_label_chosebtn.setLayoutX(650);
        VB_propic_label_chosebtn.setLayoutY(80);
        VB_body.getChildren().addAll(HB_1, HB_2, HB_3, HB_4, HB_5, HB_6);
        pane.getChildren().addAll(VB_body, VB_radio_create, VB_propic_label_chosebtn);
        Scene scene = new Scene(pane, 950, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e
                -> {
            e.consume();
            AlertBox.alert(primaryStage);
        });

//ACTION HANDLER
        B_propic.setOnAction(ae -> {
//            JFileChooser
         /*   JFileChooser jfc = new JFileChooser();
            int dialog_value = jfc.showOpenDialog(null);
            if (dialog_value == JFileChooser.APPROVE_OPTION) {
                target_file = jfc.getSelectedFile();
                System.out.println(target_file.toURI().toString());
                Image IMG_propic_in_lambda = new Image(target_file.toURI().toString(), 150, 150, true, true, false);
                IMGVIEW_propic.setImage(IMG_propic_in_lambda);

            }
*/
     //file choser    
                FileChooser fc = new FileChooser();
                target_file = fc.showOpenDialog(this.primaryStage);
                System.out.println(target_file.toURI().toString());

            if(target_file.length()/1000>512){
                L_image_size.setText("Image size is huge, chose around 512 kb");
            }
            else {
                Image IMG_propic_in_lambda = new Image(target_file.toURI().toString(), 150, 150, true, true, false);
                IMGVIEW_propic.setImage(IMG_propic_in_lambda);
                L_image_size.setText("");
            }
        });
        B_create.setOnAction(ae
                -> {
            if (RB_agree.isSelected()==true && TF_username.getText().isEmpty() == false
                    && PF_password.getText().isEmpty() == false
                    && PF_repeatpassword.getText().isEmpty() == false) {
                if (PF_password.getText().equals(PF_repeatpassword.getText())) {
                    Pattern pattern = Pattern.compile("\\s");
                    Matcher matcher = pattern.matcher(TF_username.getText());
                    boolean found = matcher.find();
                    Matcher matcher2 = pattern.matcher(PF_password.getText());
                    boolean found2 = matcher.find();

                    if (found || found2) {
                        L_wrongInput.setText("UserName or password can't contain whitespaces");
                    } else {

                            NetworkConnection nc = new NetworkConnection(server_IP, 12345,L_wrongInput);
                            System.out.println("connection established after signUp in client");
                            nc.write(TF_username.getText() + " " + PF_password.getText() + " signup");
                            String str = (String) nc.read();
                            if (str.equals("false")) {
                                try {
                                    L_wrongInput.setText("Account Already Exists");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (str.equals("true")) {
            //;19.12.2016 
                         nc.write("profilepic");
                                if (((String) nc.read()).equals("sendprofilepic")) {
                                    System.out.println(" i am in sendprofilepic call");
                                    new SendFileClientpart(nc, target_file);
                                }
                                nc.write("wantTosendProfileData");
                                if((nc.read()).equals("sendProfileData")){
                                    System.out.println("I am after wntTosendProfileData");

                                    String first_name = TF_firstname.getText().trim();
                                    if(first_name.equals("")){
                                        first_name = "noteditted";
                                    }
                                    String last_name = TF_lastname.getText().trim();
                                    if(last_name.equals("")){
                                        last_name = "noteditted";
                                    }
                                    String email_address = TF_email.getText().trim();
                                    if(email_address.equals("")){
                                        email_address = "noteditted";
                                    }
                                    String country = TF_country.getText().trim();
                                    if(country.equals("")){
                                        country = "noteditted";
                                    }
                                    String date = TF_date.getText().trim();
                                    if(date.equals("")){
                                        date = "";
                                    }
                                    String month = TF_month.getText().trim();
                                    if(month.equals("")){
                                        month = "noteditted";
                                    }
                                    String year = TF_year.getText().trim();
                                    if(year.equals("")){
                                        year = "noteditted";
                                    }
                                    String phone_number = TF_phonenumber.getText().trim();
                                    if(phone_number.equals("")){
                                        phone_number = "noteditted";
                                    }
                                    String gender = TF_gender.getText().trim();
                                    if(gender.equals("")){
                                        gender = "noteditted";
                                    }

                                    nc.write(first_name + "@#" + last_name +"@#" + email_address + "@#" + country + "@#" + date + "@#" + month + "@#" + year + "@#" + phone_number + "@#" + gender);

                                }
                                L_wrongInput.setText("SignUp Successful");
                                Image image = new Image("/Client/welcome1.png");
                                Notifications notificationBuilder = Notifications.create()
                                        .title("Welocome to FACEBOOK")
//                    .text("It is free & always will be")
                                        .graphic(new ImageView(image))
                                        .hideAfter(Duration.seconds(5))
                                        .position(Pos.TOP_LEFT)
                                        .onAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                System.out.println("CLicked");
                                            }
                                        });
                                notificationBuilder.darkStyle();
                                notificationBuilder.show();
                                
                                new Home(nc,primaryStage,TF_username.getText());


//                                JFXDialogLayout dialogLayout = new JFXDialogLayout();
//                                dialogLayout.setHeading(new Text("Greetings From Facebook"));
//                                dialogLayout.setBody(new Text("Facebook embraces you"));
//                                JFXDialog dialog = new JFXDialog(pane, dialogLayout, JFXDialog.DialogTransition.CENTER);
//                                JFXButton button = new JFXButton("Okay");
//                                button.setOnAction(e1 ->
//                                {
//                                    dialog.close();
//                                });
//                                dialogLayout.setActions(button);
//                                dialog.show();
                            }


                    }

                } else {
                    L_wrongInput.setText("Password didn't match");
                }

            }


            else if(TF_username.getText().isEmpty() || PF_password.getText().isEmpty() || PF_repeatpassword.getText().isEmpty()) {
                L_wrongInput.setText("Please fill up all the field above");
            }
            else if(RB_agree.isSelected()==false)
                        L_wrongInput.setText("Please agree to the terms and conditions");

        }
        );
        JFXFillTransition transition1 = new JFXFillTransition(Duration.millis(5000),pane,Color.VIOLET,Color.INDIGO);
        JFXFillTransition transition2 = new JFXFillTransition(Duration.millis(5000),pane,Color.INDIGO,Color.BLUE);
        JFXFillTransition transition3 = new JFXFillTransition(Duration.millis(5000),pane,Color.BLUE,Color.ORANGE);
        JFXFillTransition transition4 = new JFXFillTransition(Duration.millis(5000),pane,Color.ORANGE,Color.VIOLET);

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
            transition1.play();
        });

        return true;
    }


}
