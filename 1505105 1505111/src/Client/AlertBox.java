package Client;

import Util.NetworkConnection;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.Timer;

/**
 * Created by Utshaw on 11/25/2016.
 */
public class AlertBox {
    public static void alert(Stage window, NetworkConnection nc,String name)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit from Facebook");
        alert.setHeaderText("Do you really want to exit?");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            window.close();
            nc.write("signout");
            nc.write(name);
            nc.stopConnection();

        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }


    //Overloaded
    public static void alert(Stage window)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit from Facebook");
        alert.setHeaderText("Do you really want to exit?");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

            window.close();

        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }

    public static void alert(Stage window, NetworkConnection nc, String name, Timer timer) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quit from Facebook");
        alert.setHeaderText("Do you really want to exit?");
        alert.setContentText("Are you ok with this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            if(timer!=null)
            {
                timer.cancel();
                timer.purge();
            }
            Image image = new Image("/Client/thanks4.png",600,400,false,true,true);
            Notifications notificationBuilder = Notifications.create()
                    .title("Welocome to FACEBOOK")
//                    .text("It is free & always will be")
                    .graphic(new ImageView(image))
                    .hideAfter(Duration.seconds(2.5))
                    .position(Pos.BOTTOM_LEFT)
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("CLicked");
                        }
                    });
            notificationBuilder.darkStyle();
            notificationBuilder.show();
            PauseTransition delay = new PauseTransition(Duration.seconds(2.5));
            delay.setOnFinished( event -> window.close() );
            delay.play();
            nc.write("signout");
            nc.write(name);
            nc.stopConnection();

        } else {
            // ... user chose CANCEL or closed the dialog
        }
    }
}
