package com.greengenie.green_genie.view;

import com.greengenie.green_genie.control.ArduinoLocatieController;
import com.greengenie.green_genie.dial.InputDialog;
import com.greengenie.green_genie.model.ArduinoLocatie;
import com.greengenie.green_genie.model.Weather;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Objects;

import static com.greengenie.green_genie.MainWindow.ICON;

public class Menu {
    // Variables
    ///////////////////////[Window]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] WINDOW_SIZE = {750, 450}; // Default: 800 * 450;

    ///////////////////////[Logo]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] ICON_SIZE = {50, 50}; //Default: 100;


    BorderPane borderPane = new BorderPane();
    ComboBox<ArduinoLocatie> cbLocatie = new ComboBox<>();

    public static Stage stage;

    public static void create(Menu menu) {
        stage = new Stage();
        stage.setTitle("Menu");
        stage.getIcons().add(ICON);

        Scene scene = new Scene(menu.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        scene.getStylesheets().addAll(Objects.requireNonNull(Menu.class.getResource("/com/greengenie/css/GlobalStyleSheet.css")).toString());

        stage.setScene(scene);
        // set the window to be resizable
        stage.setResizable(true); // default: true

        stage.setOnCloseRequest(e -> stage.close());

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();
    }
    public Parent getParent() {
        return borderPane;
    }

    public Menu() {
        VBox wrapperBox = new VBox();
        wrapperBox.setPadding(new Insets(5, ICON_SIZE[1], 50, ICON_SIZE[1]));
        // set the size of the wrapperBox dynamically to the window(Stage)
        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty());

        GridPane contentGrid = new GridPane();
        contentGrid.getStyleClass().add("Content_Window");
        // make the size of the contentGrid relative to the wrapperBox size
        contentGrid.prefWidthProperty().bind(wrapperBox.widthProperty());
        contentGrid.prefHeightProperty().bind(wrapperBox.heightProperty());
        contentGrid.setPadding(new Insets(10));

        HBox logoBox = new HBox();
        logoBox.setAlignment(Pos.CENTER_LEFT);
        logoBox.setPadding(new Insets(0, 0, 10, -ICON_SIZE[1]));
        logoBox.setSpacing(5);

        // Set a value relative to the item width and height for the font size
        // source: https://easysavecode.com/66zhPWhD
        DoubleProperty fontSize = new SimpleDoubleProperty(24);
        fontSize.bind(contentGrid.widthProperty().add(contentGrid.heightProperty()).divide(50));

        ImageView imgLogo = new ImageView(ICON);
        imgLogo.setFitHeight(ICON_SIZE[0]);
        imgLogo.setFitWidth(ICON_SIZE[1]);
        logoBox.getChildren().add(imgLogo);

        int ROW = 1;
        contentGrid.add(logoBox, 1, ROW, 3, 1);
        ROW++;


        contentGrid.add(cbLocatie, 1, ROW, 3, 1);
        ROW++;
        getArduinoLocaties();

        Label lblInputValuesErr = new Label();
        Button btnInputValues = new Button("Values");
        btnInputValues.setOnAction(e ->{
            if (cbLocatie.getSelectionModel().isEmpty())
                InputValues.create(new InputValues(null));
            else
                InputValues.create(new InputValues(cbLocatie.getValue()));
            stage.close();
        });
        contentGrid.add(btnInputValues, 1, ROW);
        ROW++;
        contentGrid.add(lblInputValuesErr, 1, ROW, 2, 1);
        ROW++;

        /////////////////////////////////////// [ending code] \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        wrapperBox.getChildren().addAll(logoBox, contentGrid);

        borderPane.setLeft(wrapperBox);
    }

    public void getArduinoLocaties(){
        ArduinoLocatie selected = cbLocatie.getSelectionModel().getSelectedItem();

        // Clear the list in case old values still exist
        cbLocatie.getItems().clear();
        // add empty row
        cbLocatie.getItems().add(new ArduinoLocatie(null, null, "EmptyObject"));
        // Load and add the items from the database
        cbLocatie.getItems().addAll(Objects.requireNonNull(ArduinoLocatieController.getAllArduinoLocaties()));

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
