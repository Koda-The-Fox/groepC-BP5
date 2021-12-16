package com.greengenie.green_genie.view;

import com.greengenie.green_genie.model.Weather;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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

//import static com.greengenie.green_genie.MainWindow.ICON;

public class Menu {
    // Variables
    ///////////////////////[Window]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] WINDOW_SIZE = {750, 450}; // Default: 800 * 450;

    Color backgroundColor = Color.web("#BADC8F");

    ///////////////////////[Logo]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] ICON_SIZE = {50, 50}; //Default: 100;


    BorderPane borderPane = new BorderPane();

    public static Stage stage;

    public static void create(Menu menu) {
        stage = new Stage();
        stage.setTitle("Menu");
        //stage.getIcons().add(ICON);

        Scene scene = new Scene(menu.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        //scene.getStylesheets().addAll(Menu.class.getResource("/com/waterkersapp/css/GlobalStyleSheet.css").toString());

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

        HBox logoBox = new HBox();
        logoBox.setAlignment(Pos.CENTER_LEFT);
        logoBox.setPadding(new Insets(0, 0, 10, -ICON_SIZE[1]));
        logoBox.setSpacing(5);

        // Set a value relative to the item width and height for the font size
        // source: https://easysavecode.com/66zhPWhD
        DoubleProperty fontSize = new SimpleDoubleProperty(24);
        fontSize.bind(gridPane.widthProperty().add(gridPane.heightProperty()).divide(50));

        //ImageView imgLogo = new ImageView(ICON);
        //imgLogo.setFitHeight(ICON_SIZE[0]);
        //imgLogo.setFitWidth(ICON_SIZE[1]);
        //logoBox.getChildren().add(imgLogo);


        try {
            Label lbltest = new Label(new Weather(15.00, 2.00, 45.00, 360).getSimpleDirection(2, true));
            gridPane.add(lbltest, 1, 1);
        } catch (Exception ex){
            ex.printStackTrace();
        }


        gridPane.getChildren().addAll( ); // ADD the  elements
        gridPane.setVgap(10);
        gridPane.setHgap(60);

        gridPane.setPadding(new Insets(-gridPane.getVgap(),0,0, -gridPane.getHgap())); // negate the first VGap & Hgap.

        wrapperBox.getChildren().addAll(logoBox, gridPane);

        //wrapperBox.setAlignment(Pos.CENTER);
        wrapperBox.setPadding(new Insets(5, ICON_SIZE[1], 50, ICON_SIZE[1]));

        // set then size of the wrapperBox dynamically to the window(Stage)
        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty());

        // make the size of the gridPane relative to the wrapperBox size
        gridPane.prefWidthProperty().bind(wrapperBox.widthProperty());
        gridPane.prefHeightProperty().bind(wrapperBox.heightProperty());

        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty());


        borderPane.setLeft(wrapperBox);
        borderPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
    }

}
