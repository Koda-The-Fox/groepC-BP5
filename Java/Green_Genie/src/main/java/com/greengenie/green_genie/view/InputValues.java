package com.greengenie.green_genie.view;

import com.greengenie.green_genie.algorithm.Driver;
import com.greengenie.green_genie.algorithm.ID3;
import com.greengenie.green_genie.control.MinMaxWaardesController;
import com.greengenie.green_genie.control.RegistratieController;
import com.greengenie.green_genie.model.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.greengenie.green_genie.MainWindow.ICON;

public class InputValues {

    public MinMaxWaardes MMV = new MinMaxWaardes();
    public sensorRegistratie registratie = new sensorRegistratie(null,"",0,0,0,0,0);

    private String ID3Result = "";

    // Variables
    ///////////////////////[Window]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] WINDOW_SIZE = {750, 550}; // Default: 800 * 450;

    Color backgroundColor = Color.web("#BADC8F");

    ///////////////////////[Logo]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] ICON_SIZE = {50, 50}; //Default: 100;

    Label lblMinMaxRO = new Label();
    Label lblLastReg = new Label();
    Spinner<Double> numLuchtTemp = new Spinner<>();
    Spinner<Double> numLuchtVocht = new Spinner<>();
    Spinner<Double> numGrondTemp = new Spinner<>();
    Spinner<Double> numGrondVocht = new Spinner<>();


    BorderPane borderPane = new BorderPane();

    public static Stage stage;

    public static void create(InputValues inputValues) {
        stage = new Stage();
        stage.setTitle("Advies");
        stage.getIcons().add(ICON);

        Scene scene = new Scene(inputValues.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        scene.getStylesheets().addAll(Objects.requireNonNull(InputValues.class.getResource("/com/greengenie/css/GlobalStyleSheet.css")).toString());

        stage.setScene(scene);
        // set the window to be resizable
        stage.setResizable(true); // default: true

        stage.setOnCloseRequest(e -> stage.close());

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setOnCloseRequest(e ->{
            Menu.create(new Menu());
        });

        stage.show();
    }
    public Parent getParent() {
        return borderPane;
    }

    public InputValues(ArduinoLocatie device) {
        VBox wrapperBox = new VBox();
        wrapperBox.setPadding(new Insets(5, ICON_SIZE[1], 50, ICON_SIZE[1]));
        // set then size of the wrapperBox dynamically to the window(Stage)
        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty());

        GridPane contentGrid = new GridPane();
        contentGrid.getStyleClass().add("Content_Window");
        // make the size of the contentGrid relative to the wrapperBox size
        contentGrid.prefWidthProperty().bind(wrapperBox.widthProperty());
        contentGrid.prefHeightProperty().bind(wrapperBox.heightProperty());
        contentGrid.setVgap(10);
        contentGrid.setHgap(5);
        contentGrid.setPadding(new Insets(10 - contentGrid.getVgap(),10, 10, 10 - contentGrid.getHgap()));

        HBox logoBox = new HBox();
        logoBox.setAlignment(Pos.CENTER_LEFT);
        logoBox.setPadding(new Insets(0, 0, 10, -ICON_SIZE[1]));
        logoBox.setSpacing(5);

        // Set a value relative to the item width and height for the font size
        // source: https://easysavecode.com/66zhPWhD
        DoubleProperty fontSize = new SimpleDoubleProperty(24);
        fontSize.bind(contentGrid.widthProperty().add(contentGrid.heightProperty()).divide(50));

        ImageView imgLogo = new ImageView(ICON);
        imgLogo.setFitHeight(ICON_SIZE[0]);
        imgLogo.setFitWidth(ICON_SIZE[1]);
        logoBox.getChildren().add(imgLogo);

        int ROW = 1;
        contentGrid.add(logoBox, 1, 1);
        ROW++;// end of ROW


        // get the default Min Max values
        if (!Objects.equals(device.getStatus(), "EmptyObject")) {
            refreshData(device);
        }

        lblMinMaxRO.setText("Goede waardes: \n" + MMV.toString().replace("= ", "=\t"));
        contentGrid.add(lblMinMaxRO, 1, ROW, 3, 10);
        ROW+=10;// end of ROW

        if (!Objects.equals(device.getStatus(), "EmptyObject")) {
            Label lblDeviceRO = new Label("Apparaat: ");
            Label lblDevice = new Label(device.getLocatie());
            contentGrid.add(lblDeviceRO, 1, ROW);
            contentGrid.add(lblDevice, 2, ROW);
            ROW++;// end of ROW

            Label lblLastRegRO = new Label("Laatste registratie: ");
            Button btnRefresh = new Button("Vraag opnieuw op");
            btnRefresh.setOnAction(e->{
                refreshData(device);
                FillFields();
            });
            contentGrid.add(lblLastRegRO, 1, ROW);
            contentGrid.add(lblLastReg, 2, ROW);
            contentGrid.add(btnRefresh, 3, ROW);
            ROW++;// end of ROW
        }

        Label lblLuchtTempRO = new Label("Verwachte lucht temperatuur: ");
        contentGrid.add(lblLuchtTempRO, 1, ROW);
        numLuchtTemp.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-273.15, 60, 0));
        numLuchtTemp.setEditable(true);
        contentGrid.add(numLuchtTemp, 2, ROW);
        Label lblLuchtTempOupt = new Label();
        contentGrid.add(lblLuchtTempOupt, 3, ROW);
        ROW++;// end of ROW

        Label lblLuchtVochtRO = new Label("Verwachte lucht vochtigheid: ");
        contentGrid.add(lblLuchtVochtRO, 1, ROW);
        numLuchtVocht.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0));
        numLuchtVocht.setEditable(true);
        contentGrid.add(numLuchtVocht, 2, ROW);
        Label lblLuchtVochtOupt = new Label();
        contentGrid.add(lblLuchtVochtOupt, 3, ROW);
        ROW++;// end of ROW


        Label lblGrondTempRO = new Label("Verwachte grond temperatuur: ");
        contentGrid.add(lblGrondTempRO, 1, ROW);
        numGrondTemp.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-273.15, 60, 0));
        numGrondTemp.setEditable(true);
        contentGrid.add(numGrondTemp, 2, ROW);
        Label lblGrondTempOupt = new Label();
        contentGrid.add(lblGrondTempOupt, 3, ROW);
        ROW++;// end of ROW

        Label lblGrondVochtRO = new Label("Verwachte grond vochtigheid: ");
        contentGrid.add(lblGrondVochtRO, 1, ROW);
        numGrondVocht.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0));
        numGrondVocht.setEditable(true);
        contentGrid.add(numGrondVocht, 2, ROW);
        Label lblGrondVochtOupt = new Label();
        contentGrid.add(lblGrondVochtOupt, 3, ROW);
        ROW++;// end of ROW

        Label lblOutpt = new Label();
        VBox feedbackBox = new VBox();
        feedbackBox.setDisable(true);
        feedbackBox.setSpacing(10);

        Button btnInputValues = new Button("Values");
        btnInputValues.setOnAction(e ->{
            Input inpt = new Input();
            inpt.Grond_Vocht = ValueClassification.getClassification(MMV.getMinGV(), MMV.getMaxGV(), numGrondVocht.getValue());
            lblGrondVochtOupt.setText(inpt.Grond_Vocht.toString());
            inpt.Grond_Temp  = ValueClassification.getClassification(MMV.getMinGT(), MMV.getMaxGT(), numGrondTemp.getValue());
            lblGrondTempOupt.setText(inpt.Grond_Temp.toString());
            inpt.Lucht_Vocht = ValueClassification.getClassification(MMV.getMinLV(), MMV.getMaxLV(), numLuchtVocht.getValue());
            lblLuchtVochtOupt.setText(inpt.Lucht_Vocht.toString());
            inpt.Lucht_Temp  = ValueClassification.getClassification(MMV.getMinLT(), MMV.getMaxLT(), numLuchtTemp.getValue());
            lblLuchtTempOupt.setText(inpt.Lucht_Temp.toString());

            String result = "";
            try {
                ID3Result = Driver.getAdvice(inpt);
                String ID3res = "";
                if (Objects.equals(ID3Result, "nee"))
                    ID3res = "niet ";
                result = "De huidige waardes zijn "+ID3res+"acceptabel.";
                feedbackBox.setDisable(false);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            lblOutpt.setText(result);
        });
        contentGrid.add(btnInputValues, 1, ROW);
        ROW++;// end of ROW

        Label lblFeedbackRO = new Label("Kopt deze uitkomst?");

        HBox FeedbackButtonBox = new HBox();
        Button btnJaValues = new Button("Ja");
        btnJaValues.setOnAction(e ->{
            try {
                ID3 id3 = new ID3();
                id3.rawdata = id3.loadCSV(Driver.FILEPATH);

                Input inpt = new Input();
                inpt.Grond_Vocht = ValueClassification.getClassification(MMV.getMinGV(), MMV.getMaxGV(), numGrondVocht.getValue());
                lblGrondVochtOupt.setText(inpt.Grond_Vocht.toString());
                inpt.Grond_Temp = ValueClassification.getClassification(MMV.getMinGT(), MMV.getMaxGT(), numGrondTemp.getValue());
                lblGrondTempOupt.setText(inpt.Grond_Temp.toString());
                inpt.Lucht_Vocht = ValueClassification.getClassification(MMV.getMinLV(), MMV.getMaxLV(), numLuchtVocht.getValue());
                lblLuchtVochtOupt.setText(inpt.Lucht_Vocht.toString());
                inpt.Lucht_Temp = ValueClassification.getClassification(MMV.getMinLT(), MMV.getMaxLT(), numLuchtTemp.getValue());
                lblLuchtTempOupt.setText(inpt.Lucht_Temp.toString());
                inpt.Acceptabel = ID3Result;
                id3.addRecord(inpt);
            }catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        });
        FeedbackButtonBox.getChildren().add(btnJaValues);
        Button btnNeeValues = new Button("Nee");
        btnNeeValues.setOnAction(e ->{
            try {
                ID3 id3 = new ID3();
                id3.rawdata = id3.loadCSV(Driver.FILEPATH);

                // create the record
                Input inpt = new Input();
                inpt.Grond_Vocht = ValueClassification.getClassification(MMV.getMinGV(), MMV.getMaxGV(), numGrondVocht.getValue());
                lblGrondVochtOupt.setText(inpt.Grond_Vocht.toString());
                inpt.Grond_Temp  = ValueClassification.getClassification(MMV.getMinGT(), MMV.getMaxGT(), numGrondTemp.getValue());
                lblGrondTempOupt.setText(inpt.Grond_Temp.toString());
                inpt.Lucht_Vocht = ValueClassification.getClassification(MMV.getMinLV(), MMV.getMaxLV(), numLuchtVocht.getValue());
                lblLuchtVochtOupt.setText(inpt.Lucht_Vocht.toString());
                inpt.Lucht_Temp  = ValueClassification.getClassification(MMV.getMinLT(), MMV.getMaxLT(), numLuchtTemp.getValue());
                lblLuchtTempOupt.setText(inpt.Lucht_Temp.toString());
                inpt.Acceptabel  = ID3Result;
                // remove the record
                id3.removeRecord(inpt);

                // create the new record
                inpt.Acceptabel = (Objects.equals(inpt.Acceptabel, "ja"))? "nee" : "ja";
                // fill in the new record
                id3.addRecord(inpt);

                // Write the file again
                id3.writeCSV(Driver.FILEPATH);

                // get new result
                Input inpt2 = new Input();
                inpt2.Grond_Vocht = ValueClassification.getClassification(MMV.getMinGV(), MMV.getMaxGV(), numGrondVocht.getValue());
                lblGrondVochtOupt.setText(inpt2.Grond_Vocht.toString());
                inpt2.Grond_Temp  = ValueClassification.getClassification(MMV.getMinGT(), MMV.getMaxGT(), numGrondTemp.getValue());
                lblGrondTempOupt.setText(inpt2.Grond_Temp.toString());
                inpt2.Lucht_Vocht = ValueClassification.getClassification(MMV.getMinLV(), MMV.getMaxLV(), numLuchtVocht.getValue());
                lblLuchtVochtOupt.setText(inpt2.Lucht_Vocht.toString());
                inpt2.Lucht_Temp  = ValueClassification.getClassification(MMV.getMinLT(), MMV.getMaxLT(), numLuchtTemp.getValue());
                lblLuchtTempOupt.setText(inpt2.Lucht_Temp.toString());

                String result = "";
                try {
                    ID3Result = Driver.getAdvice(inpt2);
                    String ID3res = "";
                    if (Objects.equals(ID3Result, "nee"))
                        ID3res = "niet ";
                    result = "De huidige waardes zijn "+ID3res+"acceptabel.";
                    feedbackBox.setDisable(false);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                lblOutpt.setText(result);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        FeedbackButtonBox.getChildren().add(btnNeeValues);

        contentGrid.add(lblOutpt, 1, ROW, 2,1);
        ROW++;// end of ROW

        feedbackBox.getChildren().addAll(lblFeedbackRO, FeedbackButtonBox);


        contentGrid.add(feedbackBox, 1, ROW, 2,1);
        ROW++;// end of ROW

        if (!Objects.equals(device.getStatus(), "EmptyObject"))
            FillFields();

        /////////////////////////////////////// [ending code] \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        wrapperBox.getChildren().addAll(logoBox, contentGrid);

        borderPane.setLeft(wrapperBox);
        borderPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
    }

    private void refreshData(ArduinoLocatie device){
        // get the min max values for the specific device
        MMV = MinMaxWaardesController.getSpecificMinMaxWaardes(device);

        // get the last registered sensor values
        registratie = RegistratieController.GetRegFromDevice(device);


    }

    private void FillFields(){
        lblLastReg.setText(registratie.getDatumTijd());
        numLuchtTemp.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-273.15, 60, registratie.getLuchtTemp()));
        numLuchtVocht.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, registratie.getLuchtVocht()));
        numGrondTemp.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-273.15, 60, registratie.getGrondTemp()));
        numGrondVocht.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, registratie.getGrondVocht()));
        lblMinMaxRO.setText("Goede waardes: \n" + MMV.toString().replace("= ", "=\t"));
    }

}
