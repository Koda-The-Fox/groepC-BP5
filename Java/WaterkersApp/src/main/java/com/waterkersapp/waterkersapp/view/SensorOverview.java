package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.model.SensorReg;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

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

    private static String Sensor;

    public static Stage stage;

    public static void create(SensorOverview sensorOverview, String sensorTitle) {
        stage = new Stage();
        stage.setTitle(sensorTitle);
        Sensor = sensorTitle;
        stage.getIcons().add(ICON);

        Scene scene = new Scene(sensorOverview.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        scene.getStylesheets().addAll(SensorOverview.class.getResource("/com/waterkersapp/css/GlobalStyleSheet.css").toString(), SensorOverview.class.getResource("/com/waterkersapp/css/SensorOverviewStyleSheet.css").toString());
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


    public SensorOverview(String sensorTitle) {

        HBox logoTitleBox = new HBox();
        GridPane gp = new GridPane();

        ImageView imgLogo = new ImageView(ICON);
        imgLogo.setFitHeight(ICON_SIZE[0]);
        imgLogo.setFitWidth(ICON_SIZE[1]);

        Label title = new Label(sensorTitle);
        title.getStyleClass().add("title");
        title.setStyle("-fx-font-size:"+ICON_SIZE[1]/2.5+";");


        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER_LEFT);


        int columncount = 4;
        TableView tvContent = new TableView();
        TableColumn<SensorReg, String> tcKas = new TableColumn<>("Kas");
        tcKas.setCellValueFactory(new PropertyValueFactory<>("Kas"));
        tcKas.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount*2));
        TableColumn<SensorReg, Date> tcDate = new TableColumn<>("Datum");
        tcDate.setCellValueFactory(new PropertyValueFactory<>("Datum"));
        tcDate.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount));
        TableColumn<SensorReg, Time> tcTime = new TableColumn<>("Tijd");
        tcTime.setCellValueFactory(new PropertyValueFactory<>("Tijd"));
        tcTime.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount*2));
        TableColumn<SensorReg, Double> tcValue = new TableColumn<>("Waarde");
        tcValue.setCellValueFactory(new PropertyValueFactory<>("Waarde"));
        tcValue.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount/(2)));





        // set the table width and height dynamically
        tvContent.prefWidthProperty().bind(gp.widthProperty());
        tvContent.prefHeightProperty().bind(gp.heightProperty());

        tvContent.getColumns().addAll(tcKas, tcDate, tcTime, tcValue);

        System.out.println(new SensorReg("Kas 1", "2020-01-01", "09:30:00", 12));
        System.out.println(new SensorReg("Kas 1", "2020-02-01", "10:30:00", 15));
        System.out.println(new SensorReg("Kas 2", "2020-03-01", "11:30:00", 18));

        tvContent.getItems().addAll(
               new SensorReg("Kas 1", "2020-01-01", "09:30:00", 12),
               new SensorReg("Kas 1", "2020-02-01", "10:30:00", 15),
               new SensorReg("Kas 2", "2020-03-01", "11:30:00", 18)
        );


        GridPane.setConstraints(tvContent, 1, 3); // node, column, row
        gp.setPadding(new Insets(50));

        logoTitleBox.getChildren().addAll(imgLogo, titleBox);
        logoTitleBox.setSpacing(10);
        logoTitleBox.setAlignment(Pos.TOP_LEFT);


        gp.getChildren().addAll(tvContent);
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

        wrapperBox.setPadding(new Insets(10));
        wrapperBox.setSpacing(5);

        // set the size of the wrapperBox dynamically to the window(Stage)
        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty());

        // set the size of the grid pane dynamically to the wrapperBox
        gp.prefWidthProperty().bind(wrapperBox.widthProperty());
        gp.prefHeightProperty().bind(wrapperBox.heightProperty());

        borderPane.setLeft(wrapperBox);
        //borderPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
    }
}
