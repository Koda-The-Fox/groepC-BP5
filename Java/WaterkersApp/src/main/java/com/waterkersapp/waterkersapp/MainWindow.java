package com.waterkersapp.waterkersapp;

import com.waterkersapp.waterkersapp.model.ArduinoLocatie;
import com.waterkersapp.waterkersapp.model.Gebruiker;
import com.waterkersapp.waterkersapp.view.*;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class MainWindow extends Application {

    public static final Image ICON = new Image(Objects.requireNonNull(MainWindow.class.getResourceAsStream("/com/waterkersapp/media/images/WaterkersApp-Logo(256x).png")));

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // #TODO Uncomment when done debugging
//        Login login = new Login();
//        Login.create(login);

        Gebruiker Jordy = new Gebruiker("JorVV", false);
        Gebruiker Admin = new Gebruiker("Admn", true);
        ArduinoLocatie al = new ArduinoLocatie(10, "TestLocatie", "Testing");
        ArduinoLocatie GroepC_Arduino = new ArduinoLocatie(1, "GroepC_Arduino", "Uit");
        ArduinoLocatie Kas2 = new ArduinoLocatie(2, "Kas2", "Uit");

        // LOGIN
//        Login login = new Login();
//        Login.create(login);

        // MENU
//        Menu menu = new Menu(Jordy);
//        Menu menu = new Menu(Admin);
//        Menu.create(menu);

        // Sensor overzicht
//        SensorOverview sensorOverview = new SensorOverview(Kas2);
//        SensorOverview.create(sensorOverview);

        // Beheer
//        Beheer beheer = new Beheer(Kas2, Jordy);
//        Beheer.create(beheer, menu);


        // change password dialog
//        ChangePassDial.create(Jordy, Jordy); // User changes its own password
//        ChangePassDial.create(Jordy, Admin); // Admin changes userPassword

        // change/create user device
//        NewDeviceDial.create(al); // change device
//        NewDeviceDial.create(Kas2); // change device
        NewDeviceDial.create(GroepC_Arduino); // change GroepC_Arduino device
//        NewDeviceDial.create(null); // new device

//         change/create user dialog
//        NewUserDial.create(Jordy, Jordy); // change user
//        NewUserDial.create(null, Jordy); // new user
//        NewUserDial.create(null, Admin); // new user
//        NewUserDial.create(Jordy, Admin); // Admin changes user


        // about page
//        AboutPage ap = new AboutPage();
//        AboutPage.create(ap);
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