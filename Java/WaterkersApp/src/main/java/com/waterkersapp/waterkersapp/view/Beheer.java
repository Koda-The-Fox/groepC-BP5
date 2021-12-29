package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.control.MinMaxWaardesController;
import com.waterkersapp.waterkersapp.control.UserController;
import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.Gebruiker;
import com.waterkersapp.waterkersapp.model.MinMaxWaardes;
import javafx.animation.FadeTransition;
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
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.Objects;
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


    private BorderPane borderPane = new BorderPane();

    public static Stage stage;
    private Menu menu;

    public static void create(Beheer beheer) {
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
            Menu menu = new Menu(beheer.OgUser);
            Menu.create(menu);
        });


//        stage.show();
        stage.showAndWait(); // show and stay focussed on window

    }

    public Parent getParent() {
        return borderPane;
    }

    ArduinoLocatie OgDevice;

    Spinner<Double> numMinPH = new Spinner<> ();
    Spinner<Double> numMaxPH = new Spinner<> ();
    final Integer[] PHMargin = {1, 14}; // source: https://www.sciencefocus.com/science/could-a-ph-greater-than-14-exist/
    Spinner<Double> numMinGT = new Spinner<> ();
    Spinner<Double> numMaxGT = new Spinner<> ();
    Spinner<Double> numMinLT = new Spinner<> ();
    Spinner<Double> numMaxLT = new Spinner<> ();
    final Integer[] TempMargin = {-20, 40}; // Min temp source: https://www.howplantswork.com/2010/01/07/how-plants-survive-the-cold-or-not/ , Max temp source: https://www.americanmeadows.com/how-hot-is-too-hot-for-plants
    Spinner<Double> numMinGV = new Spinner<> ();
    Spinner<Double> numMaxGV = new Spinner<> ();
    Spinner<Double> numMinLV = new Spinner<> ();
    Spinner<Double> numMaxLV = new Spinner<> ();
    final Integer[] HumMargin = {0, 100}; // Moisture doesn't go below 0 or above 100 because it's a percentage.

    AtomicReference<MinMaxWaardes> currentWaardes;

    Text txtSysMessage = new Text ("");
    FadeTransition fadeout = new FadeTransition(Duration.seconds(5), txtSysMessage);

    ComboBox<Gebruiker> cbxUsers = new ComboBox<>();

    Gebruiker OgUser = null;
    Label lblLocatie = new Label();
    public Beheer(ArduinoLocatie OgDevice, Gebruiker user) {
        this.OgDevice = OgDevice;
        OgUser = user;

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

        Button btnEditDevice = new Button("Aparaat bewerken");
        btnEditDevice.setOnAction(e->{
            // @TODO ISSUE#11 Editing a device, Beheer/Instellingen doesn't update
            AtomicReference<Pair<Pair<Boolean, String>, ArduinoLocatie>> result = NewDeviceDial.create(OgDevice);
            this.OgDevice = result.get().getValue();
            LoadData(this.OgDevice);
        });

        Button btnEditUser = new Button("Gebruiker bewerken");
        btnEditUser.setOnAction(e->{
            AtomicReference<Pair<Pair<Boolean, String>, Gebruiker>> result = NewUserDial.create(cbxUsers.getValue(), user);
            OgUser = result.get().getValue();
            getUsers();
        });
        getUsers();

        Button btnCreateUser = new Button("Gebruiker aanmaken");
        btnCreateUser.setOnAction(e->{
            NewUserDial.create(null, user);
        });

        Label lblWater = new Label("WATER");
        lblWater.getStyleClass().add("title");
        Label lblMinPH = new Label("Min-pH:");
        numMinPH.setEditable(true);
        Button btnMinPH = new Button("Reset");
        btnMinPH.setOnAction(e -> {
            numMinPH.getValueFactory().setValue(currentWaardes.get().getMinPH());
        });
        Label lblMaxPH = new Label("Max-pH:");
        numMaxPH.setEditable(true);
        Button btnMaxPH = new Button("Reset");
        btnMaxPH.setOnAction(e -> {
            numMaxPH.getValueFactory().setValue(currentWaardes.get().getMaxPH());
        });
        Label lblGrond = new Label("GROND");
        lblGrond.getStyleClass().add("title");
        Label lblMinGT = new Label("Min-grond temperatuur(°C):");
        numMinGT.setEditable(true);
        Button btnMinGT = new Button("Reset");
        btnMinGT.setOnAction(e -> {
            numMinGT.getValueFactory().setValue(currentWaardes.get().getMinGT());
        });
        Label lblMaxGT = new Label("Max-grond temperatuur(°C):");
        numMaxGT.setEditable(true);
        Button btnMaxGT = new Button("Reset");
        btnMaxGT.setOnAction(e -> {
            numMaxGT.getValueFactory().setValue(currentWaardes.get().getMaxGT());
        });
        Label lblMinGV = new Label("Min-grond vochtigheid(%):");
        numMinGV.setEditable(true);
        Button btnMinGV = new Button("Reset");
        btnMinGV.setOnAction(e -> {
            numMinGV.getValueFactory().setValue(currentWaardes.get().getMinGV());
        });
        Label lblMaxGV = new Label("Max-grond vochtigheid(%):");
        numMaxGV.setEditable(true);
        Button btnMaxGV = new Button("Reset");
        btnMaxGV.setOnAction(e -> {
            numMinLT.getValueFactory().setValue(currentWaardes.get().getMaxGV());
        });
        Label lblLucht = new Label("LUCHT");
        lblLucht.getStyleClass().add("title");
        Label lblMinLT = new Label("Min-Lucht temperatuur(°C):");
        numMinLT.setEditable(true);
        Button btnMinLT = new Button("Reset");
        btnMinLT.setOnAction(e -> {
            numMinLT.getValueFactory().setValue(currentWaardes.get().getMinLT());
        });
        Label lblMaxLT = new Label("Max-Lucht temperatuur(°C):");
        numMaxLT.setEditable(true);
        Button btnMaxLT = new Button("Reset");
        btnMaxLT.setOnAction(e -> {
            numMaxLT.getValueFactory().setValue(currentWaardes.get().getMaxLT());
        });
        Label lblMinLV = new Label("Min-Lucht vochtigheid(%):");
        numMinLV.setEditable(true);
        Button btnMinLV = new Button("Reset");
        btnMinLV.setOnAction(e -> {
            numMinLV.getValueFactory().setValue(currentWaardes.get().getMinLV());
        });
        Label lblMaxLV = new Label("Max-Lucht vochtigheid(%):");

        numMaxLV.setEditable(true);
        Button btnMaxLV = new Button("Reset");
        btnMaxLV.setOnAction(e -> {
            numMaxLV.getValueFactory().setValue(currentWaardes.get().getMaxLV());
        });

        // get and load all the data
        LoadData(OgDevice);


        gp.add(btnEditUser, 1, 1);

        if (user.getAdmin()){
            gp.add(cbxUsers, 2, 1);
            gp.add(btnCreateUser, 3, 1);
        }

        if (OgDevice.getArduinoID() != 0) {

            gp.add(lblLocatieRO, 1, 2);

            gp.add(lblLocatie, 2, 2);

            gp.add(btnEditDevice, 1, 3);

            gp.add(lblWater, 1, 5, 2, 1);

            gp.add(lblMinPH, 1, 6);
            gp.add(numMinPH, 2, 6);
            gp.add(btnMinPH, 3, 6);

            gp.add(lblMaxPH, 1, 7);
            gp.add(numMaxPH, 2, 7);
            gp.add(btnMaxPH, 3, 7);

            gp.add(lblGrond, 1, 8, 2, 1);

            gp.add(lblMinGT, 1, 9);
            gp.add(numMinGT, 2, 9);
            gp.add(btnMinGT, 3, 9);

            gp.add(lblMaxGT, 1, 10);
            gp.add(numMaxGT, 2, 10);
            gp.add(btnMaxGT, 3, 10);

            gp.add(lblMinGV, 1, 11);
            gp.add(numMinGV, 2, 11);
            gp.add(btnMinGV, 3, 11);

            gp.add(lblMaxGV, 1, 12);
            gp.add(numMaxGV, 2, 12);
            gp.add(btnMaxGV, 3, 12);

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
        }
        else{
            CloseOverride = true;
        }


        logoTitleBox.getChildren().addAll(imgLogo, titleBox);
        logoTitleBox.setSpacing(10);
        logoTitleBox.setAlignment(Pos.TOP_LEFT);

        gp.setHgap(5);
        gp.setVgap(5);
        gp.prefWidthProperty().bind(wrapperBox.widthProperty());

        txtSysMessage.setFill(Color.RED);
        fadeout.setFromValue(1.0);
        fadeout.setToValue(0.0);
        fadeout.setOnFinished(e ->{
            txtSysMessage.setFill(Color.RED);
            txtSysMessage.setText("");
        });


        Button btnSave = new Button("Opslaan");
        btnSave.setOnAction(e -> {
            txtSysMessage.setText("");
            MinMaxWaardes oldMinMax = currentWaardes.get();
            MinMaxWaardes newMinMax = getInsertinNewObject();
            if (OgDevice.getArduinoID() != 0){
                if (oldMinMax.getFromDB()) {
                    if (!newMinMax.equals(oldMinMax)) {
                        if (MinMaxWaardesController.updateMinMaxWaardes(oldMinMax, newMinMax)) {
                            ShowMessage(Color.GREEN, "Succesvol opgeslagen.");
                            LoadData(OgDevice); // update the old values
                        } else {
                            ShowMessage(Color.RED, "Er ging iets fout, controleer alle waardes of probeer het later nog eens.\nAls dit vaker voor komt neem contact op met de customer Support.");
                        }
                    } else {
                        ShowMessage(Color.YELLOW, "Geen veranderingen gevonden.");
                    }
                } else {
                    Pair<Boolean, String> Result = MinMaxWaardesController.CreateMMW(newMinMax);
                    if (Result.getKey()) {
                        ShowMessage(Color.GREEN, Result.getValue());
                        LoadData(OgDevice); // update the old values

                    } else {
                        ShowMessage(Color.RED, "Er ging iets fout, controleer alle waardes of probeer het later nog eens.\nError: " + Result.getValue());
                    }
                }
            }else{
                Menu menu = new Menu(user);
                Menu.create(menu);
                stage.close();
            }

        });
        Button btnCancel = new Button("Afbreken");


        btnCancel.setOnAction(e ->{
            // Having trouble getting the onCloseRequest being triggered by this event.
            // Its taking too much time trying to fix this, doing it the ugly way. ¯\_(ツ)_/¯
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
                OgDevice,

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

    private void LoadData(ArduinoLocatie al){
        currentWaardes =  new AtomicReference<>(MinMaxWaardesController.getSpecificMinMaxWaardes(al));

        // Fill the new data
        lblLocatie.setText(currentWaardes.get().getLocatie().getLocatie());

        numMinPH.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(PHMargin[0],PHMargin[1], currentWaardes.get().getMinPH()));
        numMaxPH.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(PHMargin[0],PHMargin[1], currentWaardes.get().getMaxPH()));

        numMinGT.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(TempMargin[0], TempMargin[1], currentWaardes.get().getMinGT()));
        numMaxGT.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(TempMargin[0], TempMargin[1], currentWaardes.get().getMaxGT()));
        numMinLT.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(TempMargin[0], TempMargin[1], currentWaardes.get().getMinLT()));
        numMaxLT.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(TempMargin[0], TempMargin[1], currentWaardes.get().getMaxLT()));

        numMinGV.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(HumMargin[0], HumMargin[1], currentWaardes.get().getMinGV()));
        numMaxGV.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(HumMargin[0], HumMargin[1], currentWaardes.get().getMaxGV()));
        numMinLV.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(HumMargin[0], HumMargin[1], currentWaardes.get().getMinLV()));
        numMaxLV.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(HumMargin[0], HumMargin[1], currentWaardes.get().getMaxLV()));

    }

    private void FillInAllData(MinMaxWaardes MMW){

    }

    private void getUsers(){
        // Clear the list in case old values still exist
        cbxUsers.getItems().clear();
        // Load and add the items from the database
        cbxUsers.getItems().addAll(Objects.requireNonNull(UserController.getAllUsers()));

        // If there are no arduino's registered list a 'no devices' object
        if (cbxUsers.getItems().isEmpty()){
            cbxUsers.getItems().add(user);
            cbxUsers.setDisable(true);
        }
        else {
            cbxUsers.setDisable(false);
        }

        // automatically select the first item
        cbxUsers.getSelectionModel().select(0);

        // select the current user
        for (Gebruiker user: cbxUsers.getItems()
        ) {
            if (OgUser.equals(user)){
                cbxUsers.getSelectionModel().select(user);
            }
        }
    }

    private void ShowMessage(Color clr, String msg){
        Double duration = (Double)(msg.length()/5.0);
        System.out.println(String.format("MSG.length = %d, Duration = %f", msg.length(), duration));
        fadeout.setDuration(Duration.seconds(duration));
        txtSysMessage.setFill(clr);
        txtSysMessage.setText(msg);
        fadeout.playFromStart();
//        sysTransition.playFromStart();
    }

}
