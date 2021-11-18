package com.waterkersapp.waterkersapp.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    public static Stage stage;

    public static void create(Menu menu) {
        stage = new Stage();
        stage.setTitle("Menu");
        stage.getIcons().add(ICON);

        Scene scene = new Scene(menu.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        scene.getStylesheets().addAll(SensorOverview.class.getResource("/com/waterkersapp/css/GlobalStyleSheet.css").toString());

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

    public Menu() {


        GridPane gridPane = new GridPane();
        VBox wrapperBox = new VBox();

        // Set a value relative to the item width and height for the font size
        // source: https://easysavecode.com/66zhPWhD
        DoubleProperty fontSize = new SimpleDoubleProperty(24);
        fontSize.bind(gridPane.widthProperty().add(gridPane.heightProperty()).divide(50));

        ImageView imgLogo = new ImageView(ICON);
        imgLogo.setFitHeight(ICON_SIZE[0]);
        imgLogo.setFitWidth(ICON_SIZE[1]);
        GridPane.setConstraints(imgLogo, 0, 0); // node, column, row

        Button btnLogin = new Button("Log-uit");
//        btnLogin.getStyleClass().add("global-button-style");
        btnLogin.styleProperty().bind(Bindings.concat( "-fx-font-size: ", fontSize.divide(2).asString(), ";"));
        btnLogin.setOnAction(event -> {
            stage.close();
            // optional Clear all data before actually changing windows;
            Login login = new Login();
            Login.create(login);
        });

        HBox logoBox = new HBox(imgLogo, btnLogin);
        logoBox.setPadding(new Insets(0, 0, 10, -ICON_SIZE[1]));



        Button btnSensorOverzicht = new Button("Sensor Overzicht");
        btnSensorOverzicht.styleProperty().bind(Bindings.concat( "-fx-font-size: ", fontSize.asString(), ";"));
        GridPane.setConstraints(btnSensorOverzicht, 1, 1); // node, column, row
        btnSensorOverzicht.setOnAction(event -> {

            // Debug easy programming to allow navigation
            // This action will need to add some data to get the right sensor values in the grid
            SensorOverview sensorOverGrTe = new SensorOverview("Sensor Overzicht");
            SensorOverview.create(sensorOverGrTe);
        });

        Button btnSettings = new Button("Beheer/Instellingen");
        btnSettings.styleProperty().bind(Bindings.concat( "-fx-font-size: ", fontSize.asString(), ";"));
        GridPane.setConstraints(btnSettings, 1, 2); // node, column, row
        btnSettings.setOnAction(event -> {

            Beheer beheer = new Beheer();
            Beheer.create(beheer);
        });



        gridPane.getChildren().addAll( btnSensorOverzicht, btnSettings); // node, column, row
        gridPane.setVgap(10);
        gridPane.setHgap(60);

        gridPane.setPadding(new Insets(-gridPane.getVgap(),0,0, -gridPane.getHgap())); // negate the first VGap & Hgap.

        wrapperBox.getChildren().addAll(logoBox, gridPane);

        //wrapperBox.setAlignment(Pos.CENTER);
        wrapperBox.setPadding(new Insets(0, ICON_SIZE[1], 50, ICON_SIZE[1]));

        // set then size of the wrapperBox dynamically to the window(Stage)
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

        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty());


        borderPane.setLeft(wrapperBox);
        borderPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
    }
}
