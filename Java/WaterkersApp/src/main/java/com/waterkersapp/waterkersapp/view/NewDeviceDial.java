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

    private static ArduinoLocatie OgDevice;
    private static ArduinoLocatie newDevice;
    private static TTN_Info newTTNI = null;
    private static TTN_Info currentTTNI;

    /* REGULAR EXPRESSION */
    // Disallow: ';\n\r\t
    // Do not start or end with a space
    private static final Pattern negativeREGEXSQLInput = Pattern.compile("^((.*[';\n\r\t].*).)*$|^ .*$|^.* $");

    public static AtomicReference<Pair<Pair<Boolean, String>, ArduinoLocatie>> create(ArduinoLocatie OgDevice) {
        NewDeviceDial.OgDevice = OgDevice;
        Dialog<Boolean> dialog = new Dialog<>();
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(ICON);
        dialog.getDialogPane().getStylesheets().addAll(SensorOverview.class.getResource("/com/waterkersapp/css/GlobalStyleSheet.css").toString(), SensorOverview.class.getResource("/com/waterkersapp/css/TableStyle.css").toString());

        // Set the title for the page.
        // if the variable ogUser == null the page is meant to create a user not edit one.
        dialog.setTitle("Aparaat aanmaken.");
        dialog.setHeaderText("Aparaat aanmaken.");
        if (OgDevice != null){
            dialog.setTitle("Aparaat bewerken.");
            dialog.setHeaderText("Gegevens veranderen voor device: " + OgDevice.getLocatie() + " (" + OgDevice.getArduinoID() + ")");
        }

        // Set the button types.
        ButtonType okButtonType;
        if (OgDevice != null) {
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
        if (OgDevice != null){
            tbxLocatieNaam.setText(OgDevice.getLocatie());
        }
        gp.add(tbxLocatieNaam, 2, 1);

        if (OgDevice != null) {
            currentTTNI = (TTN_Info) TTNInfController.GetTTNInfo(OgDevice);
        }

        GridPane gpTTN = new GridPane();

        CheckBox chbxTTN = new CheckBox("Link TTN Applicatie");
        chbxTTN.setSelected(currentTTNI != null);
        chbxTTN.setOnAction(e -> {
            gpTTN.setDisable(!chbxTTN.isSelected());
        });

        //Override TTN is not allowed to be filled in the application rn
        //#TODO#5 (Optional) Enable after the MQTT app gets the data from the Database and writes it in the file.
        gpTTN.setDisable(true);
//        gpTTN.setDisable(!chbxTTN.isSelected());
//        gp.add(chbxTTN, 1, 2);
//        gp.add(gpTTN, 1, 3, 3, 1);


        int TTNInputLength = 300;

        if (currentTTNI == null){
            currentTTNI = new TTN_Info(OgDevice);
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

        if (OgDevice != null){
            // TTN Device Connection
            tbxDevID.setText(currentTTNI.getTTN_DeviceID());

            // TTN Connection
            tbxAppID.setText(currentTTNI.getTTN_ApplicationID());
            tbxConURL.setText(currentTTNI.getTTN_ConnectionURL());

            // login info
            tbxUser.setText(currentTTNI.getTTN_Username());
            tbxAPIPAss.setText(currentTTNI.getTTN_APIPassword());
        }

        Text txtSystemMsg = new Text("\n");
        txtSystemMsg.setFill(Color.RED);
        gp.add(txtSystemMsg, 1, 4, 2, 1);


        //Check validity
        String regexErr = "%s is niet toegestaan.\nDeze mag geen \\ ; of ' bevatten en niet beginnen of sluiten met een spatie.";
        tbxLocatieNaam.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSystemMsg.setText("\n");
            if (negativeREGEXSQLInput.matcher(tbxLocatieNaam.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "Locatienaam"));
                return;
            }
        });
        tbxDevID.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSystemMsg.setText("\n");
            if (negativeREGEXSQLInput.matcher(tbxDevID.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "Device ID"));
                return;
            }
        });
        tbxAppID.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSystemMsg.setText("\n");
            if (negativeREGEXSQLInput.matcher(tbxAppID.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "Applicatie ID"));
                return;
            }
        });
        tbxConURL.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSystemMsg.setText("\n");
            if (negativeREGEXSQLInput.matcher(tbxConURL.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "Connectie URL"));
                return;
            }
        });
        tbxUser.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSystemMsg.setText("\n");
            if (negativeREGEXSQLInput.matcher(tbxUser.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "Gebruikersnaam"));
                return;
            }
        });
        tbxAPIPAss.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSystemMsg.setText("\n");
            if (negativeREGEXSQLInput.matcher(tbxAPIPAss.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "API Wachtwoord"));
            }
        });

        wrapperBox.getChildren().addAll(gp);

        wrapperBox.setSpacing(5);
        wrapperBox.setPadding(new Insets(20));

        dialog.getDialogPane().setContent(wrapperBox);

        AtomicReference<Pair<Pair<Boolean, String>, ArduinoLocatie>> result = new AtomicReference<>(new Pair<>(new Pair<>(false, "No result yet"), newDevice));
        dialog.setResultConverter(dialogButton -> {
            txtSystemMsg.setText("\n");


            // Cancel button
            if (dialogButton == CancelButtonType) {
                result.set(new Pair<>(new Pair<>(true, "Dialoog geannuleerd.\n"), OgDevice));
                txtSystemMsg.setText(result.get().getKey().getValue());
                return result.get().getKey().getKey(); // return the key which is the Boolean
            }

            if (OgDevice == null) {
                newDevice = new ArduinoLocatie(tbxLocatieNaam.getText());
            }else {
                newDevice = new ArduinoLocatie(OgDevice.getArduinoID(), tbxLocatieNaam.getText());
                newTTNI = new TTN_Info(ArduinoLocatieController.getAllArduinoLocatie(newDevice.getLocatie()), tbxDevID.getText(), tbxAppID.getText(), tbxConURL.getText(), tbxUser.getText(), tbxAPIPAss.getText());
            }
            // Check name validity
            // tbxLocatieNaam
            if (negativeREGEXSQLInput.matcher(tbxLocatieNaam.getText()).matches()){
                result.set(new Pair<>(new Pair<>(false, String.format(regexErr, "Locatienaam")), OgDevice));
                txtSystemMsg.setText(result.get().getKey().getValue());
                return result.get().getKey().getKey(); // return the key which is the Boolean
            }
            // tbxDevID
            else if (chbxTTN.isSelected()&&negativeREGEXSQLInput.matcher(tbxDevID.getText()).matches()){
                result.set(new Pair<>(new Pair<>(false, String.format(regexErr, "Device ID")), OgDevice));
                txtSystemMsg.setText(result.get().getKey().getValue());
                return result.get().getKey().getKey(); // return the key which is the Boolean
            }
            // tbxAppID
            else if (chbxTTN.isSelected()&&negativeREGEXSQLInput.matcher(tbxAppID.getText()).matches()){
                result.set(new Pair<>(new Pair<>(false, String.format(regexErr, "Applicatie ID")), OgDevice));
                txtSystemMsg.setText(result.get().getKey().getValue());
                return result.get().getKey().getKey(); // return the key which is the Boolean
            }
            // tbxConURL
            else if (chbxTTN.isSelected()&&negativeREGEXSQLInput.matcher(tbxConURL.getText()).matches()){
                result.set(new Pair<>(new Pair<>(false, String.format(regexErr, "Connectie URL")), OgDevice));
                txtSystemMsg.setText(result.get().getKey().getValue());
                return result.get().getKey().getKey(); // return the key which is the Boolean
            }
            // tbxUser
            else if (chbxTTN.isSelected()&&negativeREGEXSQLInput.matcher(tbxUser.getText()).matches()){
                result.set(new Pair<>(new Pair<>(false, String.format(regexErr, "Gebruikersnaam")), OgDevice));
                txtSystemMsg.setText(result.get().getKey().getValue());
                return result.get().getKey().getKey(); // return the key which is the Boolean
            }
            // tbxAPIPAss
            else if (chbxTTN.isSelected()&&negativeREGEXSQLInput.matcher(tbxAPIPAss.getText()).matches()){
                result.set(new Pair<>(new Pair<>(false, String.format(regexErr, "API Wachtwoord")), OgDevice));
                txtSystemMsg.setText(result.get().getKey().getValue());
                return result.get().getKey().getKey(); // return the key which is the Boolean
            }

            // Detect empty fields
            else if (tbxLocatieNaam.getText().isEmpty()){
                result.set(new Pair<>(new Pair<>(false, "Gebruikersnaam mag niet leeg zijn.\n"), OgDevice));
                txtSystemMsg.setText(result.get().getKey().getValue());
                return result.get().getKey().getKey(); // return the key which is the Boolean
            }
            else if (chbxTTN.isSelected() && (tbxDevID.getText().isEmpty() || tbxAppID.getText().isEmpty() || tbxConURL.getText().isEmpty() || tbxUser.getText().isEmpty() || tbxAPIPAss.getText().isEmpty())){
                result.set(new Pair<>(new Pair<>(false, "Vul AUB alle data in voor de TTN Connectie.\n"), OgDevice));
                txtSystemMsg.setText(result.get().getKey().getValue());
                return result.get().getKey().getKey(); // return the key which is the Boolean
            }

            else if ((chbxTTN.isSelected() && (OgDevice != null && OgDevice.equals(newDevice)) && newTTNI.equals(currentTTNI)) || (!chbxTTN.isSelected() && (OgDevice != null && OgDevice.equals(newDevice)))){
                txtSystemMsg.setFill(Color.YELLOW);
                txtSystemMsg.setText("Geen veranderingen gevonden.\n");
            }

            // Check duplicate names
            else if (OgDevice == null && !ArduinoLocatieController.CheckDevicename(newDevice.getLocatie())){
                result.set(new Pair<>(new Pair<>(false, "Een aparaat met dezelfde naam bestaat OgDevice.\n"), OgDevice));
                txtSystemMsg.setText(result.get().getKey().getValue());
                return result.get().getKey().getKey(); // return the key which is the Boolean
            }
            else {
                if (dialogButton == okButtonType) {
                    if (OgDevice == null) {
                        result.set(new Pair<>(ArduinoLocatieController.CreateDevice(newDevice), newDevice));
                        if (result.get().getKey().getKey() && chbxTTN.isSelected()){

                            newTTNI = new TTN_Info(ArduinoLocatieController.getAllArduinoLocatie(newDevice.getLocatie()), tbxDevID.getText(), tbxAppID.getText(), tbxConURL.getText(), tbxUser.getText(), tbxAPIPAss.getText());

                            //newTTNI.getArduino().setLocatie(tbxLocatieNaam.getText()); // change the location.name to the new location otherwise it's the same as the old one


                            // We replace the result, because wel already established it was true
                            if (newTTNI.getArduino() == null) {
                                result.set(new Pair<>(new Pair<>(false, "Er ging iets fout met het opslaan van de TTN Informatie.\n"), OgDevice));
                            }
                            else {
                                result.set(new Pair<>(TTNInfController.CreateTTNI(newTTNI), newDevice));
                            }
                        }
                    } else {
                        result.set(new Pair<>(ArduinoLocatieController.ChangeDevice(OgDevice, newDevice), OgDevice));

                        newTTNI = new TTN_Info(ArduinoLocatieController.getAllArduinoLocatie(newDevice.getLocatie()), tbxDevID.getText(), tbxAppID.getText(), tbxConURL.getText(), tbxUser.getText(), tbxAPIPAss.getText());

                        if (result.get().getKey().getKey() && chbxTTN.isSelected()){
                            // We replace the result, because wel already established it was true
                            if (!currentTTNI.equals(newTTNI)) {
                                result.set(new Pair<>(TTNInfController.ChangeTTNI(currentTTNI, newTTNI), newDevice));
                            }
                        }
                    }
                    txtSystemMsg.setText(result.get().getKey().getValue()); // set the error message to the text
                    return result.get().getKey().getKey(); // return the key which is the Boolean
                }
            }
            result.set(new Pair<>(new Pair<>(false, "empty result\n"),OgDevice));
            return result.get().getKey().getKey(); // return the key which is the Boolean
        });

        dialog.setOnCloseRequest(e ->{
            if(!dialog.getResult()) {
                e.consume();
            }
        });

        dialog.showAndWait();

        return result;
    }

    /*
    private TTN_Info GenereateNewTTNI(){

    }
    */



}
