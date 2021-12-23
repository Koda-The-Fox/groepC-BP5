package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.control.ArduinoLocatieController;
import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.Gebruiker;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

import static com.waterkersapp.waterkersapp.MainWindow.ICON;

public class Menu {
    // Variables
    ///////////////////////[Window]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] WINDOW_SIZE = {750, 450}; // Default: 800 * 450;

    Color backgroundColor = Color.web("#BADC8F");

    ///////////////////////[Logo]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] ICON_SIZE = {50, 50}; //Default: 100;


    BorderPane borderPane = new BorderPane();
    //GridPane borderPane = new GridPane();

    private Gebruiker User = null;
    public static Stage stage;

    public static void create(Menu menu) {
        stage = new Stage();
        stage.setTitle("Menu");
        stage.getIcons().add(ICON);

        Scene scene = new Scene(menu.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        scene.getStylesheets().addAll(Menu.class.getResource("/com/waterkersapp/css/GlobalStyleSheet.css").toString());

        stage.setScene(scene);
        // set the window to be resizable
        stage.setResizable(true); // default: true

        stage.setOnCloseRequest(e -> stage.close());

        stage.initModality(Modality.APPLICATION_MODAL);



        stage.show();
        //stage.showAndWait(); // show and stay focussed on window
    }
    public Parent getParent() {
        return borderPane;
    }

    ComboBox<ArduinoLocatie> cbLocatie = new ComboBox<>();

    public Menu(Gebruiker user) {
        this.User = user;
        GridPane gridPane = new GridPane();
        VBox wrapperBox = new VBox();

        HBox logoBox = new HBox();
        logoBox.setAlignment(Pos.CENTER_LEFT);
        logoBox.setPadding(new Insets(0, 0, 10, -ICON_SIZE[1]));
        logoBox.setSpacing(5);

        // Set a value relative to the item width and height for the font size
        // source: https://easysavecode.com/66zhPWhD
        DoubleProperty fontSize = new SimpleDoubleProperty(24);
        fontSize.bind(gridPane.widthProperty().add(gridPane.heightProperty()).divide(50));

        ImageView imgLogo = new ImageView(ICON);
        imgLogo.setFitHeight(ICON_SIZE[0]);
        imgLogo.setFitWidth(ICON_SIZE[1]);
        logoBox.getChildren().add(imgLogo);

        Button btnLogout = new Button("Log-uit");
//        btnLogout.getStyleClass().add("global-button-style");
        btnLogout.styleProperty().bind(Bindings.concat( "-fx-font-size: ", fontSize.divide(2).asString(), ";"));
        btnLogout.setOnAction(event -> {
            stage.close();
            // optional Clear all data before actually changing windows;
            Login login = new Login();
            Login.create(login);
        });
        logoBox.getChildren().add(btnLogout);

        // get all devices and put them in the combobox
        logoBox.getChildren().add(cbLocatie);
        getArduinoLocaties();

        Button btnRefresh = new Button("\uD83D\uDD04");
        btnRefresh.setOnAction(event -> {
            getArduinoLocaties();
        });
        logoBox.getChildren().add(btnRefresh);

        Button btnNewDevice = new Button("Nieuw Aparaat");
        btnNewDevice.setOnAction(e -> {
            NewDeviceDial.create(null);
            getArduinoLocaties();
        });
        logoBox.getChildren().add(btnNewDevice);


        Button btnSensorOverzicht = new Button("Sensor Overzicht");
        btnSensorOverzicht.disableProperty().bind(cbLocatie.disableProperty());
        btnSensorOverzicht.styleProperty().bind(Bindings.concat( "-fx-font-size: ", fontSize.asString(), ";"));
        GridPane.setConstraints(btnSensorOverzicht, 1, 1); // node, column, row
        btnSensorOverzicht.setOnAction(event -> {

            // Debug easy programming to allow navigation
            // This action will need to add some data to get the right sensor values in the grid
            SensorOverview sensorOverGrTe = new SensorOverview(cbLocatie.getValue());
            SensorOverview.create(sensorOverGrTe);
            getArduinoLocaties();
        });

        Button btnSettings = new Button("Beheer/Instellingen");
        //btnSettings.disableProperty().bind(cbLocatie.disableProperty());
        btnSettings.styleProperty().bind(Bindings.concat( "-fx-font-size: ", fontSize.asString(), ";"));
        GridPane.setConstraints(btnSettings, 1, 2); // node, column, row
        btnSettings.setOnAction(event -> {
            Beheer beheer;
            if (cbLocatie.getValue().toString() == "Geen locaties"){
                // no clocations, open empty device
                beheer = new Beheer(new ArduinoLocatie(), user);
            }
            else {
                beheer = new Beheer(cbLocatie.getValue(), user);
            }
            stage.close();
            Beheer.create(beheer);
        });








        gridPane.getChildren().addAll( btnSensorOverzicht, btnSettings); // node, column, row
        gridPane.setVgap(10);
        gridPane.setHgap(60);

        gridPane.setPadding(new Insets(-gridPane.getVgap(),0,0, -gridPane.getHgap())); // negate the first VGap & Hgap.

        wrapperBox.getChildren().addAll(logoBox, gridPane);

        //wrapperBox.setAlignment(Pos.CENTER);
        wrapperBox.setPadding(new Insets(5, ICON_SIZE[1], 50, ICON_SIZE[1]));
        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty());

        // make the size of the gridPane relative to the wrapperBox size
        gridPane.prefWidthProperty().bind(wrapperBox.widthProperty());
        gridPane.prefHeightProperty().bind(wrapperBox.heightProperty());

        // make the size of the buttons relative to the gridPane size
        btnSensorOverzicht.prefWidthProperty().bind(gridPane.widthProperty());
        btnSensorOverzicht.prefHeightProperty().bind(gridPane.heightProperty());
        btnSettings.prefWidthProperty().bind(gridPane.widthProperty());
        btnSettings.prefHeightProperty().bind(gridPane.heightProperty());



        borderPane.setLeft(wrapperBox);
        borderPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
    }


    public void getArduinoLocaties(){
        ArduinoLocatie selected = cbLocatie.getSelectionModel().getSelectedItem();

        // Clear the list in case old values still exist
        cbLocatie.getItems().clear();
        // Load and add the items from the database
        cbLocatie.getItems().addAll(Objects.requireNonNull(ArduinoLocatieController.getAllArduinoLocaties(User, true)));

        // If there are no arduino's registered list a 'no devices' object
        if (cbLocatie.getItems().isEmpty()){
            cbLocatie.getItems().add(new ArduinoLocatie(0, "", "Geen locaties"));
            cbLocatie.setDisable(true);
            cbLocatie.getSelectionModel().select(0); // automatically select the first item
        }
        else {
            cbLocatie.setDisable(false);
            cbLocatie.getSelectionModel().select(0); // automatically select the first item
            if (selected != null)
                for (ArduinoLocatie item: cbLocatie.getItems()) {
                    if (selected.equals(item)){
                        cbLocatie.getSelectionModel().select(item);
                    }
                }

        }


    }
}
