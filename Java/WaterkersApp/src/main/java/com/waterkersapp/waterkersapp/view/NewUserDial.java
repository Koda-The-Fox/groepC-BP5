package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.model.Gebruiker;
<<<<<<< HEAD
import com.waterkersapp.waterkersapp.model.sensorRegistratie;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
=======
>>>>>>> parent of c409198 (Change new user dialog UI, Table Views functionality)
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

<<<<<<< HEAD
import javax.security.auth.callback.Callback;

import java.util.ArrayList;

=======
>>>>>>> parent of c409198 (Change new user dialog UI, Table Views functionality)
import static com.waterkersapp.waterkersapp.MainWindow.ICON;

public class NewUserDial {

<<<<<<< HEAD



    private static  final ComboBox<ArduinoLocatie> cbxDevAdd = new ComboBox<>();
    private static  final TableView tvDevices = new TableView();

    public static Boolean create(Gebruiker ogUser) {// Create the custom dialog.
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Gebruiker aanmaken.");
        dialog.setHeaderText("Gebruiker aanmaken.");
        if (ogUser != null){
            dialog.setTitle("Gebruiker '" + ogUser.getLoginNaam() + "' bewerken.");
            dialog.setHeaderText("Gegevens veranderen voor gebruiker: " + ogUser.getLoginNaam());
        }
=======
    // Variables
    ///////////////////////[Window]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] WINDOW_SIZE = {750, 450}; // Default: 800 * 450;
    Color backgroundColor = Color.web("#BADC8F");

    ///////////////////////[Logo]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] ICON_SIZE = {75, 75}; //Default: 100;





    BorderPane borderPane = new BorderPane();

    public static Stage stage;

    public static void create(NewUserDial newUser) {
        stage = new Stage();
        stage.setTitle("Beheer/Instellingen");
        stage.getIcons().add(ICON);
>>>>>>> parent of c409198 (Change new user dialog UI, Table Views functionality)

        Scene scene = new Scene(newUser.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        scene.getStylesheets().addAll(NewUserDial.class.getResource("/com/waterkersapp/css/GlobalStyleSheet.css").toString());
        stage.setScene(scene);
        // set the window to be resizable
        stage.setResizable(true); // default: true

        stage.setOnCloseRequest(e -> stage.close());

        stage.initModality(Modality.APPLICATION_MODAL);


//        stage.show();
        stage.showAndWait(); // show and stay focussed on window
    }
    public Parent getParent() {
        return borderPane;
    }


    /**
     * Leave ogUser null to create a new user.
     * @param ogUser The user to edit.
     */
    public NewUserDial(Gebruiker ogUser) {
        HBox titleBox = new HBox();
        GridPane gp = new GridPane();
        VBox wrapperBox = new VBox();

        // Set the title for the page.
        // if the variable ogUser == null the page is meant to create a user not edit one.
        Label lblTitle = new Label("Gebruiker aanmaken.");
        if (ogUser != null){
            lblTitle.setText("Gebruiker '" + ogUser.getLoginNaam() + "' bewerken.");
        }

        Label lblUsername = new Label("Gebruikersnaam: ");
        TextField tbxUsername = new TextField("");
        Button btnRstUsername = new Button("Reset");
        btnRstUsername.setOnAction(e -> {
            //@TODO Make the functionality to reset the text
        });
        Label lblPassword = new Label("Wachtwoord: ");
        Button btnChngePasword = new Button("Verander wachtwoord");
        btnChngePasword.setOnAction(e -> {
            //@TODO Direct tot the change password dialog
        });
<<<<<<< HEAD
        gp.add(lblPassword, 1, 2, 1, 1);

        if (ogUser == null){
            gp.add(tbxcrtePass, 2, 2, 1, 1);
        }
        else {
            gp.add(btnChngePasword, 2, 2, 1, 1);
        }
        gp.add(txtSystemMsgPssword, 1, 3, 3, 1);


        if (ogUser != null){
            tbxUsername.setText(ogUser.getLoginNaam());
        }

        // Devices

        int columncount = 3;
        Label lblTableDevices = new Label("Devices:");
        gp.add(lblTableDevices, 1, 4, 1, 1);

        int buttonWidth = 80; // default 80

        // Full list of all devices
        ArrayList<ArduinoLocatie> alAltDev = new ArrayList<>();
        ObservableList<ArduinoLocatie> olAltDev = FXCollections.observableArrayList();
        olAltDev.setAll(alAltDev);
        FilteredList<ArduinoLocatie> flAltDev = new FilteredList<>(olAltDev);
        // @TODO Get all the devices from the database and put them in 'flAltDev'

        ArrayList<ArduinoLocatie> alRegDev = new ArrayList<>();
        ObservableList<ArduinoLocatie> olRegDev = FXCollections.observableArrayList();
        olRegDev.setAll(alRegDev);
        FilteredList<ArduinoLocatie> flRegDev = new FilteredList<>(olRegDev);
        alRegDev.add(new ArduinoLocatie(1, "Locatie 1", "Defect")); // @TODO Remove when the TODO beneath is done. :3
        // @TODO remove the registered devices from 'flAltDev' and put them in 'flRegDev'

        refreshData(); // refresh the table after editing the list, (Delete, Add, Change) !!!!!Important!!!!!


        cbxDevAdd.setEditable(false);
        cbxDevAdd.setPrefWidth(buttonWidth);
        cbxDevAdd.setItems(flAltDev);
        gp.add(cbxDevAdd, 1, 5, 1, 1);


        Button btnAddDev = new Button("Toevoegen");
        btnAddDev.setPrefWidth(buttonWidth);
        btnAddDev.setOnAction(e -> {
            // @TODO Make an add device dialog or some other mechanic, remove the selected devices from 'flAltDev' and put them in 'flRegDev'
        });
        gp.add(btnAddDev, 1, 6, 1, 1);
        Button btnRemDev = new Button("Verwijderen");
        btnRemDev.setPrefWidth(buttonWidth);
        btnRemDev.setOnAction(e -> {
            // @TODO remove the selected device from 'flRegDev' and add it to 'flAltDev', if one is selected;
        });
        gp.add(btnRemDev, 1, 7, 1, 1);


        tvDevices.setPrefHeight(100);
        gp.add(tvDevices, 2, 4, 2, 5);

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
=======
        Button btnRstPasword = new Button("Reset");
        btnRstPasword.setOnAction(e -> {
            //@TODO Make the functionality to reset the text
        });


>>>>>>> parent of c409198 (Change new user dialog UI, Table Views functionality)


<<<<<<< HEAD

        tvDevices.setItems(flRegDev);
=======
>>>>>>> parent of c409198 (Change new user dialog UI, Table Views functionality)

        titleBox.getChildren().addAll(lblTitle);
        gp.getChildren().addAll();
        wrapperBox.getChildren().addAll(titleBox ,gp);

        wrapperBox.setSpacing(5);
        wrapperBox.setPadding(new Insets(20));

        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty());

        borderPane.setLeft(wrapperBox);
    }
<<<<<<< HEAD



    protected static void refreshData() {
        tvDevices.refresh();

    }

=======
>>>>>>> parent of c409198 (Change new user dialog UI, Table Views functionality)
}
