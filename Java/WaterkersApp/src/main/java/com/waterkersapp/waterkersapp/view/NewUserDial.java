package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.model.Gebruiker;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.waterkersapp.waterkersapp.MainWindow.ICON;

public class NewUserDial {

    // Variables
    ///////////////////////[Window]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] WINDOW_SIZE = {750, 450}; // Default: 800 * 450;
    Color backgroundColor = Color.web("#BADC8F");

    ///////////////////////[Logo]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] ICON_SIZE = {75, 75}; //Default: 100;





    BorderPane borderPane = new BorderPane();

    public static Stage stage;

    public static void create(NewUserDial newUser) {
        stage = new Stage();
        stage.setTitle("Beheer/Instellingen");
        stage.getIcons().add(ICON);

        Scene scene = new Scene(newUser.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        scene.getStylesheets().addAll(NewUserDial.class.getResource("/com/waterkersapp/css/GlobalStyleSheet.css").toString());
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


    /**
     * Leave ogUser null to create a new user.
     * @param ogUser The user to edit.
     */
    public NewUserDial(Gebruiker ogUser) {
        HBox titleBox = new HBox();
        GridPane gp = new GridPane();
        VBox wrapperBox = new VBox();

        // Set the title for the page.
        // if the variable ogUser == null the page is meant to create a user not edit one.
        Label lblTitle = new Label("Gebruiker aanmaken.");
        if (ogUser != null){
            lblTitle.setText("Gebruiker '" + ogUser.getLoginNaam() + "' bewerken.");
        }

        Label lblUsername = new Label("Gebruikersnaam: ");
        TextField tbxUsername = new TextField("");
        Button btnRstUsername = new Button("Reset");
        btnRstUsername.setOnAction(e -> {
            //@TODO Make the functionality to reset the text
        });
        Label lblPassword = new Label("Wachtwoord: ");
        Button btnChngePasword = new Button("Verander wachtwoord");
        btnChngePasword.setOnAction(e -> {
            //@TODO Direct tot the change password dialog
        });
        Button btnRstPasword = new Button("Reset");
        btnRstPasword.setOnAction(e -> {
            //@TODO Make the functionality to reset the text
        });





        titleBox.getChildren().addAll(lblTitle);
        gp.getChildren().addAll();
        wrapperBox.getChildren().addAll(titleBox ,gp);

        wrapperBox.setSpacing(5);
        wrapperBox.setPadding(new Insets(20));

        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty());

        borderPane.setLeft(wrapperBox);
    }
}
