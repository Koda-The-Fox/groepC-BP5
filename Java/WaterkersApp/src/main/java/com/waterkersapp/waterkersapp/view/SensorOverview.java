package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.sensorRegistratie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.util.ArrayList;
import java.util.List;

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

    public static void create(SensorOverview sensorOverview) {
        stage = new Stage();
        stage.setTitle("Sensor Overzicht");
        stage.getIcons().add(ICON);

        Scene scene = new Scene(sensorOverview.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
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


    public SensorOverview(ArduinoLocatie al) {

        HBox logoTitleBox = new HBox();

        GridPane gp = new GridPane();

        ImageView imgLogo = new ImageView(ICON);
        imgLogo.setFitHeight(ICON_SIZE[0]);
        imgLogo.setFitWidth(ICON_SIZE[1]);

        Label tbxTitle = new Label("Sensor overview");
        tbxTitle.getStyleClass().add("page_title");
        tbxTitle.setStyle("-fx-font-size:"+ICON_SIZE[1]/2.5+";");

        HBox titleBox = new HBox(tbxTitle);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        Label lblLocatie = new Label("Locatie: " + al.toString());
        lblLocatie.setPadding(new Insets(0, 0, 0, ICON_SIZE[1]/2));
        lblLocatie.setAlignment(Pos.CENTER_RIGHT);

        int columncount = 7;
        TableView tvContent = new TableView();
        TableColumn<sensorRegistratie, Integer> tcKas = new TableColumn<>("Arduino");
        tcKas.setCellValueFactory(new PropertyValueFactory<>("Arduino"));
        tcKas.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount));
        TableColumn<sensorRegistratie, String> tcDate = new TableColumn<>("Datum & Tijd");
        tcDate.setCellValueFactory(new PropertyValueFactory<>("Datum"));
        tcDate.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount));

        // Water
        TableColumn<sensorRegistratie, String> tcWater = new TableColumn<>("Water");
        TableColumn<sensorRegistratie, Double> tcPhVal = new TableColumn<>("PHwaarde");
        tcPhVal.setCellValueFactory(new PropertyValueFactory<>("PHwaarde"));
        tcPhVal.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount));
        tcWater.getColumns().add(tcPhVal);
        // Grond
        TableColumn<sensorRegistratie, Double> tcGrond = new TableColumn<>("Grond");
        TableColumn<sensorRegistratie, Double> tcGrTmp = new TableColumn<>("GrondTemp");
        tcGrTmp.setCellValueFactory(new PropertyValueFactory<>("GrondTemp"));
        tcGrTmp.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount));
        TableColumn<sensorRegistratie, Double> tcGrVht = new TableColumn<>("GrondVocht");
        tcGrVht.setCellValueFactory(new PropertyValueFactory<>("GrondVocht"));
        tcGrVht.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount));
        tcGrond.getColumns().addAll(tcGrTmp, tcGrVht);
        // Lucht
        TableColumn<sensorRegistratie, Date> tcLucht = new TableColumn<>("Lucht");
        TableColumn<sensorRegistratie, Double> tcLuTmp = new TableColumn<>("LuchtTemp");
        tcLuTmp.setCellValueFactory(new PropertyValueFactory<>("LuchtTemp"));
        tcLuTmp.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount));
        TableColumn<sensorRegistratie, Double> tcLuVht = new TableColumn<>("LuchtVocht");
        tcLuVht.setCellValueFactory(new PropertyValueFactory<>("LuchtVocht"));
        tcLuVht.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount));
        tcLucht.getColumns().addAll(tcLuTmp, tcLuVht);




        // set the table width and height dynamically
        tvContent.prefWidthProperty().bind(gp.widthProperty());
//        tvContent.prefHeightProperty().bind(gp.heightProperty()); // GP height isn't dynamically sized
        tvContent.setMinHeight(100);

        tvContent.setPlaceholder(new Label("Geen data gevonden, probeer later nog eens."));

        tvContent.getColumns().addAll(tcKas, tcDate, tcWater, tcGrond, tcLucht);

        System.out.println(new sensorRegistratie(1, "2020-01-01", 5, 12, 9, 60, 50));
        System.out.println(new sensorRegistratie(2, "2020-02-01", 7, 15, 13, 75, 60));
        System.out.println(new sensorRegistratie(3, "2020-03-01", 8 ,18 ,16 ,85, 90));


        // @TODO Retrieve the data & make it in such a way that it loads in an observable list.
        /*
        tvContent.getItems().add( new sensorRegistratie(1, "2020-01-01", 5, 12, 9, 60, 50) );
        tvContent.getItems().add( new sensorRegistratie(2, "2020-02-01", 7, 15, 13, 75, 60) );
        tvContent.getItems().add( new sensorRegistratie(3, "2020-03-01", 8 ,18 ,16 ,85, 90) );

        tvContent.getItems().addAll(
               new SensorReg("Kas 1", "2020-01-01", "09:30:00", 12),
               new SensorReg("Kas 1", "2020-02-01", "10:30:00", 15),
               new SensorReg("Kas 2", "2020-03-01", "11:30:00", 18)
        );
         */


        GridPane.setConstraints(tvContent, 1, 3); // node, column, row

        logoTitleBox.getChildren().addAll(imgLogo, titleBox);
        logoTitleBox.setSpacing(10);
        logoTitleBox.setAlignment(Pos.TOP_LEFT);

        gp.getChildren().addAll(tvContent);

        ScrollPane contentWindow = new ScrollPane(gp);

        contentWindow.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        contentWindow.getStyleClass().add("ContentWindow");
        gp.setPadding(new Insets(50));

        HBox contentWrapper = new HBox(contentWindow);
        contentWrapper.setPadding(new Insets(0, ICON_SIZE[1]/2, 15, ICON_SIZE[1]/2));

        VBox wrapperBox = new VBox(logoTitleBox, lblLocatie,contentWrapper);

        wrapperBox.setSpacing(5);
//        wrapperBox.setPadding(new Insets(20));

        // set the size of the wrapperBox dynamically to the window(Stage)
        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty());
        wrapperBox.setPadding(new Insets(5, 0,0,0));


        // set the size of the grid pane dynamically to the wrapperBox
        contentWindow.prefWidthProperty().bind(contentWrapper.widthProperty());
        contentWindow.prefHeightProperty().bind(contentWrapper.heightProperty());

        // set the size of the grid pane dynamically to the wrapperBox
        contentWrapper.prefWidthProperty().bind(wrapperBox.widthProperty());
        contentWrapper.prefHeightProperty().bind(wrapperBox.heightProperty());

        // set the size of the grid pane dynamically to the wrapperBox
        gp.prefWidthProperty().bind(contentWindow.widthProperty());
//        gp.prefHeightProperty().bind(contentWindow.heightProperty());

        borderPane.setLeft(wrapperBox);
        //borderPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
    }
}
