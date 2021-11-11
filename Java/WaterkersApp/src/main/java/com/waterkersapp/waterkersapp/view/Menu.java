package com.waterkersapp.waterkersapp.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
    private static final double BTN_FNT_SIZE = 24; // default: 14
    private static final String BTN_FNT_WGHT = "bolder";
    private static final Color BTN_FNT_CLR = Color.web("#B9DB8E"); // RGB: 185, 219, 142
    // Border
    private static final Color BTN_BRDR_CLR = Color.web("#A16E58"); // RGB: 161, 110, 88
    private static final double BTN_BRDR_WDTH = 5;
    private static final double BTN_BRDR_RADIUS = 5;
    // Background
    private static final double BTN_BCGND_RADIUS = BTN_BRDR_RADIUS;
    private static final Color BTN_BCKG_CLR = Color.web("#92BA64"); // RGB: 146, 186, 100
    // Geometry
    private static final double BTN_RADIUS = BTN_BRDR_RADIUS;
    private static final double[] BTN_SIZE = {300, 100}; // Width x Height



    BorderPane borderPane = new BorderPane();
    //GridPane borderPane = new GridPane();

    public static Stage stage;

    public static void create(Menu menu) {
        stage = new Stage();
        stage.setTitle("Menu");
        stage.getIcons().add(ICON);

        stage.setScene(new Scene(menu.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1])));
        // set the window to be resizable
        stage.setResizable(false); // default: true

        stage.setOnCloseRequest(e -> stage.close());

        stage.initModality(Modality.APPLICATION_MODAL);



        stage.show();
        //stage.showAndWait(); // show and stay focussed on window
    }
    public Parent getParent() {
        return borderPane;
    }

    public Menu() {




        ImageView imgLogo = new ImageView(ICON);
        imgLogo.setFitHeight(ICON_SIZE[0]);
        imgLogo.setFitWidth(ICON_SIZE[1]);
        GridPane.setConstraints(imgLogo, 0, 0); // node, column, row


        String btnStyle =
                // Font
                "-fx-font-size:"+ BTN_FNT_SIZE +";"+
                "-fx-font-weight:"+ BTN_FNT_WGHT +";"+
                "-fx-text-fill:#"+ Integer.toHexString(BTN_FNT_CLR.hashCode()) +";"+
                // Border
                "-fx-border-width:"+ BTN_BRDR_WDTH +";"+
                "-fx-border-radius:"+ BTN_BRDR_RADIUS +";"+
                "-fx-border-color:#"+ Integer.toHexString(BTN_BRDR_CLR.hashCode()) +";"+
                // Background
                "-fx-background-radius:"+BTN_RADIUS+";"+
                "-fx-background-color:#"+ Integer.toHexString(BTN_BCKG_CLR.hashCode()) +";"+
                // Geometry
                "-fx-min-width:"+BTN_SIZE[0]+";"+
                "-fx-min-height:"+BTN_SIZE[1]+";"+
                "-fx-max-width:"+BTN_SIZE[0]+";"+
                "-fx-max-height:"+BTN_SIZE[1]+";"
        ;

        Button btnGrndVcht = new Button("Grondvochtigheid");
        btnGrndVcht.setStyle(btnStyle);
        GridPane.setConstraints(btnGrndVcht, 1, 1); // node, column, row

        Button btnLchtTmep = new Button("Luchttemperatuur");
        btnLchtTmep.setStyle(btnStyle);
        GridPane.setConstraints(btnLchtTmep, 2, 1); // node, column, row

        Button btnLchtVcht = new Button("Luchtvochtigheid");
        btnLchtVcht.setStyle(btnStyle);
        GridPane.setConstraints(btnLchtVcht, 1, 2); // node, column, row

        Button btnpHWaarde = new Button("pH-Waarde");
        btnpHWaarde.setStyle(btnStyle);
        GridPane.setConstraints(btnpHWaarde, 2, 2); // node, column, row

        Button btnGrndTemp = new Button("Grondtemperatuur");
        btnGrndTemp.setStyle(btnStyle);
        GridPane.setConstraints(btnGrndTemp, 1, 3); // node, column, row

        Button btnSettings = new Button("Beheer/Instellingen");
        btnSettings.setStyle(btnStyle);
        GridPane.setConstraints(btnSettings, 2, 3); // node, column, row


        GridPane gridPane = new GridPane();

        gridPane.getChildren().addAll(imgLogo, btnGrndVcht, btnLchtTmep, btnLchtVcht, btnpHWaarde, btnGrndTemp, btnSettings); // node, column, row
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        HBox wrapperBox = new HBox(gridPane);

        wrapperBox.setAlignment(Pos.CENTER);


        borderPane.setLeft(wrapperBox);
        borderPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
        borderPane.setPadding(new Insets(15));
    }
}
