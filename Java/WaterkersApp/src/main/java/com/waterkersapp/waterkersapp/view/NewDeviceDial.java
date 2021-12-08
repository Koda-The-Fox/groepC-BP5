package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.control.ArduinoLocatieController;
import com.waterkersapp.waterkersapp.control.ChangePassController;
import com.waterkersapp.waterkersapp.control.ChangeUserController;
import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.Gebruiker;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.concurrent.atomic.AtomicReference;

import static com.waterkersapp.waterkersapp.MainWindow.ICON;



public class NewDeviceDial {

    private static ArduinoLocatie newDevice;

    public static void create(ArduinoLocatie al) {// Create the custom dialog.// Create the custom dialog.
        Dialog<Boolean> dialog = new Dialog<>();
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(ICON);

        // Set the title for the page.
        // if the variable ogUser == null the page is meant to create a user not edit one.
        dialog.setTitle("Gebruiker aanmaken.");
        dialog.setHeaderText("Gebruiker aanmaken.");
        if (al != null){
            dialog.setTitle("Aparaat bewerken.");
            dialog.setHeaderText("Gegevens veranderen voor device: " + al.getLocatie() + " (" + al.getArduinoID() + ")");
        }

        // Set the button types.
        ButtonType okButtonType;
        if (al != null) {
            okButtonType = new ButtonType("Opslaan", ButtonBar.ButtonData.OK_DONE);
        }else{
            okButtonType = new ButtonType("Aanmaken", ButtonBar.ButtonData.OK_DONE);
        }
        ButtonType CancelButtonType = new ButtonType("Annuleren", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, CancelButtonType);

        GridPane gp = new GridPane();
        VBox wrapperBox = new VBox();



        Text txtSystemMsg = new Text("");

        TextField tbxLocatieNaam = new TextField();
        if (al != null){
            tbxLocatieNaam.setText(al.getLocatie());
        }

//        tbxLocatieNaam.setDisable(true);
        gp.add(tbxLocatieNaam, 1, 1, 1, 1);
        /*
        Button btnCheckLock = new Button("Verander");
        btnCheckLock.setOnAction(e -> {
            if (btnCheckLock.getText().equals("Verander")){
                btnCheckLock.setText("Valideren");
                tbxLocatieNaam.setDisable(false);

            }
            else {
                if (ArduinoLocatieController.CheckDevicename(tbxLocatieNaam.getText())){
                    btnCheckLock.setText("Verander");
                tbxLocatieNaam.setDisable(true);
                }
                else {

                }

            }
        });
        gp.add(btnCheckLock, 2, 1, 1, 1);
        */




        txtSystemMsg.setFill(Color.RED);
        gp.add(txtSystemMsg, 1, 2, 2, 1);

        wrapperBox.getChildren().addAll(gp);

        wrapperBox.setSpacing(5);
        wrapperBox.setPadding(new Insets(20));

        dialog.getDialogPane().setContent(wrapperBox);

        AtomicReference<Pair<Boolean, String>> result = new AtomicReference<>(new Pair<>(false, "No result yet"));
        dialog.setResultConverter(dialogButton -> {
            txtSystemMsg.setText("");

            if (dialogButton == CancelButtonType) {
                result.set(new Pair<>(true, "Dialoog geannuleerd."));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }
            else if (tbxLocatieNaam.getText().isEmpty()){
                result.set(new Pair<>(false, "Gebruikersnaam mag niet leeg zijn."));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }


            newDevice = new ArduinoLocatie(tbxLocatieNaam.getText());
            if (al != null && !ArduinoLocatieController.CheckDevicename(newDevice.getLocatie())){
                result.set(new Pair<>(false, "Een aparaat met dezelfde naam bestaat al."));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }
            else {
                if (dialogButton == okButtonType) {
                    if (al == null) {
                        result.set(ArduinoLocatieController.CreateDevice(newDevice));
                    } else {
                        result.set(ArduinoLocatieController.ChangeDevice(al, newDevice));
                    }
                    txtSystemMsg.setText(result.get().getValue()); // set the error message to the text
                    return result.get().getKey(); // return the key which is the Boolean
                }
            }
            result.set(new Pair<>(false, "empty result"));
            return result.get().getKey(); // return the key which is the Boolean
        });


        dialog.setOnCloseRequest(e ->{
            if(!dialog.getResult()) {
                e.consume();
            }
        });

        dialog.showAndWait();
    }

}
