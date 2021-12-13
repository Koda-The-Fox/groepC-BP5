package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.control.MinMaxWaardesController;
import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.Gebruiker;
import com.waterkersapp.waterkersapp.model.MinMaxWaardes;
import javafx.geometry.Insets;
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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;

import static com.waterkersapp.waterkersapp.MainWindow.ICON;

public class Beheer {
    // Variables
    ///////////////////////[Window]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] WINDOW_SIZE = {750, 450}; // Default: 800 * 450;
    Color backgroundColor = Color.web("#BADC8F");

    ///////////////////////[Logo]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] ICON_SIZE = {75, 75}; //Default: 100;


    private static Gebruiker user;
    private Boolean CloseOverride = false;


    BorderPane borderPane = new BorderPane();

    public static Stage stage;
    public Stage stage2;

    public static void create(Beheer beheer, Gebruiker user) {
        stage = new Stage();
        stage.setTitle("Beheer/Instellingen");
        stage.getIcons().add(ICON);

        Scene scene = new Scene(beheer.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        scene.getStylesheets().addAll(Beheer.class.getResource("/com/waterkersapp/css/GlobalStyleSheet.css").toString(), SensorOverview.class.getResource("/com/waterkersapp/css/BeheerStyle.css").toString());
        stage.setScene(scene);
        // set the window to be resizable
        stage.setResizable(true); // default: true


        stage.initModality(Modality.APPLICATION_MODAL);

        // onClose event
        stage.setOnCloseRequest(e -> {
            if (!beheer.CloseOverride) {
                if (!beheer.getInsertinNewObject().equals(beheer.currentWaardes.get())){
                    Alert alrt = new Alert(Alert.AlertType.WARNING, "Er zijn veranderingen die niet zijn opgeslagen.\nWilt u doorgaan zonder op te slaan?", ButtonType.YES, ButtonType.NO);
                    alrt.showAndWait();
                    if (alrt.getResult() == ButtonType.NO){
                        e.consume();
                        return;
                    }
                }
            }
            Menu menu = new Menu(user);
            Menu.create(menu);
        });


//        stage.show();
        stage.showAndWait(); // show and stay focussed on window

    }

    public Parent getParent() {
        return borderPane;
    }

    ArduinoLocatie al;

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

    AtomicReference<MinMaxWaardes> currentWaardes;

    public Beheer(ArduinoLocatie al, Gebruiker user) {
        this.al = al;

        if (al.equals(new ArduinoLocatie())){
            // AL is a new object so no device is selected
            // disable all buttons but the new device button
            // @TODO add a new device button and add the functionality
            NewDeviceDial ndd = new NewDeviceDial();
        }

        currentWaardes = new AtomicReference<>(LoadData(al));

        HBox logoTitleBox = new HBox();
        GridPane gp = new GridPane();
        VBox wrapperBox = new VBox();
        ImageView imgLogo = new ImageView(ICON);
        Label title = new Label("Beheer/Instellingen");
        HBox titleBox = new HBox(title);


        imgLogo.setFitHeight(ICON_SIZE[0]);
        imgLogo.setFitWidth(ICON_SIZE[1]);

        title.getStyleClass().add("page_title");
        title.setStyle("-fx-font-size:"+ICON_SIZE[1]/2.5+";");

        titleBox.setAlignment(Pos.CENTER_LEFT);

        /*--------------[Content]---------------*/

        Label lblLocatieRO = new Label("Locatie: ");
        gp.add(lblLocatieRO, 1, 2, 1, 1);

        Label lblLocatie = new Label(currentWaardes.get().getLocatie().getLocatie());
        // @TODO FIX ERROR
        /*
         * It only happens with devices that are newly created, possibly because they don't have  Min/Max waardes.

            Exception in thread "JavaFX Application Thread" java.lang.NullPointerException
            at com.waterkersapp.waterkersapp/com.waterkersapp.waterkersapp.view.Beheer.<init>(Beheer.java:134)
            at com.waterkersapp.waterkersapp/com.waterkersapp.waterkersapp.view.Menu.lambda$new$5(Menu.java:142)
        */
        gp.add(lblLocatie, 2, 2, 1, 1);


        Label lblWater = new Label("WATER");
        lblWater.getStyleClass().add("title");
        Label lblMinPH = new Label("Min-pH:");
        numMinPH = new Spinner<> ();
        numMinPH.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10000, currentWaardes.get().getMinPH()));
        numMinPH.setEditable(true);
        Button btnMinPH = new Button("Reset");
        btnMinPH.setOnAction(e -> {
            numMinPH.getValueFactory().setValue(currentWaardes.get().getMinPH());
        });
        Label lblMaxPH = new Label("Max-pH:");
        numMaxPH = new Spinner<> ();
        numMaxPH.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10000, currentWaardes.get().getMaxPH()));
        numMaxPH.setEditable(true);
        Button btnMaxPH = new Button("Reset");
        btnMaxPH.setOnAction(e -> {
            numMaxPH.getValueFactory().setValue(currentWaardes.get().getMaxPH());
        });
        Label lblGrond = new Label("GROND");
        lblGrond.getStyleClass().add("title");
        Label lblMinGT = new Label("Min-grond temperatuur(°C):");
        numMinGT = new Spinner<> ();
        numMinGT.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-20, 40 , currentWaardes.get().getMinGT())); // Min temp source: https://www.howplantswork.com/2010/01/07/how-plants-survive-the-cold-or-not/ , Max temp source: https://www.americanmeadows.com/how-hot-is-too-hot-for-plants, init value source: https://www.houseplantsexpert.com/indoor-plants-temperature-guide.html
        numMinGT.setEditable(true);
        Button btnMinGT = new Button("Reset");
        btnMinGT.setOnAction(e -> {
            numMinGT.getValueFactory().setValue(currentWaardes.get().getMinGT());
        });
        Label lblMaxGT = new Label("Max-grond temperatuur(°C):");
        numMaxGT = new Spinner<> ();
        numMaxGT.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-20, 40, currentWaardes.get().getMaxGT())); // Min temp source: https://www.howplantswork.com/2010/01/07/how-plants-survive-the-cold-or-not/ , Max temp source: https://www.americanmeadows.com/how-hot-is-too-hot-for-plants, init value source: https://www.houseplantsexpert.com/indoor-plants-temperature-guide.html
        numMaxGT.setEditable(true);
        Button btnMaxGT = new Button("Reset");
        btnMaxGT.setOnAction(e -> {
            numMaxGT.getValueFactory().setValue(currentWaardes.get().getMaxGT());
        });
        Label lblMinGV = new Label("Min-grond vochtigheid(%):");
        numMinGV = new Spinner<> ();
        numMinGV.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, currentWaardes.get().getMinGV())); // Moisture doesn't go below 0 or above 100 because it's a percentage. init value source: https://www.greenwaybiotech.com/blogs/gardening-articles/how-soil-moisture-affects-your-plants-growth
        numMinGV.setEditable(true);
        Button btnMinGV = new Button("Reset");
        btnMinGV.setOnAction(e -> {
            numMinGV.getValueFactory().setValue(currentWaardes.get().getMinGV());
        });
        Label lblMaxGV = new Label("Max-grond vochtigheid(%):");
        numMaxGV = new Spinner<> ();
        numMaxGV.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, currentWaardes.get().getMaxGV())); // Moisture doesn't go below 0 or above 100 because it's a percentage. init value source: https://www.greenwaybiotech.com/blogs/gardening-articles/how-soil-moisture-affects-your-plants-growth
        numMaxGV.setEditable(true);
        Button btnMaxGV = new Button("Reset");
        btnMaxGV.setOnAction(e -> {
            numMinLT.getValueFactory().setValue(currentWaardes.get().getMaxGV());
        });
        Label lblLucht = new Label("LUCHT");
        lblLucht.getStyleClass().add("title");
        Label lblMinLT = new Label("Min-Lucht temperatuur(°C):");
        numMinLT = new Spinner<> ();
        numMinLT.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-20, 40 , currentWaardes.get().getMinLT())); // Min temp source: https://www.howplantswork.com/2010/01/07/how-plants-survive-the-cold-or-not/ , Max temp source: https://www.americanmeadows.com/how-hot-is-too-hot-for-plants, init value source: https://www.houseplantsexpert.com/indoor-plants-temperature-guide.html
        numMinLT.setEditable(true);
        Button btnMinLT = new Button("Reset");
        btnMinLT.setOnAction(e -> {
            numMinLT.getValueFactory().setValue(currentWaardes.get().getMinLT());
        });
        Label lblMaxLT = new Label("Max-Lucht temperatuur(°C):");
        numMaxLT = new Spinner<> ();
        numMaxLT.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-20, 40, currentWaardes.get().getMaxLT())); // Min temp source: https://www.howplantswork.com/2010/01/07/how-plants-survive-the-cold-or-not/ , Max temp source: https://www.americanmeadows.com/how-hot-is-too-hot-for-plants, init value source: https://www.houseplantsexpert.com/indoor-plants-temperature-guide.html
        numMaxLT.setEditable(true);
        Button btnMaxLT = new Button("Reset");
        btnMaxLT.setOnAction(e -> {
            numMaxLT.getValueFactory().setValue(currentWaardes.get().getMaxLT());
        });
        Label lblMinLV = new Label("Min-Lucht vochtigheid(%):");
        numMinLV = new Spinner<> ();
        numMinLV.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, currentWaardes.get().getMinLV())); // Moisture doesn't go below 0 or above 100 because it's a percentage. init value source: https://www.greenwaybiotech.com/blogs/gardening-articles/how-soil-moisture-affects-your-plants-growth
        numMinLV.setEditable(true);
        Button btnMinLV = new Button("Reset");
        btnMinLV.setOnAction(e -> {
            numMinLV.getValueFactory().setValue(currentWaardes.get().getMinLV());
        });
        Label lblMaxLV = new Label("Max-Lucht vochtigheid(%):");
        numMaxLV = new Spinner<> ();
        numMaxLV.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, currentWaardes.get().getMaxLV())); // Moisture doesn't go below 0 or above 100 because it's a percentage. init value source: https://www.greenwaybiotech.com/blogs/gardening-articles/how-soil-moisture-affects-your-plants-growth
        numMaxLV.setEditable(true);
        Button btnMaxLV = new Button("Reset");
        btnMaxLV.setOnAction(e -> {
            numMaxLV.getValueFactory().setValue(currentWaardes.get().getMaxLV());
        });


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

        gp.setHgap(5);
        gp.setVgap(5);
        gp.prefWidthProperty().bind(wrapperBox.widthProperty());


        Text txtSysMessage = new Text (""); // set a new line so the label is visible by using an empty line./
        txtSysMessage.setFill(Color.RED);

        Button btnSave = new Button("Opslaan");
        btnSave.setOnAction(e -> {
            MinMaxWaardes oldMinMax = currentWaardes.get();
            MinMaxWaardes newMinMax = getInsertinNewObject();
            if (!newMinMax.equals(oldMinMax)){
                MinMaxWaardesController MMC = new MinMaxWaardesController();
                if (MMC.updateMinMaxWaardes(oldMinMax, newMinMax)){
                    txtSysMessage.setFill(Color.GREEN);
                    txtSysMessage.setText("Succesvol opgeslagen, U kunt dit scherm afsluiten.");
                    currentWaardes.set(getInsertinNewObject());

                    // Close the window without checking for changes
                    CloseOverride = true;
                    Menu menu = new Menu(user);
                    Menu.create(menu);
                    stage.close();
                }
                else{
                    txtSysMessage.setFill(Color.RED);
                    txtSysMessage.setText("Er ging iets fout, controleer alle waardes of probeer het later nog eens. Als dit vaker voor komt neem contact op met de customer Support.");
                }
            }
            else{
                txtSysMessage.setFill(Color.YELLOW);
                txtSysMessage.setText("Geen veranderingen gevonden.");
            }
        });
        Button btnCancel = new Button("Afbreken");
        btnCancel.setOnAction(e ->{
            // Having trouble getting the onCloseRequest being triggered by this event.
            // Its taking too much time trying to fix this, doing it the ugly way. ¯\_(ツ)_/¯
            System.out.println("Close Requested");
            if (!CloseOverride) {
                if (!getInsertinNewObject().equals(currentWaardes.get())){
                    Alert alrt = new Alert(Alert.AlertType.WARNING, "Er zijn veranderingen die niet zijn opgeslagen.\nWilt u doorgaan zonder op te slaan?", ButtonType.YES, ButtonType.NO);
                    alrt.showAndWait();
                    if (alrt.getResult() == ButtonType.NO){
                        e.consume();
                        return;
                    }
                }
            }
            Menu menu = new Menu(user);
            Menu.create(menu);
            stage.close();
        });
        HBox b = new HBox( btnSave, btnCancel);
        b.setSpacing(5);
        VBox a = new VBox(gp, txtSysMessage, b);
        a.setSpacing(20);


        // Initialising
        ScrollPane contentWindow = new ScrollPane(a);
        contentWindow.setPadding(new Insets(10));

        contentWindow.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        contentWindow.getStyleClass().add("ContentWindow");


        HBox contentWrapper = new HBox(contentWindow);
        contentWrapper.setPadding(new Insets(0, ICON_SIZE[1]/2, 15, ICON_SIZE[1]/2));


        wrapperBox.getChildren().addAll(logoTitleBox /*, lblLocatie */,contentWrapper);

        wrapperBox.setSpacing(5);
        wrapperBox.setPadding(new Insets(20));

        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty());

        borderPane.setLeft(wrapperBox);

    }




    /**
     * Converts the current values in the spinners and text field into an object
     * @return
     */
    private MinMaxWaardes getInsertinNewObject(){
        return new MinMaxWaardes(
                al,

                numMinPH.getValue(),
                numMaxPH.getValue(),
                numMinGT.getValue(),
                numMaxGT.getValue(),
                numMinLT.getValue(),
                numMaxLT.getValue(),
                numMinGV.getValue(),
                numMaxGV.getValue(),
                numMinLV.getValue(),
                numMaxLV.getValue()
        );
    }

    private MinMaxWaardes LoadData(ArduinoLocatie al){
        MinMaxWaardesController mmController = new MinMaxWaardesController();
        return mmController.getSpecificMinMaxWaardes(al);
    }

}
