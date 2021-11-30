package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.control.ChangePassController;
import com.waterkersapp.waterkersapp.model.Gebruiker;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Objects;
import java.util.Optional;

import static com.waterkersapp.waterkersapp.MainWindow.ICON;

public class ChangePassDial {

    public static boolean create(Gebruiker ogUser) {
        // Create the custom dialog.
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Change Password Dialog");
        dialog.setHeaderText("Wachtwoord veranderen voor gebruiker: " + ogUser.getLoginNaam());

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Opslaan", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, new ButtonType("Annuleren", ButtonBar.ButtonData.CANCEL_CLOSE));

        // Create the username and password labels and fields.
        GridPane gp = new GridPane();
        HBox buttonBox = new HBox();
        VBox wrapperBox = new VBox();

        Label lblNewPass = new Label("New wachtwoord: ");
        gp.add(lblNewPass, 1, 1, 1, 1);
        PasswordField tbxNewPass = new PasswordField();
        gp.add(tbxNewPass, 2, 1, 1, 1);
        Label lblNewPassVal = new Label("New wachtwoord nogmaals: ");
        gp.add(lblNewPassVal, 1, 2, 1, 1);
        PasswordField tbxNewPassVal = new PasswordField();
        gp.add(tbxNewPassVal, 2, 2, 1, 1);
        Text lblNewPassValErr = new Text("");
        gp.add(lblNewPassValErr, 1, 3, 2, 1);
        Label lblOldPass = new Label("Vorig wachtwoord: ");
        gp.add(lblOldPass, 1, 4, 1, 1);
        PasswordField tbxOldPass = new PasswordField();
        gp.add(tbxOldPass, 2, 4, 1, 1);
        Text lblOldPassValErr = new Text("\r\n");
        gp.add(lblOldPassValErr, 1, 3, 2, 1);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        tbxNewPass.textProperty().addListener((observable, oldValue, newValue) -> {
            lblNewPassValErr.setText("");
            lblOldPassValErr.setText("");
            loginButton.setDisable(true);
            if (!Objects.equals(tbxNewPass.getText(), tbxNewPassVal.getText())) {
                lblNewPassValErr.setFill(Color.RED);
                lblNewPassValErr.setText("Wachtwoord komt niet overeen.");
            } else if (tbxNewPass.getText().isEmpty()){
                lblNewPassValErr.setFill(Color.RED);
                lblNewPassValErr.setText("Vul aub een wachtwoord in.");
            }
            else if (tbxOldPass.getText().equals(tbxNewPass.getText()) ){
                lblOldPassValErr.setFill(Color.RED);
                lblOldPassValErr.setText("Het nieuwe wachtwoord mag niet hetzelfde zijn als het\nhuidige wachtwoord.");
            }
            else if (tbxOldPass.getText().isEmpty()){
                // do nothing
            }
            else{
                lblNewPassValErr.setText("");
                lblOldPassValErr.setText("");
                loginButton.setDisable(false);
            }
        });
        tbxNewPassVal.textProperty().addListener((observable, oldValue, newValue) -> {
            lblNewPassValErr.setText("");
            lblOldPassValErr.setText("");
            loginButton.setDisable(true);
            if (!Objects.equals(tbxNewPass.getText(), tbxNewPassVal.getText())) {
                lblNewPassValErr.setFill(Color.RED);
                lblNewPassValErr.setText("Wachtwoord komt niet overeen.");
            } else if (tbxNewPass.getText().isEmpty()){
                lblNewPassValErr.setFill(Color.RED);
                lblNewPassValErr.setText("Vul aub een wachtwoord in.");
            }
            else if (tbxOldPass.getText().equals(tbxNewPass.getText()) ){
                lblOldPassValErr.setFill(Color.RED);
                lblOldPassValErr.setText("Het nieuwe wachtwoord mag niet hetzelfde zijn als het\nhuidige wachtwoord.");
            }
            else if (tbxOldPass.getText().isEmpty()){
                // do nothing
            }
            else{
                lblNewPassValErr.setText("");
                lblOldPassValErr.setText("");
                loginButton.setDisable(false);
            }
        });
        tbxOldPass.textProperty().addListener((observable, oldValue, newValue) -> {
            lblNewPassValErr.setText("");
            lblOldPassValErr.setText("");
            loginButton.setDisable(true);
            if (!Objects.equals(tbxNewPass.getText(), tbxNewPassVal.getText())) {
                // Do nothing
            } else if (tbxNewPass.getText().isEmpty()){
                // Do nothing
            }
            else if (tbxOldPass.getText().equals(tbxNewPass.getText()) ){
                lblOldPassValErr.setFill(Color.RED);
                lblOldPassValErr.setText("Het nieuwe wachtwoord mag niet hetzelfde zijn als het\nhuidige wachtwoord.");
            }
            else if (tbxOldPass.getText().isEmpty()){
                lblOldPassValErr.setFill(Color.RED);
                lblOldPassValErr.setText("Vul aub uw huidige wachtwoord in.\n");
            }
            else{
                lblNewPassValErr.setText("");
                lblOldPassValErr.setText("");
                loginButton.setDisable(false);
            }
        });


        buttonBox.getChildren().addAll();
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setSpacing(10);

//        gp.getChildren().addAll(lblNewPass, tbxNewPass, lblNewPassVal, tbxNewPassVal, lblOldPass, tbxOldPass);
        wrapperBox.getChildren().addAll(gp);

        wrapperBox.setSpacing(5);
        wrapperBox.setPadding(new Insets(10));
        wrapperBox.setPadding(new Insets(0,100,0,0));


        dialog.getDialogPane().setContent(wrapperBox);


        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return ChangePassController.ChangePassword(ogUser, tbxOldPass.getText(), tbxNewPass.getText());
            }
            return false;
        });

        dialog.showAndWait();

        return dialog.getResult();
    }

}
