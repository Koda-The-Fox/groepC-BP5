package com.greengenie.green_genie.view;

import com.greengenie.green_genie.model.Weather;
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

import java.util.Objects;

import static com.greengenie.green_genie.MainWindow.ICON;

public class InputWeather {

    // Variables
    ///////////////////////[Window]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] WINDOW_SIZE = {750, 450}; // Default: 800 * 450;

    Color backgroundColor = Color.web("#BADC8F");

    ///////////////////////[Logo]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] ICON_SIZE = {50, 50}; //Default: 100;


    BorderPane borderPane = new BorderPane();

    public static Stage stage;

    public static void create(InputWeather inputWeather) {
        stage = new Stage();
        stage.setTitle("InputWeather");
        stage.getIcons().add(ICON);

        Scene scene = new Scene(inputWeather.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        scene.getStylesheets().addAll(Objects.requireNonNull(Menu.class.getResource("/com/greengenie/css/GlobalStyleSheet.css")).toString());

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

    public InputWeather() {
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

        // @TODO#1 Use a weather API to get the weather

        Weather gatheredWeather = null;
        try {
            gatheredWeather = new Weather();
            gatheredWeather = new Weather("Oss", 10.00, 6.00, 99, 225, 4, 8.00, "08:36", "16:29", 0, 90); // Dummy data from https://weerlive.nl/api/json-data-10min.php?key=e23912e085&locatie=Oss (16-12-2021 16:22)
        }
        catch (Exception ex){
            ex.printStackTrace();
        }


        int row = 1;
        Label lblLocation = new Label("Locatie: ");
        contentGrid.add(lblLocation, 1, row);
        TextField tbxLocation = new TextField();
        tbxLocation.setText(gatheredWeather.getLoc());
        contentGrid.add(tbxLocation, 2, row);
        row++;// end of row

        // Temperature
        Label lblMaxTempRO = new Label("Maximale verwachte temperatuur: ");
        contentGrid.add(lblMaxTempRO, 1, row);
        Spinner<Double> numMaxTemp = new Spinner<>();
        numMaxTemp.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-273.15, 60, gatheredWeather.getMaxTemp()));
        contentGrid.add(numMaxTemp, 2, row);
        row++;// end of row
        Label lblMinTempRO = new Label("Minimale verwachte temperatuur: ");
        contentGrid.add(lblMinTempRO, 1, row);
        Spinner<Double> numMinTemp = new Spinner<>();
        numMinTemp.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-273.15, 60, gatheredWeather.getMaxTemp()));
        contentGrid.add(numMinTemp, 2, row);
        row++;// end of row

        Label lblHumRO = new Label("Verwachte lucht vochtigheid: ");
        contentGrid.add(lblHumRO, 1, row);
        Spinner<Integer> numHum = new Spinner<>();
        numHum.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, gatheredWeather.getrHum()));
        contentGrid.add(numHum, 2, row);
        row++;// end of row

        // Wind
        Label lblDirectionRO = new Label("Wind Richting: ");
        contentGrid.add(lblDirectionRO, 1, row);
        Spinner<Integer> numDirection = new Spinner<>();
        numDirection.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 360, gatheredWeather.getWindD()));
        contentGrid.add(numDirection, 2, row);
        row++;// end of row
        Label lblSpeedRO = new Label("Wind Snelheid(in Bft): ");
        contentGrid.add(lblSpeedRO, 1, row);
        Spinner<Double> numSpeed = new Spinner<>();
        numSpeed.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 12, gatheredWeather.getWindS()));
        contentGrid.add(numSpeed, 2, row);
        row++;// end of row


        Label lblDwPntRO = new Label("Dauwpunt: ");
        contentGrid.add(lblDwPntRO, 1, row);
        Spinner<Double> numDwPnt = new Spinner<>();
        numDwPnt.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(-273.15, 100, gatheredWeather.getDewPnt()));
        contentGrid.add(numDwPnt, 2, row);
        row++;// end of row

        // Time
        Label lblSupRO = new Label("Zonsopgang: ");
        contentGrid.add(lblSupRO, 1, row);
        Spinner<Integer> dtpSupH = new Spinner<>();
        dtpSupH.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, gatheredWeather.getSunUp().getHours()));
        contentGrid.add(dtpSupH, 2, row);
        Label lblSupDevRO = new Label(" : ");
        contentGrid.add(lblSupDevRO, 3, row);
        Spinner<Integer> dtpSupM = new Spinner<>();
        dtpSupM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, gatheredWeather.getSunUp().getMinutes()));
        contentGrid.add(dtpSupM, 4, row);
        row++;// end of row
        Label lblSdwnRO = new Label("Zondondergang: ");
        contentGrid.add(lblSdwnRO, 1, row);
        Spinner<Integer> dtpSdwnH = new Spinner<>();
        dtpSdwnH.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, gatheredWeather.getSunDwn().getHours()));
        contentGrid.add(dtpSdwnH, 2, row);
        Label lblSdwnDevRO = new Label(" : ");
        contentGrid.add(lblSdwnDevRO, 3, row);
        Spinner<Integer> dtpSdwnM = new Spinner<>();
        dtpSdwnM.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, gatheredWeather.getSunDwn().getMinutes()));
        contentGrid.add(dtpSdwnM, 4, row);
        row++;// end of row


        Label lblRainRO = new Label("Kans op regen: ");
        contentGrid.add(lblRainRO, 1, row);
        Spinner<Integer> numRain = new Spinner<>();
        numRain.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, gatheredWeather.getRain()));
        contentGrid.add(numRain, 2, row);
        row++;// end of row
        Label lblSunRO = new Label("Kans op zon: ");
        contentGrid.add(lblSunRO, 1, row);
        Spinner<Integer> numSun = new Spinner<>();
        numSun.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, gatheredWeather.getRain()));
        contentGrid.add(numSun, 2, row);
        row++;// end of row



        /////////////////////////////////////// [ending code] \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        wrapperBox.getChildren().addAll(logoBox, contentGrid);

        borderPane.setLeft(wrapperBox);
        borderPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
    }
}
