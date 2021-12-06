package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.MainWindow;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static com.waterkersapp.waterkersapp.MainWindow.ICON;

public class AboutPage {
    // Variables
    ///////////////////////[Window]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] WINDOW_SIZE = {350, 170}; // Default: 350 * 170;
    Color backgroundColor = Color.web("#BADC8F");

    ///////////////////////[Logo]\\\\\\\\\\\\\\\\\\\\\
    private static final double ICON_SIZE = 100; //Default: 100;


    BorderPane borderPane = new BorderPane();
    //GridPane borderPane = new GridPane();


    public static Stage stage;

    public static void create(AboutPage frmAP) {
        stage = new Stage();
        stage.setTitle("Sensor Overzicht");
        stage.getIcons().add(ICON);

        Scene scene = new Scene(frmAP.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        scene.getStylesheets().addAll(AboutPage.class.getResource("/com/waterkersapp/css/GlobalStyleSheet.css").toString(), AboutPage.class.getResource("/com/waterkersapp/css/AboutStyle.css").toString());
        stage.setScene(scene);
        // set the window to be resizable
        stage.setResizable(false); // default: false

        stage.setOnCloseRequest(e -> stage.close());

        stage.initModality(Modality.APPLICATION_MODAL);


        stage.show();
        //stage.showAndWait(); // show and stay focussed on window
    }
    public Parent getParent() {
        return borderPane;
    }



    public AboutPage() {
        ImageView imgLogo = new ImageView(ICON);
        imgLogo.setFitWidth(ICON_SIZE);
        imgLogo.setFitHeight(imgLogo.getFitWidth());


        GridPane gpInfo = new GridPane();
        GridPane gpSocials = new GridPane();
        VBox gridWrappers = new VBox(gpInfo, gpSocials);
        gridWrappers.setSpacing(15);
        HBox wrapperBox = new HBox(imgLogo, gridWrappers);
        wrapperBox.setSpacing(20);

        // Company/application info
        Label lblTitleInfo = new Label("Info");
        lblTitleInfo.getStyleClass().add("grid_title");
        gpInfo.add(lblTitleInfo, 1, 1, 2, 1);

        Label lblCompanyRO = new Label("Bedrijf: ");
        gpInfo.add(lblCompanyRO, 1, 2, 1, 1);
        Label lblCompany = new Label("GroepC");
        gpInfo.add(lblCompany, 2, 2, 1, 1);
        Label lblAuthorRO = new Label("Programmeur: ");
        gpInfo.add(lblAuthorRO, 1, 3, 1, 1);
        Label lblAuthor = new Label("Jordy van Venrooij");
        gpInfo.add(lblAuthor, 2, 3, 1, 1);

        // Socials
        gpSocials.setHgap(10);
        gpSocials.setPadding(new Insets(0,0,0,-gpSocials.getHgap()));
        Alert dialOpnBroweser = new Alert(Alert.AlertType.INFORMATION, "Wilt u door gaan? ", ButtonType.YES, ButtonType.NO);
        dialOpnBroweser.setTitle("");
        dialOpnBroweser.setHeaderText("Dit zal een webbrowser openen en je naar\nde GitHub pagina brengen van dit programma.");

        Label lblTitleSocials = new Label("Socials");
        lblTitleSocials.getStyleClass().add("grid_title");
        gpSocials.add(lblTitleSocials, 1, 1, 2, 1);

        ImageView imgGitHub = new ImageView(new Image(Objects.requireNonNull(MainWindow.class.getResourceAsStream("/com/waterkersapp/media/images/GitHub-32px.png"))));
        imgGitHub.setFitWidth(30);
        imgGitHub.setFitHeight(imgGitHub.getFitWidth());
        gpSocials.add(imgGitHub, 1, 2, 1, 1);
        Hyperlink hlGitHub = new Hyperlink("GitHub");
        hlGitHub.setOnAction(e -> {
            hlGitHub.setVisited(false);
            //if the button type is yes continue opening a webbrowser
            dialOpnBroweser.showAndWait();
            if (dialOpnBroweser.getResult().equals(ButtonType.YES)) {
                try {
                    java.awt.Desktop.getDesktop().browse(new URI("https://github.com/Koda-The-Fox/groepC-BP5"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
            dialOpnBroweser.setResult(ButtonType.NO); // Default the ButtonType result to 'NO'.
        });
        gpSocials.add(hlGitHub, 2, 2, 1, 1);

        borderPane.setCenter(wrapperBox);
        borderPane.setPadding(new Insets(20));
        borderPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));

    }
}
