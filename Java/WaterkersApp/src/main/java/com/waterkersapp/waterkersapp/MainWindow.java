package com.waterkersapp.waterkersapp;

import com.waterkersapp.waterkersapp.view.Login;
import com.waterkersapp.waterkersapp.view.Menu;
import com.waterkersapp.waterkersapp.view.SensorOverview;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainWindow extends Application {

    public static final Image ICON = new Image(MainWindow.class.getResourceAsStream("/com/waterkersapp/media/images/Icon.png"));

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // LOGIN
        //Login login = new Login();
        //Login.create(login);

        // MENU
//        Menu menu = new Menu();
//        Menu.create(menu);

        // Sensor overzicht
        SensorOverview sensorOverview = new SensorOverview();
        SensorOverview.create(sensorOverview, "Grondvochtigheid");

        // Beheer
        //Beheer beheer = new Beheer();
        //Beheer.create(beheer);
    }

    /*
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

     */

}