package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.control.LoginController;
import com.waterkersapp.waterkersapp.model.Gebruiker;
import com.waterkersapp.waterkersapp.util.SQLFunctions;
import javafx.css.Style;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.waterkersapp.waterkersapp.MainWindow.ICON;

public class Login {

    // Variables
    ///////////////////////[Window]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] WINDOW_SIZE = {800, 450}; // Default: 800 * 450;
    Color backgroundColor = Color.web("#BADC8F");

    ///////////////////////[Logo]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] ICON_SIZE = {100, 100}; //Default: 100;

    ///////////////////////[Input fields]\\\\\\\\\\\\\\\\\\\\\
    // Border
    private static final Color TXTF_BRDR_CLR = Color.web("906B4E"); // RGB: 113, 156, 64
    private static final double TXTF_BRDR_WDTH = 5;
    private static final double TXTF_BRDR_RADIUS = 5;
    // Background
    private static final double TXTF_BCGND_RADIUS = TXTF_BRDR_RADIUS;

    ///////////////////////[Login Button]\\\\\\\\\\\\\\\\\\\\\
    // Font
    private static final double BTN_FNT_SIZE = 16; // default: 14
    private static final String BTN_FNT_WGHT = "bold";
    private static final Color BTN_FNT_CLR = Color.web("#FFF"); // RGB: 255, 255, 255
    private static final String BTN_FNT_FAM = "Arial";
    // Border
    private static final Color BTN_BRDR_CLR = Color.web("#719C40"); // RGB: 113, 156, 64
    private static final double BTN_BRDR_RADIUS = 100;
    private static final double BTN_BRDR_WDTH = TXTF_BRDR_WDTH;
    // Background
    private static final Color BTN_BCK_CLR = Color.web("#A07E63"); // RGB: 160, 126, 99
    // Geometry
    private static final double BTN_RADIUS = BTN_BRDR_RADIUS;
    private static final double[] BTN_SIZE = {90, 90}; // Width x Height


    BorderPane borderPane = new BorderPane();

    public static Stage stage;

    public static void create(Login login) {
        stage = new Stage();
        stage.setTitle("Login");
        stage.getIcons().add(ICON);

        double Min_width = 533.333;
        double Min_height = 300;

        Scene scene = new Scene(login.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        scene.getStylesheets().addAll(SensorOverview.class.getResource("/com/waterkersapp/css/GlobalStyleSheet.css").toString(), SensorOverview.class.getResource("/com/waterkersapp/css/LoginStyle.css").toString());

        stage.setScene(scene);
        stage.setMinWidth(Min_width);
        //stage.setMaxWidth(Max_width);
        stage.setMinHeight(Min_height);
        //stage.setMaxHeight(Max_height);

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

    private Gebruiker User = new Gebruiker();

    private Boolean LoginAccepted = false;

    private void setLoginAccepted(boolean state){
        LoginAccepted = state;
    }

    Boolean getLoginAccepted(){
        return LoginAccepted;
    }

    private void setUser(Gebruiker user){
        User = user;
    }

    Gebruiker getUser(){
        return User;
    }

    public Login() {

        ImageView imgLogo = new ImageView(ICON);

        TextField tbxUsername = new TextField();
        tbxUsername.setPromptText("Gebruikersnaam"); //to set the hint text

        PasswordField tbxPassword = new PasswordField();
        tbxPassword.setPromptText("Wachtwoord"); //to set the hint text
        tbxPassword.setStyle(tbxUsername.getStyle());
        System.out.println("TextFields: \n" + tbxPassword.getStyle().replace(";", ";\n")); // Preview the style parameters in the console

        /*
        Hyperlink hlPassword = new Hyperlink("Reset Password");
        hlPassword.setOnAction(event -> Reset_Pass.create());
         */
        Text txtSysMessage = new Text ("\n"); // set a new line so the label is visible by using an empty line./
        txtSysMessage.setFill(Color.RED);


        Button btnLogin = new Button("Login");
        btnLogin.requestFocus();
        btnLogin.setOnAction(event -> {

            if (tbxUsername.getText().isEmpty()){
                txtSysMessage.setFill(Color.RED);
                txtSysMessage.setText("Gebruikersnaam mag niet leeg zijn.");
            }
            else if (tbxPassword.getText().isEmpty()){
                txtSysMessage.setFill(Color.RED);
                txtSysMessage.setText("Wachtwoord mag niet leeg zijn.");
            }
            else {// If both fields are not empty continue with the validation.
                if (!LoginController.validateLogin(tbxUsername.getText(), tbxPassword.getText())) {
                    setLoginAccepted(false);

                    txtSysMessage.setFill(Color.RED);
                    txtSysMessage.setText("Login failed, check if the entered\ninformation is correct and try again.");
                } else {
                    setLoginAccepted(true);

                    txtSysMessage.setFill(Color.GREEN);
                    txtSysMessage.setText("Login accepted, this window should\nexit automatically.");


                    Menu menu = new Menu(LoginController.getUser(tbxUsername.getText(), tbxPassword.getText()));
                    Menu.create(menu);

                    stage.close(); // Close the window
                }
            }
        });
        btnLogin.setDefaultButton(true);










        VBox loginBox = new VBox(tbxUsername, tbxPassword);
        HBox buttonsBox = new HBox(btnLogin);
        VBox statusBox = new VBox(txtSysMessage);
        VBox wrapperBox = new VBox(imgLogo, loginBox, statusBox, buttonsBox);

        imgLogo.fitWidthProperty().bind(wrapperBox.widthProperty().divide(8));
        imgLogo.fitHeightProperty().bind(wrapperBox.widthProperty().divide(8));


        loginBox.setSpacing(10);
        loginBox.setMaxWidth(400);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.prefWidthProperty().bind(wrapperBox.widthProperty().divide(50));
        loginBox.prefHeightProperty().bind(wrapperBox.heightProperty().divide(50));

        statusBox.setMaxWidth(400);
        statusBox.setAlignment(Pos.CENTER);
        statusBox.prefWidthProperty().bind(wrapperBox.widthProperty());
        statusBox.prefHeightProperty().bind(wrapperBox.heightProperty().divide(50));

        buttonsBox.setSpacing(10);
        buttonsBox.setMaxWidth(400);
        buttonsBox.setAlignment(Pos.TOP_CENTER);
        buttonsBox.prefWidthProperty().bind(wrapperBox.widthProperty());
        buttonsBox.prefHeightProperty().bind(wrapperBox.heightProperty().divide(50));

        wrapperBox.setAlignment(Pos.CENTER);
        wrapperBox.setSpacing(10);
        wrapperBox.setPadding(new Insets(50, 300, 0, 300));
        wrapperBox.prefWidthProperty().bind(borderPane.widthProperty());
        wrapperBox.prefHeightProperty().bind(borderPane.heightProperty().divide(50));

        borderPane.setCenter(wrapperBox);
        borderPane.setBackground(new Background(new BackgroundFill(backgroundColor, null, null)));
    }
}
