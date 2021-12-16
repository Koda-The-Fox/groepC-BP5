package com.greengenie.green_genie.dial;

import com.greengenie.green_genie.view.Menu;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static com.greengenie.green_genie.MainWindow.ICON;


public class InputDialog {

    public static String HighMediumLow(String question){
        Dialog<Boolean> dialog = new Dialog<>();
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(ICON);
        dialog.getDialogPane().getStylesheets().addAll(Objects.requireNonNull(Menu.class.getResource("/com/greengenie/css/GlobalStyleSheet.css")).toString());

        dialog.setTitle("Aparaat aanmaken.");
        dialog.setHeaderText("Aparaat aanmaken.");

        Label lblQuestion = new Label(question);
        lblQuestion.setAlignment(Pos.CENTER_LEFT);

        ButtonType CancelButtonType = new ButtonType("Annuleren", ButtonBar.ButtonData.CANCEL_CLOSE);

        ButtonType HighButton = new ButtonType("Hoog", ButtonBar.ButtonData.OK_DONE);
        ButtonType NormalButton = new ButtonType("Normaal", ButtonBar.ButtonData.OK_DONE);
        ButtonType LowButton = new ButtonType("Laag", ButtonBar.ButtonData.OK_DONE);

        dialog.getDialogPane().getButtonTypes().addAll(HighButton, NormalButton, LowButton, CancelButtonType);


        AtomicReference<String> result = new AtomicReference<>("");
        dialog.setResultConverter(dialogButton -> {
            // Cancel button
            if (dialogButton == CancelButtonType) {
                result.set("Geannuleerd.");
                return false;
            }
            else if (dialogButton == HighButton){
                result.set("Hoog.");
            }
            else if (dialogButton == NormalButton){
                result.set("Normaal.");
            }
            else if (dialogButton == LowButton){
                result.set("Laag.");
            }
            return true;
        });


        dialog.getDialogPane().setContent(lblQuestion);
        dialog.showAndWait();
        return result.get();
    }
}
