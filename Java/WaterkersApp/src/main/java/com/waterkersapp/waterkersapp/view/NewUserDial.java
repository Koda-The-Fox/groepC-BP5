package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.control.ChangePassController;
import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.Gebruiker;
import com.waterkersapp.waterkersapp.model.sensorRegistratie;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.security.auth.callback.Callback;

import static com.waterkersapp.waterkersapp.MainWindow.ICON;

public class NewUserDial {

    public static Boolean create(Gebruiker ogUser) {// Create the custom dialog.
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Gebruiker aanmaken.");
        dialog.setHeaderText("Gebruiker aanmaken.");
        if (ogUser != null){
            dialog.setTitle("Gebruiker '" + ogUser.getLoginNaam() + "' bewerken.");
            dialog.setHeaderText("Gegevens veranderen voor gebruiker: " + ogUser.getLoginNaam());
        }


        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Opslaan", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, new ButtonType("Annuleren", ButtonBar.ButtonData.CANCEL_CLOSE));

        // Create the username and password labels and fields.

        GridPane gp = new GridPane();
        VBox wrapperBox = new VBox();

        // Set the title for the page.
        // if the variable ogUser == null the page is meant to create a user not edit one.

        Label lblUsername = new Label("Gebruikersnaam: ");
        TextField tbxUsername = new TextField("");
        Button btnRstUsern = new Button("Reset");
        btnRstUsern.setOnAction(e -> {
            if (ogUser != null){
                tbxUsername.setText(ogUser.getLoginNaam());
            }
            else{
                tbxUsername.setText("");
            }
        });
        gp.add(lblUsername, 1, 1, 1, 1);
        gp.add(tbxUsername, 2, 1, 1, 1);
        gp.add(btnRstUsern, 3, 1, 1, 1);
        Label lblPassword = new Label("Wachtwoord: ");
        Text txtSystemMsgPssword = new Text("");
        Button btnChngePasword = new Button("Wachtwoord veranderen");
        PasswordField tbxcrtePass = new PasswordField();
        btnChngePasword.setOnAction(e -> {
            if  (ChangePassDial.create(ogUser)){
                txtSystemMsgPssword.setFill(Color.GREEN);
                txtSystemMsgPssword.setText("Wachtwoord veraderen is gelukt.");
            }
            else{
                txtSystemMsgPssword.setFill(Color.RED);
                txtSystemMsgPssword.setText("Wachtwoord veraderen is mislukt of geannuleerd.");
            }
        });
        gp.add(lblPassword, 1, 2, 1, 1);

        if (ogUser == null){
            gp.add(tbxcrtePass, 2, 2, 1, 1);
        }
        else {
            gp.add(btnChngePasword, 2, 2, 1, 1);
        }
        gp.add(txtSystemMsgPssword, 1, 3, 3, 1);

        /* redundant, if not needed after a couple of days remove the code - 01-12-2021
        Button btnRstPasword = new Button("Reset");
        btnRstPasword.setOnAction(e -> {
            //@TODO remove code
        });
        */

        if (ogUser != null){
            tbxUsername.setText(ogUser.getLoginNaam());
        }

        // Devices

        int columncount = 3;
        Label lblTableDevices = new Label("Devices:");
        gp.add(lblTableDevices, 1, 4, 1, 1);

        int buttonWidth = 80; // default 80
        Button btnAddDev = new Button("Toevoegen");
        btnAddDev.setPrefWidth(buttonWidth);
        btnAddDev.setOnAction(e -> {
            // @TODO Make an add device dialog or some other mechanic;
        });
        gp.add(btnAddDev, 1, 5, 1, 1);
        Button btnRemDev = new Button("Verwijderen");
        btnRemDev.setPrefWidth(buttonWidth);
        btnRemDev.setOnAction(e -> {
            // @TODO remove the selected device, if one is selected;
        });
        gp.add(btnRemDev, 1, 6, 1, 1);


        TableView tvDevices = new TableView();
        tvDevices.setPrefHeight(100);
        gp.add(tvDevices, 2, 4, 2, 4);

        // ArduinoID
        TableColumn<ArduinoLocatie, Integer> tcID = new TableColumn<ArduinoLocatie, Integer>("ID");
        tcID.setCellValueFactory(new PropertyValueFactory<ArduinoLocatie, Integer>("arduinoID"));
        tcID.prefWidthProperty().bind(tvDevices.widthProperty().divide(columncount));


        // Locatie
        TableColumn<ArduinoLocatie, String> tcLocatie = new TableColumn<ArduinoLocatie, String>("Locatie");
        tcLocatie.setCellValueFactory(new PropertyValueFactory<ArduinoLocatie, String>("locatie"));
        tcLocatie.prefWidthProperty().bind(tvDevices.widthProperty().divide(columncount));
        // Status
        TableColumn<ArduinoLocatie, String> tcStatus = new TableColumn<ArduinoLocatie, String>("Status");
        tcStatus.setCellValueFactory(new PropertyValueFactory<ArduinoLocatie, String>("status"));
        tcStatus.prefWidthProperty().bind(tvDevices.widthProperty().divide(columncount));

        tvDevices.getColumns().addAll(tcID, tcLocatie, tcStatus);

        tvDevices.getItems().add(new ArduinoLocatie(1, "Locatie 1", "Defect"));


        // Password validation
        /*
        Label lblCurPass = new Label("Huidig wachtwoord: ");
        PasswordField tbxPassword = new PasswordField();
        HBox passwordBox = new HBox(lblCurPass, tbxPassword);
        wrapperBox.getChildren().addAll(gp, passwordBox);
         */

        wrapperBox.getChildren().addAll(gp);

        wrapperBox.setSpacing(5);
        wrapperBox.setPadding(new Insets(20));


        dialog.getDialogPane().setContent(wrapperBox);


        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                //return ChangePassController.ChangePassword(ogUser, tbxOldPass.getText(), tbxNewPass.getText());
            }
            return false;
        });

        dialog.showAndWait();

        return dialog.getResult();
    }



}
