package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.model.Gebruiker;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.waterkersapp.waterkersapp.MainWindow.ICON;

public class ChangePassDial {

    // Variables
    ///////////////////////[Window]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] WINDOW_SIZE = {400, 450}; // Default: 800 * 450;
    Color backgroundColor = Color.web("#BADC8F");





    BorderPane borderPane = new BorderPane();

    public static Stage stage;

    public static void create(ChangePassDial cpDial) {
        stage = new Stage();
        stage.setTitle("Beheer/Instellingen");
        stage.getIcons().add(ICON);

        Scene scene = new Scene(cpDial.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        scene.getStylesheets().addAll(ChangePassDial.class.getResource("/com/waterkersapp/css/GlobalStyleSheet.css").toString());
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
     *
     * @param ogUser The user whom passwords is being changed.
     */
    public ChangePassDial(Gebruiker ogUser) {
        GridPane gp = new GridPane();
        VBox wrapperBox = new VBox();

        Label lblNewPass = new Label("New wachtwoord: ");
        gp.add(lblNewPass, 1, 1, 1, 1);
        PasswordField tbxNewPass = new PasswordField();
        gp.add(tbxNewPass, 2, 1, 1, 1);
        Label lblNewPassVal = new Label("New wachtwoord nogmaals: ");
        gp.add(lblNewPassVal, 1, 2, 1, 1);
        PasswordField tbxNewPassVal = new PasswordField();
        gp.add(tbxNewPassVal, 2, 2, 1, 1);
        Label lblOldPass = new Label("Vorig wachtwoord: ");
        gp.add(lblOldPass, 1, 3, 1, 1);
        PasswordField tbxOldPass = new PasswordField();
        gp.add(tbxOldPass, 2, 3, 1, 1);





//        gp.getChildren().addAll(lblNewPass, tbxNewPass, lblNewPassVal, tbxNewPassVal, lblOldPass, tbxOldPass);
        wrapperBox.getChildren().addAll(gp);

        wrapperBox.setSpacing(5);
        wrapperBox.setPadding(new Insets(10));

        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty());

        borderPane.setLeft(wrapperBox);
        borderPane.setPadding(new Insets(20));
    }
}
