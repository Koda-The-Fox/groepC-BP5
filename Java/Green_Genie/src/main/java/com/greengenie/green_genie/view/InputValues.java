package com.greengenie.green_genie.view;

import com.greengenie.green_genie.algorithm.Driver;
import com.greengenie.green_genie.model.Input;
import com.greengenie.green_genie.model.MinMaxWaardes;
import com.greengenie.green_genie.model.ValueClassification;
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
import java.util.Objects;

import static com.greengenie.green_genie.MainWindow.ICON;

public class InputValues {

    public MinMaxWaardes MMV = new MinMaxWaardes();


    // Variables
    ///////////////////////[Window]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] WINDOW_SIZE = {750, 450}; // Default: 800 * 450;

    Color backgroundColor = Color.web("#BADC8F");

    ///////////////////////[Logo]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] ICON_SIZE = {50, 50}; //Default: 100;


    BorderPane borderPane = new BorderPane();

    public static Stage stage;

    public static void create(InputValues inputValues) {
        stage = new Stage();
        stage.setTitle("InputValues");
        stage.getIcons().add(ICON);

        Scene scene = new Scene(inputValues.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        scene.getStylesheets().addAll(Objects.requireNonNull(InputValues.class.getResource("/com/greengenie/css/GlobalStyleSheet.css")).toString());

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

    public InputValues() {
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
        contentGrid.setPadding(new Insets(10));

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

        contentGrid.add(logoBox, 1, 1, 3, 1);


        int row = 2;
        Label lblLuchtTempRO = new Label("Verwachte lucht temperatuur: ");
        contentGrid.add(lblLuchtTempRO, 1, row);
        Spinner<Double> numLuchtTemp = new Spinner<>();
        numLuchtTemp.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-273.15, 60, 0));
        numLuchtTemp.setEditable(true);
        contentGrid.add(numLuchtTemp, 2, row);
        Label lblLuchtTempOupt = new Label();
        contentGrid.add(lblLuchtTempOupt, 3, row);
        row++;// end of row

        Label lblLuchtVochtRO = new Label("Verwachte lucht vochtigheid: ");
        contentGrid.add(lblLuchtVochtRO, 1, row);
        Spinner<Double> numLuchtVocht = new Spinner<>();
        numLuchtVocht.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0));
        numLuchtVocht.setEditable(true);
        contentGrid.add(numLuchtVocht, 2, row);
        Label lblLuchtVochtOupt = new Label();
        contentGrid.add(lblLuchtVochtOupt, 3, row);
        row++;// end of row


        Label lblGrondTempRO = new Label("Verwachte grond temperatuur: ");
        contentGrid.add(lblGrondTempRO, 1, row);
        Spinner<Double> numGrondTemp = new Spinner<>();
        numGrondTemp.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-273.15, 60, 0));
        numGrondTemp.setEditable(true);
        contentGrid.add(numGrondTemp, 2, row);
        Label lblGrondTempOupt = new Label();
        contentGrid.add(lblGrondTempOupt, 3, row);
        row++;// end of row

        Label lblGrondVochtRO = new Label("Verwachte grond vochtigheid: ");
        contentGrid.add(lblGrondVochtRO, 1, row);
        Spinner<Double> numGrondVocht = new Spinner<>();
        numGrondVocht.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 0));
        numGrondVocht.setEditable(true);
        contentGrid.add(numGrondVocht, 2, row);
        Label lblGrondVochtOupt = new Label();
        contentGrid.add(lblGrondVochtOupt, 3, row);
        row++;// end of row

        Label lblOutpt = new Label();
        VBox feedbackBox = new VBox();
        feedbackBox.setDisable(true);

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
                result = "Zijn de huidige waardes acceptabel? " + Driver.getAdvice(inpt);
                feedbackBox.setDisable(false);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            lblOutpt.setText(result);
        });
        contentGrid.add(btnInputValues, 1, row);
        row++;// end of row

        Label lblFeedbackRO = new Label("Kopt deze uitkomst?");

        HBox FeedbackButtonBox = new HBox();
        Button btnJaValues = new Button("Ja");
        btnJaValues.setOnAction(e ->{
            // Do nothing, all is good
        });
        Button btnNeeValues = new Button("Nee");
        btnNeeValues.setOnAction(e ->{
            //@TODO#6 add record to dataset file and/or maybe replace the result(acceptabel)
            // make a method that write in the dataset file
        });


        feedbackBox.getChildren().add(lblFeedbackRO);


        contentGrid.add(lblOutpt, 1, row, 2,1);
        row++;// end of row


        /////////////////////////////////////// [ending code] \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        wrapperBox.getChildren().addAll(logoBox, contentGrid);

        borderPane.setLeft(wrapperBox);
        borderPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
    }


}
