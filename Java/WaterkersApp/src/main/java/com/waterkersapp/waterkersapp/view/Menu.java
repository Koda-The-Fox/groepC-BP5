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

    ///////////////////////[Buttons]\\\\\\\\\\\\\\\\\\\\\
    // Font
    private static final double BTN_FNT_SIZE = 24; // default: 12
    private static final String BTN_FNT_WGHT = "bolder";
    private static final Color BTN_FNT_CLR = Color.web("#B9DB8E"); // RGB: 185, 219, 142
    // Border
    private static final Color BTN_BRDR_CLR = Color.web("#A16E58"); // RGB: 161, 110, 88
    private static final double BTN_BRDR_WDTH = 5;
    private static final double BTN_BRDR_RADIUS = 5;
    // Background
    private static final double BTN_BCGND_RADIUS = BTN_BRDR_RADIUS;
    private static final Color BTN_BCKG_CLR = Color.web("#92BA64"); // RGB: 146, 186, 100



    BorderPane borderPane = new BorderPane();
    //GridPane borderPane = new GridPane();

    public static Stage stage;

    public static void create(Menu menu) {
        stage = new Stage();
        stage.setTitle("Menu");
        stage.getIcons().add(ICON);

        stage.setScene(new Scene(menu.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1])));
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

        ImageView imgLogo = new ImageView(ICON);
        imgLogo.setFitHeight(ICON_SIZE[0]);
        imgLogo.setFitWidth(ICON_SIZE[1]);
        GridPane.setConstraints(imgLogo, 0, 0); // node, column, row

        HBox logoBox = new HBox(imgLogo);
        logoBox.setPadding(new Insets(0, 0, 10, -ICON_SIZE[1]));

        // Set a value relative to the item width and height for the font size
        // source: https://easysavecode.com/66zhPWhD
        DoubleProperty fontSize = new SimpleDoubleProperty(BTN_FNT_SIZE);
        fontSize.bind(gridPane.widthProperty().add(gridPane.heightProperty()).divide(50));

        String btnStyle =
                // Font
                // Font if dynamic so won't be set directly
                "-fx-font-weight:"+ BTN_FNT_WGHT +";"+
                "-fx-text-fill:#"+ Integer.toHexString(BTN_FNT_CLR.hashCode()) +";"+
                // Border
                "-fx-border-width:"+ BTN_BRDR_WDTH +";"+
                "-fx-border-radius:"+ BTN_BRDR_RADIUS +";"+
                "-fx-border-color:#"+ Integer.toHexString(BTN_BRDR_CLR.hashCode()) +";"+
                // Background
                "-fx-background-radius:"+BTN_BCGND_RADIUS+";"+
                "-fx-background-color:#"+ Integer.toHexString(BTN_BCKG_CLR.hashCode()) +";"//+
        ;

        Button btnGrndVcht = new Button("Grondvochtigheid");
        btnGrndVcht.styleProperty().bind(Bindings.concat( "-fx-font-size: ", fontSize.asString(), ";\n" + btnStyle));
        GridPane.setConstraints(btnGrndVcht, 1, 1); // node, column, row

        Button btnLchtTmep = new Button("Luchttemperatuur");
        btnLchtTmep.styleProperty().bind(Bindings.concat( "-fx-font-size: ", fontSize.asString(), ";\n" + btnStyle));
        GridPane.setConstraints(btnLchtTmep, 2, 1); // node, column, row

        Button btnLchtVcht = new Button("Luchtvochtigheid");
        btnLchtVcht.styleProperty().bind(Bindings.concat( "-fx-font-size: ", fontSize.asString(), ";\n" + btnStyle));
        GridPane.setConstraints(btnLchtVcht, 1, 2); // node, column, row

        Button btnpHWaarde = new Button("pH-Waarde");
        btnpHWaarde.styleProperty().bind(Bindings.concat( "-fx-font-size: ", fontSize.asString(), ";\n" + btnStyle));
        GridPane.setConstraints(btnpHWaarde, 2, 2); // node, column, row

        Button btnGrndTemp = new Button("Grondtemperatuur");
        btnGrndTemp.styleProperty().bind(Bindings.concat( "-fx-font-size: ", fontSize.asString(), ";\n" + btnStyle));
        GridPane.setConstraints(btnGrndTemp, 1, 3); // node, column, row

        Button btnSettings = new Button("Beheer/Instellingen");
        btnSettings.styleProperty().bind(Bindings.concat(btnStyle + "-fx-font-size: ", fontSize.asString(), ";\n" + btnStyle));
        GridPane.setConstraints(btnSettings, 2, 3); // node, column, row



        gridPane.getChildren().addAll( btnGrndVcht, btnLchtTmep, btnLchtVcht, btnpHWaarde, btnGrndTemp, btnSettings); // node, column, row
        gridPane.setVgap(10);
        gridPane.setHgap(60);

        // Debug, remove when not needed anymore
        gridPane.setStyle(
                "-fx-background-color:rgb(75,180,156, 0.7)"
        );
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
        btnGrndVcht.prefWidthProperty().bind(gridPane.widthProperty());
        btnGrndVcht.prefHeightProperty().bind(gridPane.heightProperty());
        btnLchtTmep.prefWidthProperty().bind(gridPane.widthProperty());
        btnLchtTmep.prefHeightProperty().bind(gridPane.heightProperty());
        btnLchtVcht.prefWidthProperty().bind(gridPane.widthProperty());
        btnLchtVcht.prefHeightProperty().bind(gridPane.heightProperty());
        btnpHWaarde.prefWidthProperty().bind(gridPane.widthProperty());
        btnpHWaarde.prefHeightProperty().bind(gridPane.heightProperty());
        btnGrndTemp.prefWidthProperty().bind(gridPane.widthProperty());
        btnGrndTemp.prefHeightProperty().bind(gridPane.heightProperty());
        btnSettings.prefWidthProperty().bind(gridPane.widthProperty());
        btnSettings.prefHeightProperty().bind(gridPane.heightProperty());

        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty());



        borderPane.setLeft(wrapperBox);
        borderPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
    }
}
