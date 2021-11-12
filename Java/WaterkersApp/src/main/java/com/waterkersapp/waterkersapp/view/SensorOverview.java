package com.waterkersapp.waterkersapp.view;

import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.waterkersapp.waterkersapp.MainWindow.ICON;

public class SensorOverview {
    // Variables
    ///////////////////////[Window]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] WINDOW_SIZE = {750, 450}; // Default: 800 * 450;
    Color backgroundColor = Color.web("#BADC8F");

    ///////////////////////[Logo]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] ICON_SIZE = {75, 75}; //Default: 100;





    BorderPane borderPane = new BorderPane();
    //GridPane borderPane = new GridPane();

    public static Stage stage;

    public static void create(SensorOverview sensorOverview, String sensor) {
        stage = new Stage();
        stage.setTitle(sensor);
        stage.getIcons().add(ICON);

        stage.setScene(new Scene(sensorOverview.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1])));
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


    public SensorOverview() {


        ImageView imgLogo = new ImageView(ICON);
        imgLogo.setFitHeight(ICON_SIZE[0]);
        imgLogo.setFitWidth(ICON_SIZE[1]);

        Label title = new Label("Test Title");
        title.setStyle(
                "-fx-font-size:"+ICON_SIZE[1]/2.5+";"+
                "-fx-font-weight:bolder;"+
                "-fx-text-fill:#7DB143;" // RGB: 125, 177, 67
        );
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER_LEFT);


        HBox logoTitleBox = new HBox(imgLogo, titleBox);
        logoTitleBox.setSpacing(10);
        logoTitleBox.setAlignment(Pos.TOP_LEFT);




        GridPane gp = new GridPane();
        gp.getChildren().addAll();
        gp.setStyle(
                "-fx-border-color:#977363;"+ // RGB: 151, 115, 99
                "-fx-border-width:5;"+
                "-fx-border-radius:5;"+
                "-fx-background-color:#92BA64;"+ // RGB: 146, 186, 100
                "-fx-background-radius:5;"
        );
        HBox content = new HBox(gp);
        content.setPadding(new Insets(0, ICON_SIZE[1]/2, 15, ICON_SIZE[1]/2));


        VBox wrapperBox = new VBox(logoTitleBox,content);

//        wrapperBox.setAlignment(Pos.TOP_LEFT);
        wrapperBox.setPadding(new Insets(10));
        wrapperBox.setSpacing(5);

        // set then size of the wrapperBox dynamically to the window(Stage)
        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty());

        // set then size of the grid pane dynamically to the wrapperBox
        gp.prefWidthProperty().bind(wrapperBox.widthProperty());
        gp.prefHeightProperty().bind(wrapperBox.heightProperty());

        borderPane.setLeft(wrapperBox);
        borderPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
    }
}
