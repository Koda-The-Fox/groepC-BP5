package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.control.ArduinoLocatieController;
import com.waterkersapp.waterkersapp.control.TTNInfController;
import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.TTN_Info;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import static com.waterkersapp.waterkersapp.MainWindow.ICON;



public class NewDeviceDial {

    private static ArduinoLocatie newDevice;
    private static TTN_Info newTTNI = null;
    private static TTN_Info currentTTNI;

    /* REGULAR EXPRESSION */
    // Disallow: ';\n\r\t
    // Do not start or end with a space
    private static final Pattern negativeREGEXSQLInput = Pattern.compile("^((.*[';\n\r\t].*).)*$|^ .*$|^.* $");

    public static void create(ArduinoLocatie al) {
        Dialog<Boolean> dialog = new Dialog<>();
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(ICON);

        // Set the title for the page.
        // if the variable ogUser == null the page is meant to create a user not edit one.
        dialog.setTitle("Aparaat aanmaken.");
        dialog.setHeaderText("Aparaat aanmaken.");
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



        Label lblLocName = new Label("Locatie Naam: ");
        gp.add(lblLocName, 1, 1);

        TextField tbxLocatieNaam = new TextField();
        if (al != null){
            tbxLocatieNaam.setText(al.getLocatie());
        }
        gp.add(tbxLocatieNaam, 2, 1);

        if (al != null) {
            currentTTNI = (TTN_Info) TTNInfController.GetTTNInfo(al);
        }

        GridPane gpTTN = new GridPane();
        gpTTN.setDisable(currentTTNI == null); //@TODO Check if device has TTN Info on the database
        gp.add(gpTTN, 1, 3, 3, 1);

        CheckBox chbxTTN = new CheckBox("Link TTN Applicatie");
        chbxTTN.setSelected(currentTTNI != null);
        chbxTTN.setOnAction(e -> {
            gpTTN.setDisable(!chbxTTN.isSelected());
        });
        gp.add(chbxTTN, 1, 2);


        int TTNInputLength = 300;

        if (currentTTNI == null){
            currentTTNI = new TTN_Info(al);
        }

        // TTN Input fields
        Label lblDevID = new Label("DeviceID: ");
        gpTTN.add(lblDevID, 1,2);
        TextField tbxDevID = new TextField();
        tbxDevID.setPrefWidth(TTNInputLength);
        gpTTN.add(tbxDevID, 2,2, 3, 1);
        Label lblAppID = new Label("ApplicationID: ");
        gpTTN.add(lblAppID, 1,3);
        TextField tbxAppID = new TextField();
        tbxAppID.setPrefWidth(TTNInputLength);
        gpTTN.add(tbxAppID, 2,3, 3, 1);
        Label lblConURL = new Label("ConnectionURL: ");
        gpTTN.add(lblConURL, 1,4);
        TextField tbxConURL = new TextField();
        tbxConURL.setPrefWidth(TTNInputLength);
        gpTTN.add(tbxConURL, 2,4, 3, 1);
        Label lblUser = new Label("Username: ");
        gpTTN.add(lblUser, 1,5);
        TextField tbxUser = new TextField();
        tbxUser.setPrefWidth(TTNInputLength);
        gpTTN.add(tbxUser, 2,5, 3, 1);
        Label lblAPIPass= new Label("API Password: ");
        gpTTN.add(lblAPIPass, 1,6);
        PasswordField tbxAPIPAss = new PasswordField();
        tbxAPIPAss.setPrefWidth(TTNInputLength);
        tbxAPIPAss.getStyleClass().remove("password-field");
        tbxAPIPAss.getStyleClass().add("text-field");
        gpTTN.add(tbxAPIPAss, 2,6, 3, 1);

        if (al != null){
            // TTN Device Connection
            tbxDevID.setText(currentTTNI.getTTN_DeviceID());

            // TTN Connection
            tbxAppID.setText(currentTTNI.getTTN_ApplicationID());
            tbxConURL.setText(currentTTNI.getTTN_ConnectionURL());

            // login info
            tbxUser.setText(currentTTNI.getTTN_Username());
            tbxAPIPAss.setText(currentTTNI.getTTN_APIPassword());
        }

        Text txtSystemMsg = new Text("");
        txtSystemMsg.setFill(Color.RED);
        gp.add(txtSystemMsg, 1, 4, 2, 1);


        //Check validity
        String regexErr = "%s is niet toegestaan.\nDeze mag geen \\ ; of ' bevatten en niet beginnen of sluiten met een spatie.";
        tbxLocatieNaam.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSystemMsg.setText("\n");
            if (!negativeREGEXSQLInput.matcher(tbxLocatieNaam.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "Locatienaam"));
                return;
            }
        });
        tbxDevID.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSystemMsg.setText("\n");
            if (!negativeREGEXSQLInput.matcher(tbxDevID.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "Device ID"));
                return;
            }
        });
        tbxAppID.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSystemMsg.setText("\n");
            if (!negativeREGEXSQLInput.matcher(tbxAppID.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "Applicatie ID"));
                return;
            }
        });
        tbxConURL.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSystemMsg.setText("\n");
            if (!negativeREGEXSQLInput.matcher(tbxConURL.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "Connectie URL"));
                return;
            }
        });
        tbxUser.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSystemMsg.setText("\n");
            if (!negativeREGEXSQLInput.matcher(tbxUser.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "Gebruikersnaam"));
                return;
            }
        });
        tbxAPIPAss.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSystemMsg.setText("\n");
            if (!negativeREGEXSQLInput.matcher(tbxAPIPAss.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "API Wachtwoord"));
                return;
            }
        });





        wrapperBox.getChildren().addAll(gp);

        wrapperBox.setSpacing(5);
        wrapperBox.setPadding(new Insets(20));

        dialog.getDialogPane().setContent(wrapperBox);

        AtomicReference<Pair<Boolean, String>> result = new AtomicReference<>(new Pair<>(false, "No result yet"));
        dialog.setResultConverter(dialogButton -> {
            txtSystemMsg.setText("\n");

            if (al == null) {
                newDevice = new ArduinoLocatie(tbxLocatieNaam.getText());
            }else {
                newDevice = new ArduinoLocatie(al.getArduinoID(), tbxLocatieNaam.getText());
            }

            if (chbxTTN.isSelected()) {
                newTTNI = new TTN_Info(ArduinoLocatieController.getAllArduinoLocatie(al.getLocatie()), tbxDevID.getText(), tbxAppID.getText(), tbxConURL.getText(), tbxUser.getText(), tbxAPIPAss.getText());
                newTTNI.getArduino().setLocatie(tbxLocatieNaam.getText()); // change the location.name to the new location otherwise it's the same as the old one
            }

            // Check name validity
            // tbxLocatieNaam
            if (!negativeREGEXSQLInput.matcher(tbxLocatieNaam.getText()).matches()){
                result.set(new Pair<>(false, String.format(regexErr, "Locatienaam")));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }
            // tbxDevID
            else if (chbxTTN.isSelected()&&!negativeREGEXSQLInput.matcher(tbxDevID.getText()).matches()){
                result.set(new Pair<>(false, String.format(regexErr, "Device ID")));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }
            // tbxAppID
            else if (chbxTTN.isSelected()&&!negativeREGEXSQLInput.matcher(tbxAppID.getText()).matches()){
                result.set(new Pair<>(false, String.format(regexErr, "Applicatie ID")));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }
            // tbxConURL
            else if (chbxTTN.isSelected()&&!negativeREGEXSQLInput.matcher(tbxConURL.getText()).matches()){
                result.set(new Pair<>(false, String.format(regexErr, "Connectie URL")));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }
            // tbxUser
            else if (chbxTTN.isSelected()&&!negativeREGEXSQLInput.matcher(tbxUser.getText()).matches()){
                result.set(new Pair<>(false, String.format(regexErr, "Gebruikersnaam")));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }
            // tbxAPIPAss
            else if (chbxTTN.isSelected()&&!negativeREGEXSQLInput.matcher(tbxAPIPAss.getText()).matches()){
                result.set(new Pair<>(false, String.format(regexErr, "API Wachtwoord")));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }



            // Cancel button
            if (dialogButton == CancelButtonType) {
                result.set(new Pair<>(true, "Dialoog geannuleerd.\n"));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }

            // Detect empty fields
            else if (tbxLocatieNaam.getText().isEmpty()){
                result.set(new Pair<>(false, "Gebruikersnaam mag niet leeg zijn.\n"));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }
            else if (chbxTTN.isSelected() && (tbxDevID.getText().isEmpty() || tbxAppID.getText().isEmpty() || tbxConURL.getText().isEmpty() || tbxUser.getText().isEmpty() || tbxAPIPAss.getText().isEmpty())){
                result.set(new Pair<>(false, "Vul AUB alle data in voor de TTN Connectie.\n"));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }

            // Detect changes
            // @TODO Fix the if statement underneath, it doesn't trigger correctly
            else if (chbxTTN.isSelected() && (newTTNI != null && newTTNI.equals(currentTTNI))){
                    txtSystemMsg.setFill(Color.YELLOW);
                    txtSystemMsg.setText("Geen veranderingen gevonden.\n");
            }
            else if (al != null && al.equals(newDevice)) {
                    txtSystemMsg.setFill(Color.YELLOW);
                    txtSystemMsg.setText("Geen veranderingen gevonden.\n");
            }

            // Check duplicate names
            else if (al == null && !ArduinoLocatieController.CheckDevicename(newDevice.getLocatie())){
                result.set(new Pair<>(false, "Een aparaat met dezelfde naam bestaat al.\n"));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }
            else {
                if (dialogButton == okButtonType) {
                    if (al == null) {
                        result.set(ArduinoLocatieController.CreateDevice(newDevice));
                        if (chbxTTN.isSelected()) {
                            newTTNI.setArduino(ArduinoLocatieController.getAllArduinoLocatie(newDevice.getLocatie()));
                        }
                        if (result.get().getKey() && chbxTTN.isSelected()){
                            // We replace the result, because wel already established it was true
                            if (newTTNI.getArduino() == null) {
                                result.set(new Pair<>(false, "Er ging iets fout met het opslaan van de TTN Informatie.\n"));
                            }
                            else {
                                result.set(TTNInfController.CreateTTNI(newTTNI));
                            }
                        }
                    } else {
                        result.set(ArduinoLocatieController.ChangeDevice(al, newDevice));
                        if (result.get().getKey() && chbxTTN.isSelected()){
                            // We replace the result, because wel already established it was true
                            result.set(TTNInfController.ChangeTTNI(currentTTNI, newTTNI));
                        }
                    }
                    txtSystemMsg.setText(result.get().getValue()); // set the error message to the text
                    return result.get().getKey(); // return the key which is the Boolean
                }
            }
            result.set(new Pair<>(false, "empty result\n"));
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
