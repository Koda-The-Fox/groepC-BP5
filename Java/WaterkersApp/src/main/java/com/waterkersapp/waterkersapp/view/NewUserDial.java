package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.control.ArduinoLocatieController;
import com.waterkersapp.waterkersapp.control.BeheerdArduinoController;
import com.waterkersapp.waterkersapp.control.UserController;
import com.waterkersapp.waterkersapp.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

import static com.waterkersapp.waterkersapp.MainWindow.ICON;

public class NewUserDial {

    private static final ComboBox<ArduinoLocatie> cbxDevAdd = new ComboBox<>();
    private static Button btnAddDev = new Button("Toevoegen");
    private static Button btnRemDev = new Button("Verwijderen");

    private static ArrayList<ArduinoLocatie> alAltDev = new ArrayList<>();
    private static ObservableList<ArduinoLocatie> olAltDev;
    private static FilteredList<ArduinoLocatie> flAltDev;

    private static  final TableView<ArduinoLocatie> tvDevices = new TableView();
    private static ArrayList<ArduinoLocatie> alRegDev = new ArrayList<>();
    private static ObservableList<ArduinoLocatie> olRegDev;
    private static FilteredList<ArduinoLocatie> flRegDev;

    private static String ACTION = "maken";

    private static Gebruiker newUser;
    private static Gebruiker ogUser;

    /* REGULAR EXPRESSION */
    // Disallow: ';\n\r\t
    // Do not start or end with a space
    private static final Pattern negativeREGEXSQLInput = Pattern.compile("^((.*[';\n\r\t].*).)*$|^ .*$|^.* $");

    public static void create(Gebruiker ogUser, Gebruiker editor) {// Create the custom dialog.// Create the custom dialog.
        NewUserDial.ogUser = ogUser;

        Dialog<Boolean> dialog = new Dialog<>();
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(ICON);
        dialog.getDialogPane().getStylesheets().addAll(SensorOverview.class.getResource("/com/waterkersapp/css/GlobalStyleSheet.css").toString(), SensorOverview.class.getResource("/com/waterkersapp/css/TableStyle.css").toString());
//        dialog.getDialogPane().setMaxWidth(500);
//        dialog.getDialogPane().setMinWidth(500);


        // Set the title for the page.
        // if the variable ogUser == null the page is meant to create a user not edit one.
        dialog.setTitle("Gebruiker aanmaken.");
        dialog.setHeaderText("Gebruiker aanmaken.");
        if (ogUser != null){
            dialog.setTitle("Gebruiker '" + ogUser.getLoginNaam() + "' bewerken.");
            dialog.setHeaderText("Gegevens veranderen voor gebruiker: " + ogUser.getLoginNaam());
            ACTION = "bewerken";
        }


        // Set the button types.
        ButtonType okButtonType;
        if (ogUser != null) {
            okButtonType = new ButtonType("Opslaan", ButtonBar.ButtonData.OK_DONE);
        }else{
            okButtonType = new ButtonType("Aanmaken", ButtonBar.ButtonData.OK_DONE);
        }
        ButtonType CancelButtonType = new ButtonType("Annuleren", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, CancelButtonType);

        GridPane gp = new GridPane();
        gp.setVgap(5);
        VBox wrapperBox = new VBox();

        // Set the title for the page.
        // if the variable ogUser == null the page is meant to create a user not edit one.
        Label lblTitle = new Label("Gebruiker aanmaken.");
        if (ogUser != null){
            lblTitle.setText("Gebruiker '" + ogUser.getLoginNaam() + "' bewerken.");
        }

        Node ndeCngeUser = dialog.getDialogPane().lookupButton(okButtonType);
        ndeCngeUser.setDisable(true);


        Label lblUsername = new Label("Gebruikersnaam: ");
        gp.add(lblUsername, 1, 1, 1, 1);

        TextField tbxUsername = new TextField("");
        if (ogUser != null){
            tbxUsername.setText(ogUser.getLoginNaam());
            ndeCngeUser.setDisable(false);
        }
        tbxUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            if (tbxUsername.getText().isEmpty()){
                ndeCngeUser.setDisable(true);
            }
            else {
                ndeCngeUser.setDisable(false);
            }
        });
        gp.add(tbxUsername, 2, 1, 1, 1);
        Button btnRstUsername = new Button("Reset");
        btnRstUsername.setOnAction(e -> {
            if (ogUser != null){
                tbxUsername.setText(ogUser.getLoginNaam());
            }
            else {
                tbxUsername.setText("");
            }
        });
        gp.add(btnRstUsername, 3, 1, 1, 1);
        Label lblPassword = new Label("Wachtwoord: ");
        gp.add(lblPassword, 1, 2, 1, 1);
        Button btnChngePasword = new Button("Verander wachtwoord");

        Text txtSystemMsgPassword = new Text("");
        gp.add(txtSystemMsgPassword, 2, 3, 2, 1);
        btnChngePasword.setOnAction(e -> {
            txtSystemMsgPassword.setText("");
            if (ChangePassDial.create(ogUser)){
                txtSystemMsgPassword.setFill(Color.GREEN);
                txtSystemMsgPassword.setText("Wachtwoord " + ACTION + "  gelukt.");
            }
            else{
                txtSystemMsgPassword.setFill(Color.RED);
                txtSystemMsgPassword.setText("Wachtwoord " + ACTION + " mislukt of geannuleerd.");
            }
        });

        PasswordField tbxcrtePass = new PasswordField();
        if (ogUser == null){
            gp.add(tbxcrtePass, 2, 2, 1, 1);
        }
        else {
            gp.add(btnChngePasword, 2, 2, 1, 1);
        }



        // Devices

        int columncount = 3;
        Label lblTableDevices = new Label("Devices:");

        int buttonWidth = 100; // default 100

        loadResetData(); //

        cbxDevAdd.setEditable(false);
        cbxDevAdd.setPrefWidth(buttonWidth);


        // select the first item if  it exists
        if (cbxDevAdd.getItems().size() > 0)
            cbxDevAdd.getSelectionModel().select(0);


        btnAddDev.setPrefWidth(buttonWidth);
        btnAddDev.setOnAction(e -> {
            if (!cbxDevAdd.getSelectionModel().isEmpty()){
                olRegDev.add(cbxDevAdd.getSelectionModel().getSelectedItem());
                olAltDev.remove(cbxDevAdd.getSelectionModel().getSelectedItem());

                // select the first item if  it exists
                if (cbxDevAdd.getItems().size() > 0)
                    cbxDevAdd.getSelectionModel().select(0);
            }
            tvDevices.refresh(); // refresh the table after editing the list, (Delete, Add, Change) !!!!!Important!!!!!
        });

        btnRemDev.setPrefWidth(buttonWidth);
        btnRemDev.setOnAction(e -> {
            if (!tvDevices.getSelectionModel().isEmpty()){
                olAltDev.add(tvDevices.getSelectionModel().getSelectedItem());
                olRegDev.remove(tvDevices.getSelectionModel().getSelectedItem());

                // set the first item to be selected if it's the only item
                if (cbxDevAdd.getItems().size() > 0)
                    cbxDevAdd.getSelectionModel().select(0);
        }
            tvDevices.refresh(); // refresh the table after editing the list, (Delete, Add, Change) !!!!!Important!!!!!
        });



        // ArduinoID
        TableColumn<ArduinoLocatie, Integer> tcID = new TableColumn<ArduinoLocatie, Integer>("ID");
        tcID.setCellValueFactory(cellData -> cellData.getValue().arduinoIDProperty());
        tcID.prefWidthProperty().bind(tvDevices.widthProperty().divide(columncount));

        // Locatie
        TableColumn<ArduinoLocatie, String> tcLocatie = new TableColumn<ArduinoLocatie, String>("Locatie");
        tcLocatie.setCellValueFactory(cellData -> cellData.getValue().locatieProperty());
        tcLocatie.prefWidthProperty().bind(tvDevices.widthProperty().divide(columncount));
        // Status
        TableColumn<ArduinoLocatie, String> tcStatus = new TableColumn<ArduinoLocatie, String>("Status");
        tcStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        tcStatus.prefWidthProperty().bind(tvDevices.widthProperty().divide(columncount));

        tvDevices.setPlaceholder(new Label("Geen aparaten toegevoegd."));
        tvDevices.getColumns().clear();
        tvDevices.getColumns().addAll(tcID, tcLocatie, tcStatus);

        tvDevices.setPrefHeight(100);

        Label lblAdmin = new Label("Is admin: ");
        CheckBox chxAdmin = new CheckBox();
        if (ogUser != null)
            chxAdmin.setSelected(ogUser.getAdmin());

        chxAdmin.setOnAction(e -> {
            System.out.println();
            if (ogUser != null && ogUser.equals(editor)){
                if (!chxAdmin.isSelected()){
                    Alert alrt = new Alert(Alert.AlertType.WARNING,"Opgelet\nU staat op het punt uw admin rechten te verwijderen.\nDit betekent dat U zelf dit niet meer kan veranderen.", ButtonType.OK);
                    alrt.getDialogPane().getStylesheets().addAll(SensorOverview.class.getResource("/com/waterkersapp/css/GlobalStyleSheet.css").toString());
                    alrt.showAndWait();
                }
            }

            // Device assignment (dis/en)able
            loadResetData();
        });

        // Admin Tools
        if (editor.getAdmin()) {
            gp.add(lblTableDevices, 1, 4, 1, 1);
            gp.add(cbxDevAdd, 1, 5, 1, 1);
            gp.add(btnAddDev, 1, 6, 1, 1);
            gp.add(btnRemDev, 1, 7, 1, 1);
            gp.add(tvDevices, 2, 4, 2, 5);
            gp.add(lblAdmin, 1, 9, 1, 1);
            gp.add(chxAdmin, 2, 9, 1, 1);
        }

        PasswordField tbxCurPass = new PasswordField();
        gp.add(tbxCurPass, 2, 10, 1, 1);

        Text txtSystemMsg = new Text("\n\n");
        txtSystemMsg.setFill(Color.RED);
        gp.add(txtSystemMsg, 2, 11, 2, 1);

        // Check name validity
        String regexErr = "%s is niet toegestaan.\nDeze mag geen \\ ; of ' bevatten\nen niet beginnen of sluiten met een spatie.";
        tbxUsername.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSystemMsg.setText("\n");
            if (negativeREGEXSQLInput.matcher(tbxUsername.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "Gebruikersnaam"));
            }
        });
        tbxcrtePass.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSystemMsg.setText("\n");
            if (ogUser == null && negativeREGEXSQLInput.matcher(tbxcrtePass.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "Wachtwoord"));
            }
        });
        tbxCurPass.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSystemMsg.setText("\n");
            if (negativeREGEXSQLInput.matcher(tbxCurPass.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "Huidig wachtwoord"));
            }
        });


        wrapperBox.getChildren().addAll(gp);

        wrapperBox.setSpacing(5);
        wrapperBox.setPadding(new Insets(20));

        dialog.getDialogPane().setContent(wrapperBox);


        AtomicReference<Pair<Boolean, String>> result = new AtomicReference<>(new Pair<>(false, "No result yet\n"));
        dialog.setResultConverter(dialogButton -> {
            txtSystemMsg.setText("");

            newUser = new Gebruiker(tbxUsername.getText(), tbxcrtePass.getText(), chxAdmin.isSelected());



            if (dialogButton == CancelButtonType) {
                result.set(new Pair<>(true, "gebruiker " + ACTION + " geannuleerd.\n"));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }

            // Validation
            else if (negativeREGEXSQLInput.matcher(tbxUsername.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "Gebruikersnaam"));
            }
            else if (ogUser == null && negativeREGEXSQLInput.matcher(tbxcrtePass.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "Wachtwoord"));
            }
            else if (negativeREGEXSQLInput.matcher(tbxCurPass.getText()).matches()){
                txtSystemMsg.setText(String.format(regexErr, "Huidig wachtwoord"));
            }


            else if (tbxUsername.getText().isEmpty()){
                result.set(new Pair<>(false, "Gebruikersnaam mag niet leeg zijn.\n"));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }
            else if (ogUser == null && tbxcrtePass.getText().isEmpty()){
                result.set(new Pair<>(false, "Vul aub een wachtwoord in.\n"));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }
            else if (tbxCurPass.getText().isEmpty()){
                result.set(new Pair<>(false, "Vul aub het huidige wachtwoord in.\n"));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }
            else if (!UserController.CheckPassword(ogUser, tbxCurPass.getText())){
                result.set(new Pair<>(false, "Het ingevulde huidige wachtwoord is verkeerd.\n"));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }


            else if (ogUser == null && !UserController.CheckUsername(newUser.getLoginNaam())){
                result.set(new Pair<>(false, "Gebruikersnaam bestaat al.\n"));
                txtSystemMsg.setText(result.get().getValue());
                return result.get().getKey(); // return the key which is the Boolean
            }
            else {
                if (dialogButton == okButtonType) {
                    if (ogUser == null) {
                        result.set(UserController.CreateUser(newUser));
                    } else {
                        ogUser.setLoginPass(tbxCurPass.getText());
                        result.set(UserController.ChangeUser(ogUser, newUser));
                    }
                    if (result.get().getKey()){
                        result.set(BeheerdArduinoController.CreateBeheerdRecord(newUser, tvDevices.getItems()));
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


    protected static void loadResetData() {

        // Full list of all devices
        olAltDev = FXCollections.observableArrayList();
        olAltDev.addAll(Objects.requireNonNull(ArduinoLocatieController.getAllArduinoLocaties(null, false)));

        olRegDev = FXCollections.observableArrayList();

        if (ogUser != null) {
            for (ArduinoLocatie alReg : Objects.requireNonNull(ArduinoLocatieController.getAllArduinoLocaties(ogUser, false))) {
                for (ArduinoLocatie olAlt : olAltDev) {
                    if (alReg.getArduinoID().equals(olAlt.getArduinoID())) {
                        olRegDev.add(olAlt);
                    }
                }
            }
        }
        olAltDev.removeAll(olRegDev); // re move the already registered devices

        flAltDev = new FilteredList<>(olAltDev);
        flRegDev = new FilteredList<>(olRegDev);

        tvDevices.setItems(flRegDev);
        cbxDevAdd.setItems(flAltDev);

        tvDevices.refresh();

    }






}
