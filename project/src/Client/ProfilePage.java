/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import ClientFileOperation.RecieveFileClientPart;
import ClientFileOperation.SendFileClientpart;
import Util.NetworkConnection;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class ProfilePage {
    private NetworkConnection nc;
    private Stage primaryStage;
    private BorderPane borderPnae;
    private String clientName;
    ImageView IMGVIEW_coverphoto;
    public ProfilePage(Stage primaryStage, BorderPane borderPnae, String clientName, String client_data, NetworkConnection nc) {
        this.primaryStage = primaryStage;
        this.borderPnae = borderPnae;
        this.clientName = clientName;
        this.nc = nc;
        primaryStage.setTitle(clientName + "'s prrofilepage ");
 //Cover Photo
     //checking if client change his coverphoto
        nc.write("is_changed_coverphoto?");
        
        String command = (String)nc.read();
        if(command.equals("yes_coverphoto_changed")){
            new RecieveFileClientPart(nc, clientName, "changed_coverphoto");
            Image IMG_coverphoto = new Image ("file:ClientImages/coverphoto/" + clientName +".jpg", 500, 300, true, true, true);
            IMGVIEW_coverphoto = new ImageView(IMG_coverphoto);
        
        }
 
        else if(command.equals("no_coverphoto_not_changed"))
        {
            Image IMG_coverphoto = new Image ("file:ClientImages/samplecoverphoto/sample cover photo.jpg", 500, 400, true, true, true);
            IMGVIEW_coverphoto = new ImageView(IMG_coverphoto);

        }
 
 
                JFXButton B_change_cover_photo = new JFXButton("Change Cover Pitcture");
              
       
        B_change_cover_photo.setButtonType(JFXButton.ButtonType.RAISED);
        B_change_cover_photo.setRipplerFill(Color.WHITE);
        B_change_cover_photo.setTextFill(Color.GHOSTWHITE);
//        Button signUp = new Button("Sign Up");
        B_change_cover_photo.setStyle("-fx-background-color: #2196f3;-fx-font-weight: bold");
        B_change_cover_photo.setPadding(new Insets(10, 10, 10, 10));
//        Button B_change_profile_pic = new Button("Change Profile Pitcture");
       VBox VB_change_photos = new VBox(10, B_change_cover_photo);
        HBox HB_coverphoto = new HBox(IMGVIEW_coverphoto, VB_change_photos);
       B_change_cover_photo.setOnAction(ae->{
           changeYourCoverphoto();
           
       });
        
        
        
        
        
 //profile pic
        Image IMG_propic = new Image("file:ClientImages\\propic\\"+ clientName + ".jpg", 200, 300,true, true, false);
        ImageView IMGVIEW_propic = new ImageView(IMG_propic);
//client data
        String[] client_data_arr = client_data.split("@#");
        for(int i=0; i<9; i++){
            if(client_data_arr[i].equals("noteditted")){
                client_data_arr[i] = "";
            }
        }
        Label L_data= new Label ("Name: " + client_data_arr[0] + " "+client_data_arr[1] + "\n"
                +"Birth Date: "+client_data_arr[4] +"." +client_data_arr[5] + "." +client_data_arr[6] +"\n"
                +"Gender: " + client_data_arr[8] +"\n"
                +"Country: " + client_data_arr[3] +"\n"
                +"Email: " + client_data_arr[2] +"\n"
                +"Contact Number: "+ client_data_arr[7]
//0=firstname, 1 =lastname, 2= email, 3= country, 4=date, 5=month, 6=year, 7=phone, 8= gender

        );
        L_data.setStyle(

                    "-fx-text-fill: black;"
                    + "-fx-font-weight: bold;"
                    +"-fx-font-size: 25;"
                    +"-fx-font-color: black"

        );
        HBox HB_propic_data = new HBox(10, IMGVIEW_propic, L_data );

        VBox VB_client_profile = new VBox(5, HB_coverphoto, HB_propic_data);
        VB_client_profile.setStyle(
                "-fx-background-color: #53769b"
        );

// to create gap in right side
        VBox VB_right_gap = new VBox();
        VB_right_gap.setMaxWidth(300);
        VB_right_gap.setMinWidth(300);
 /*       
//to create gap in bottom side
        HBox HB_bottom_gap = new HBox();
        HB_bottom_gap.setMaxHeight(500);
        HB_bottom_gap.setMinHeight(500);
*/
        borderPnae.setCenter(VB_client_profile);
        borderPnae.setRight(VB_right_gap);
 //       borderPnae.setBottom(HB_bottom_gap);
        primaryStage.setFullScreen(true);
    
    }
    private void changeYourCoverphoto(){
       FileChooser fc = new FileChooser();
                File target_file = fc.showOpenDialog(this.primaryStage);
                System.out.println(target_file.toURI().toString());

            if(target_file.length()/1000>512){
                //L_image_size.setText("Image size is huge, chose around 512 kb");
                System.out.println("size huhe");
            }
            else {
                Image IMG_coverphoto = new Image(target_file.toURI().toString(), 150, 150, true, true, false);
                IMGVIEW_coverphoto.setImage(IMG_coverphoto);
                //L_image_size.setText("");
            }       
       
       
       
       
       
       //after chosing file this statement will execute
        nc.write("want_to_change_coverphoto");
           if(((String)nc.read()).equals("send_cover_photo")){
               new SendFileClientpart(nc, target_file);
           }

    }

}
