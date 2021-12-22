package com.greengenie.green_genie.view;

import com.greengenie.green_genie.dial.InputDialog;
import com.greengenie.green_genie.model.Weather;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        /*
        contentGrid.setVgap(10);
        contentGrid.setHgap(60);

        contentGrid.setPadding(new Insets(-contentGrid.getVgap(),0,0, -contentGrid.getHgap())); // negate the first VGap & Hgap.
         */


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


        contentGrid.add(logoBox, 1, 1, 3, 1);

        try {
            Label lbltest = new Label(new Weather().getSimpleDirection(2, true));
            contentGrid.add(lbltest, 1, 2);
        } catch (Exception ex){
            ex.printStackTrace();
        }

        Button btnInputWeather = new Button("Weather");
        btnInputWeather.setOnAction(e ->{
            InputWeather.create(new InputWeather());
        });
        contentGrid.add(btnInputWeather, 1, 3);

        Label lbloutput = new Label("Dialog not openned yet.");
        contentGrid.add(lbloutput, 1, 5);
        Button btnInput = new Button("Dialog");
        btnInput.setOnAction(e ->{
            lbloutput.setText(InputDialog.HighMediumLow("Question?"));
        });

        contentGrid.add(btnInput, 1, 4);



        Button btnInputValues = new Button("Values");
        btnInputValues.setOnAction(e ->{
            InputValues.create(new InputValues());
        });
        contentGrid.add(btnInputValues, 1, 6);



        /////////////////////////////////////// [ending code] \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        wrapperBox.getChildren().addAll(logoBox, contentGrid);

        borderPane.setLeft(wrapperBox);
    }

}
