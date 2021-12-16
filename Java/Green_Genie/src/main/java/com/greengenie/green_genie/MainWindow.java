package com.greengenie.green_genie;

import com.greengenie.green_genie.view.Menu;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class MainWindow extends Application {

    public static final Image ICON = new Image(Objects.requireNonNull(MainWindow.class.getResourceAsStream("/com/greengenie/media/images/GreenGenie-Logo(256x).png")));

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // MENU
        Menu menu = new Menu();
        Menu.create(menu);

    }


}