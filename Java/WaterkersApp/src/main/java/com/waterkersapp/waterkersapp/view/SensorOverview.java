package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.control.RegistratieController;
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



    private final TableView tvContent = new TableView();

    BorderPane borderPane = new BorderPane();
    //GridPane borderPane = new GridPane();


    public static Stage stage;

    public static void create(SensorOverview sensorOverview) {
        stage = new Stage();
        stage.setTitle("Sensor Overzicht");
        stage.getIcons().add(ICON);

        Scene scene = new Scene(sensorOverview.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        scene.getStylesheets().addAll(SensorOverview.class.getResource("/com/waterkersapp/css/GlobalStyleSheet.css").toString(), SensorOverview.class.getResource("/com/waterkersapp/css/TableStyle.css").toString());
        stage.setScene(scene);
        // set the window to be resizable
        stage.setResizable(true); // default: true

        stage.setMinWidth(WINDOW_SIZE[0]);
        stage.setMinHeight(WINDOW_SIZE[1]);

        stage.setOnCloseRequest(e -> stage.close());

        stage.initModality(Modality.APPLICATION_MODAL);


        stage.show();
        //stage.showAndWait(); // show and stay focussed on window
    }
    public Parent getParent() {
        return borderPane;
    }


    ObservableList<sensorRegistratie> olSenReg;
    ArduinoLocatie currentAL;
    public SensorOverview(ArduinoLocatie al) {
        this.currentAL = al;

        HBox logoTitleBox = new HBox();

        GridPane gp = new GridPane();
        ScrollPane contentWindow = new ScrollPane();

        ImageView imgLogo = new ImageView(ICON);
        imgLogo.setFitHeight(ICON_SIZE[0]);
        imgLogo.setFitWidth(ICON_SIZE[1]);

        Label tbxTitle = new Label("Sensor overview");
        tbxTitle.getStyleClass().add("page_title");
        tbxTitle.setStyle("-fx-font-size:"+ICON_SIZE[1]/2.5+";");

        HBox titleBox = new HBox(tbxTitle);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        HBox systemBox = new HBox();
        Label lblLocatie = new Label("Locatie: " + al.toString());
        lblLocatie.setPadding(new Insets(0, 0, 0, ICON_SIZE[1]/2));

        Button btnRefresh = new Button("Ververs \uD83D\uDDD8");
        btnRefresh.setOnAction(e -> {
            refreshTable();
        });

        systemBox.getChildren().addAll(lblLocatie, btnRefresh);
        systemBox.setAlignment(Pos.BOTTOM_LEFT);
        systemBox.setSpacing(50);

        // Setup and add the columns to the table
        // Also use setCellValueFactory's to get the right value from the onject 'sensorRegistratie'.
        int columncount = 7; // the amount of columns spreads over the entire table.
        double first2ColumnsResize = 1.5;
        TableColumn<sensorRegistratie, String> tcKas = new TableColumn<sensorRegistratie, String>("Arduino");
        tcKas.setCellValueFactory(cellData -> cellData.getValue().getArduino().locatieProperty());
        tcKas.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount*first2ColumnsResize));
        tcKas.setResizable(false);
        TableColumn<sensorRegistratie, String> tcDate = new TableColumn<sensorRegistratie, String>("Datum & Tijd");
        tcDate.setCellValueFactory(cellData -> cellData.getValue().datumTijdProperty());
        tcDate.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount/first2ColumnsResize));
        tcDate.setResizable(false);

        // Water
        TableColumn<sensorRegistratie, String> tcWater = new TableColumn<sensorRegistratie, String>("Water");
        TableColumn<sensorRegistratie, Double> tcPhVal = new TableColumn<sensorRegistratie, Double>("PHwaarde");
        tcPhVal.setCellValueFactory(cellData -> cellData.getValue().PHwaardeProperty());
        tcPhVal.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount*1.25)); // 1.25 is the perfect value for 800x450 and 1920x1080 size
        tcPhVal.setResizable(false);
        tcWater.getColumns().add(tcPhVal);
        tcWater.setResizable(false);
        // Grond
        TableColumn<sensorRegistratie, Double> tcGrond = new TableColumn<sensorRegistratie, Double>("Grond");
        TableColumn<sensorRegistratie, Double> tcGrTmp = new TableColumn<sensorRegistratie, Double>("GrondTemp");
        tcGrTmp.setCellValueFactory(cellData -> cellData.getValue().grondTempProperty());
        tcGrTmp.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount));
        tcGrTmp.setResizable(false);
        TableColumn<sensorRegistratie, Double> tcGrVht = new TableColumn<sensorRegistratie, Double>("GrondVocht");
        tcGrVht.setCellValueFactory(cellData -> cellData.getValue().grondVochtProperty());
        tcGrVht.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount));
        tcGrVht.setResizable(false);
        tcGrond.getColumns().addAll(tcGrTmp, tcGrVht);
        tcGrond.setResizable(false);
        // Lucht
        TableColumn<sensorRegistratie, Double> tcLucht = new TableColumn<sensorRegistratie, Double>("Lucht");
        TableColumn<sensorRegistratie, Double> tcLuTmp = new TableColumn<sensorRegistratie, Double>("LuchtTemp");
        tcLuTmp.setCellValueFactory(cellData -> cellData.getValue().luchtTempProperty());
        tcLuTmp.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount));
        tcLuTmp.setResizable(false);
        TableColumn<sensorRegistratie, Double> tcLuVht = new TableColumn<sensorRegistratie, Double>("LuchtVocht");
        tcLuVht.setCellValueFactory(cellData -> cellData.getValue().luchtVochtProperty());
        tcLuVht.prefWidthProperty().bind(tvContent.widthProperty().divide(columncount));
        tcLuVht.setResizable(false);
        tcLucht.getColumns().addAll(tcLuTmp, tcLuVht);
        tcLucht.setResizable(false);

        // set the table width and height dynamically
        tvContent.prefWidthProperty().bind(gp.widthProperty());
        tvContent.prefHeightProperty().bind(contentWindow.heightProperty().subtract(12)); // 12 NO bar at all
        tvContent.setMinHeight(200);

        tvContent.setPlaceholder(new Label("Geen data gevonden, probeer later nog eens."));

        // Setup the table
        tvContent.getColumns().addAll(tcKas, tcDate, tcWater, tcGrond, tcLucht);

        // Setup the data lists for the table
        olSenReg = FXCollections.observableArrayList();
        FilteredList<sensorRegistratie> flSenReg = new FilteredList<>(olSenReg);
        // Fill the list with the data from the database
//        olSenReg.setAll(RegistratieController.GetRegFromDevice(al));

        tvContent.setItems(flSenReg);
        refreshTable(); // refresh the table after editing the list, (Delete, Add, Change) !!!!!Important!!!!!


        GridPane.setConstraints(tvContent, 1, 3); // node, column, row

        logoTitleBox.getChildren().addAll(imgLogo, titleBox);
        logoTitleBox.setSpacing(10);
        logoTitleBox.setAlignment(Pos.TOP_LEFT);

        gp.getChildren().addAll(tvContent);

        contentWindow.setContent(gp);

        contentWindow.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        contentWindow.getStyleClass().add("ContentWindow");
        gp.setPadding(new Insets(0,50,0,50));

        HBox contentWrapper = new HBox(contentWindow);
        contentWrapper.setPadding(new Insets(0, ICON_SIZE[1]/2, 15, ICON_SIZE[1]/2));

        VBox wrapperBox = new VBox(logoTitleBox, systemBox, contentWrapper);

        wrapperBox.setSpacing(5);
//        wrapperBox.setPadding(new Insets(20));

        // set the size of the wrapperBox dynamically to the window(Stage)
        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty());
        wrapperBox.setPadding(new Insets(5, 0,0,0));


        // set the size dynamically
        contentWindow.prefWidthProperty().bind(contentWrapper.widthProperty());
        contentWindow.prefHeightProperty().bind(contentWrapper.heightProperty());

        contentWrapper.prefWidthProperty().bind(wrapperBox.widthProperty());
        contentWrapper.prefHeightProperty().bind(wrapperBox.heightProperty());

        gp.prefWidthProperty().bind(contentWindow.widthProperty());
//        gp.prefHeightProperty().bind(contentWindow.heightProperty());



        borderPane.setLeft(wrapperBox);
        //borderPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
    }

    /**
     * Fill the item list and Reload the Table
     */
    protected void refreshTable() {
        olSenReg.setAll(RegistratieController.GetRegFromDevice(currentAL));
        tvContent.refresh();
    }
}
