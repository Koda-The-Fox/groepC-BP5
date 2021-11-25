package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.control.ArduinoLocatieController;
import com.waterkersapp.waterkersapp.control.MinMaxWaardesController;
import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.MinMaxWaardes;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.Iterator;

import static com.waterkersapp.waterkersapp.MainWindow.ICON;

public class Beheer {
    // Variables
    ///////////////////////[Window]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] WINDOW_SIZE = {750, 450}; // Default: 800 * 450;
    Color backgroundColor = Color.web("#BADC8F");

    ///////////////////////[Logo]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] ICON_SIZE = {75, 75}; //Default: 100;





    BorderPane borderPane = new BorderPane();
    //GridPane borderPane = new GridPane();

    public static Stage stage;

    public static void create(Beheer beheer) {
        stage = new Stage();
        stage.setTitle("Beheer/Instellingen");
        stage.getIcons().add(ICON);

        Scene scene = new Scene(beheer.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        scene.getStylesheets().addAll(SensorOverview.class.getResource("/com/waterkersapp/css/GlobalStyleSheet.css").toString(), SensorOverview.class.getResource("/com/waterkersapp/css/BeheerStyle.css").toString());
        stage.setScene(scene);
        // set the window to be resizable
        stage.setResizable(true); // default: true

        stage.setOnCloseRequest(e -> stage.close());

        stage.initModality(Modality.APPLICATION_MODAL);


//        stage.show();
        stage.showAndWait(); // show and stay focussed on window
    }
    public Parent getParent() {
        return borderPane;
    }

    Spinner<Double> numMinPH;
    Spinner<Double> numMaxPH;
    Spinner<Double> numMinGT;
    Spinner<Double> numMaxGT;
    Spinner<Double> numMinGV;
    Spinner<Double> numMaxGV;
    Spinner<Double> numMinLT;
    Spinner<Double> numMaxLT;
    Spinner<Double> numMinLV;
    Spinner<Double> numMaxLV;

    public Beheer(ArduinoLocatie al) {
        if (al.equals(new ArduinoLocatie())){
            // AL is a new object so no device is selected
            // disable all buttons but the new device button
            // @TODO add a new device button and add the functionality
        }

        MinMaxWaardes currentWaardes = new MinMaxWaardes();

        HBox logoTitleBox = new HBox();
        GridPane gp = new GridPane();
        VBox wrapperBox = new VBox();
        ImageView imgLogo = new ImageView(ICON);
        Label title = new Label("Beheer/Instellingen");
        HBox titleBox = new HBox(title);

        Label lblLocatie = new Label("Locatie: " + al.toString());
        lblLocatie.setPadding(new Insets(0, 0, 0, ICON_SIZE[1]/2));

        imgLogo.setFitHeight(ICON_SIZE[0]);
        imgLogo.setFitWidth(ICON_SIZE[1]);

        title.getStyleClass().add("page_title");
        title.setStyle("-fx-font-size:"+ICON_SIZE[1]/2.5+";");

        titleBox.setAlignment(Pos.CENTER_LEFT);

        /*--------------[Content]---------------*/


        /*
        ComboBox<ArduinoLocatie> cbLocatie = new ComboBox<>();
        gp.add(cbLocatie, 2, 1, 2, 1); // node, column, row

        ArduinoLocatieController alController = new ArduinoLocatieController();
        //cbLocatie.getItems().addAll("Kas 1","Kas 2","Kas 3"); //@TODO Debug, remove values and load them from the database when possible
        cbLocatie.getItems().addAll(alController.getAllArduinoLocaties());
        if (!cbLocatie.getItems().isEmpty()){
            cbLocatie.getSelectionModel().select(0); // automatically select the first item @todo Make it so the uses selects an arduino in the top of the screen so all pages use that to view data
        }
        */


        Label lblWater = new Label("WATER");
        lblWater.getStyleClass().add("title");
        Label lblMinPH = new Label("Min-pH:");
        numMinPH = new Spinner<> ();
        numMinPH.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10000, currentWaardes.getMinPH())); //@TODO set to the correct Min/Max values
        numMinPH.setEditable(true);
        Button btnMinPH = new Button("Reset");
        Label lblMaxPH = new Label("Max-pH:");
        numMaxPH = new Spinner<> ();
        numMaxPH.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10000, currentWaardes.getMaxPH())); //@TODO set to the correct Min/Max values
        numMaxPH.setEditable(true);
        Button btnMaxPH = new Button("Reset");
        Label lblGrond = new Label("GROND");
        lblGrond.getStyleClass().add("title");
        Label lblMinGT = new Label("Min-grond temperatuur(°C):");
        numMinGT = new Spinner<> ();
        numMinGT.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-20, 40 ,15)); // Min temp source: https://www.howplantswork.com/2010/01/07/how-plants-survive-the-cold-or-not/ , Max temp source: https://www.americanmeadows.com/how-hot-is-too-hot-for-plants, init value source: https://www.houseplantsexpert.com/indoor-plants-temperature-guide.html
        numMinGT.setEditable(true);
        Button btnMinGT = new Button("Reset");
        Label lblMaxGT = new Label("Max-grond temperatuur(°C):");
        numMaxGT = new Spinner<> ();
        numMaxGT.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-20, 40, 24)); // Min temp source: https://www.howplantswork.com/2010/01/07/how-plants-survive-the-cold-or-not/ , Max temp source: https://www.americanmeadows.com/how-hot-is-too-hot-for-plants, init value source: https://www.houseplantsexpert.com/indoor-plants-temperature-guide.html
        numMaxGT.setEditable(true);
        Button btnMaxGT = new Button("Reset");
        Label lblMinGV = new Label("Min-grond vochtigheid(%):");
        numMinGV = new Spinner<> ();
        numMinGV.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 20)); // Moisture doesn't go below 0 or above 100 because it's a percentage. init value source: https://www.greenwaybiotech.com/blogs/gardening-articles/how-soil-moisture-affects-your-plants-growth
        numMinGV.setEditable(true);
        Button btnMinGV = new Button("Reset");
        Label lblMaxGV = new Label("Max-grond vochtigheid(%):");
        numMaxGV = new Spinner<> ();
        numMaxGV.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 60)); // Moisture doesn't go below 0 or above 100 because it's a percentage. init value source: https://www.greenwaybiotech.com/blogs/gardening-articles/how-soil-moisture-affects-your-plants-growth
        numMaxGV.setEditable(true);
        Button btnMaxGV = new Button("Reset");
        Label lblLucht = new Label("LUCHT");
        lblLucht.getStyleClass().add("title");
        Label lblMinLT = new Label("Min-Lucht temperatuur(°C):");
        numMinLT = new Spinner<> ();
        numMinLT.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-20, 40 ,15)); // Min temp source: https://www.howplantswork.com/2010/01/07/how-plants-survive-the-cold-or-not/ , Max temp source: https://www.americanmeadows.com/how-hot-is-too-hot-for-plants, init value source: https://www.houseplantsexpert.com/indoor-plants-temperature-guide.html
        numMinLT.setEditable(true);
        Button btnMinLT = new Button("Reset");
        Label lblMaxLT = new Label("Max-Lucht temperatuur(°C):");
        numMaxLT = new Spinner<> ();
        numMaxLT.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-20, 40, 24)); // Min temp source: https://www.howplantswork.com/2010/01/07/how-plants-survive-the-cold-or-not/ , Max temp source: https://www.americanmeadows.com/how-hot-is-too-hot-for-plants, init value source: https://www.houseplantsexpert.com/indoor-plants-temperature-guide.html
        numMaxLT.setEditable(true);
        Button btnMaxLT = new Button("Reset");
        Label lblMinLV = new Label("Min-Lucht vochtigheid(%):");
        numMinLV = new Spinner<> ();
        numMinLV.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 20)); // Moisture doesn't go below 0 or above 100 because it's a percentage. init value source: https://www.greenwaybiotech.com/blogs/gardening-articles/how-soil-moisture-affects-your-plants-growth
        numMinLV.setEditable(true);
        Button btnMinLV = new Button("Reset");
        Label lblMaxLV = new Label("Max-Lucht vochtigheid(%):");
        numMaxLV = new Spinner<> ();
        numMaxLV.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 60)); // Moisture doesn't go below 0 or above 100 because it's a percentage. init value source: https://www.greenwaybiotech.com/blogs/gardening-articles/how-soil-moisture-affects-your-plants-growth
        numMaxLV.setEditable(true);
        Button btnMaxLV = new Button("Reset");


        gp.add(lblWater, 1, 3, 2, 1);
        gp.add(lblMinPH, 1, 4);
        gp.add(numMinPH, 2, 4);
        gp.add(btnMinPH, 3, 4);
        gp.add(lblMaxPH, 1, 5);
        gp.add(numMaxPH, 2, 5);
        gp.add(btnMaxPH, 3, 5);

        gp.add(lblGrond, 1, 7, 2, 1);
        gp.add(lblMinGT, 1, 8);
        gp.add(numMinGT, 2, 8);
        gp.add(btnMinGT, 3, 8);
        gp.add(lblMaxGT, 1, 9);
        gp.add(numMaxGT, 2, 9);
        gp.add(btnMaxGT, 3, 9);
        gp.add(lblMinGV, 1, 10);
        gp.add(numMinGV, 2, 10);
        gp.add(btnMinGV, 3, 10);
        gp.add(lblMaxGV, 1, 11);
        gp.add(numMaxGV, 2, 11);
        gp.add(btnMaxGV, 3, 11);

        gp.add(lblLucht, 1, 13, 2, 1);
        gp.add(lblMinLT, 1, 14);
        gp.add(numMinLT, 2, 14);
        gp.add(btnMinLT, 3, 14);
        gp.add(lblMaxLT, 1, 15);
        gp.add(numMaxLT, 2, 15);
        gp.add(btnMaxLT, 3, 15);
        gp.add(lblMinLV, 1, 16);
        gp.add(numMinLV, 2, 16);
        gp.add(btnMinLV, 3, 16);
        gp.add(lblMaxLV, 1, 17);
        gp.add(numMaxLV, 2, 17);
        gp.add(btnMaxLV, 3, 17);


        logoTitleBox.getChildren().addAll(imgLogo, titleBox);
        logoTitleBox.setSpacing(10);
        logoTitleBox.setAlignment(Pos.TOP_LEFT);

//        gp.setGridLinesVisible(true);
        gp.setHgap(5);
        gp.setVgap(5);
        gp.prefWidthProperty().bind(wrapperBox.widthProperty());
//        gp.prefHeightProperty().bind(wrapperBox.heightProperty());

        Button btnSave = new Button("Opslaan");
        Button btnCancel = new Button("Afbreken");
        HBox b = new HBox( btnSave, btnCancel);
        b.setSpacing(5);
        VBox a = new VBox(gp ,b);
        a.setSpacing(40);

        // Functions
        /*
        cbLocatie.setOnAction( e -> {
            ArduinoLocatie al = cbLocatie.getValue(); // get the selected location
            LoadData(al);
        });

         */










        // Initialising
        ScrollPane contentWindow = new ScrollPane(a);
        contentWindow.setPadding(new Insets(10));

//        contentWindow.setVmax();
        contentWindow.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        contentWindow.getStyleClass().add("ContentWindow");


        HBox contentWrapper = new HBox(contentWindow);
        contentWrapper.setPadding(new Insets(0, ICON_SIZE[1]/2, 15, ICON_SIZE[1]/2));


        wrapperBox.getChildren().addAll(logoTitleBox, lblLocatie,contentWrapper);

        wrapperBox.setSpacing(5);
        wrapperBox.setPadding(new Insets(20));

        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty());

        borderPane.setLeft(wrapperBox);
    }

    private void LoadData(ArduinoLocatie al){
        MinMaxWaardesController mmController = new MinMaxWaardesController();
        MinMaxWaardes mmWaardes = mmController.getSpecificMinMaxWaardes(al);

        //@TODO Use listener instead so the value is dynamic to the object. in that case we need an 'originalObject' & 'currentObject' so we can revert the changes.

        numMinPH.getValueFactory().setValue(mmWaardes.getMinPH());
        numMaxPH.getValueFactory().setValue(mmWaardes.getMaxPH());

        numMinGT.getValueFactory().setValue(mmWaardes.getMinGT());
        numMaxGT.getValueFactory().setValue(mmWaardes.getMaxGT());
        numMinGV.getValueFactory().setValue(mmWaardes.getMinGV());
        numMaxGV.getValueFactory().setValue(mmWaardes.getMaxGV());

        numMinLT.getValueFactory().setValue(mmWaardes.getMinLT());
        numMaxLT.getValueFactory().setValue(mmWaardes.getMaxLT());
        numMinLV.getValueFactory().setValue(mmWaardes.getMinLV());
        numMaxLV.getValueFactory().setValue(mmWaardes.getMaxLV());
    }

}
