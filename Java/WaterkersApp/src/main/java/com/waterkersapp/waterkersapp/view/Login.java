package com.waterkersapp.waterkersapp.view;

import com.waterkersapp.waterkersapp.control.LoginController;
import com.waterkersapp.waterkersapp.model.Gebruiker;
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

import java.util.regex.Pattern;

import static com.waterkersapp.waterkersapp.MainWindow.ICON;

public class Login {

    // Variables
    ///////////////////////[Window]\\\\\\\\\\\\\\\\\\\\\
    private static final double[] WINDOW_SIZE = {800, 450}; // Default: 800 * 450;
    Color backgroundColor = Color.web("#BADC8F");

    BorderPane borderPane = new BorderPane();


    int FieldsMaxWidth = 300;


    /* REGULAR EXPRESSION */
    // Disallow: ';\n\r\t
    // Do not start or end with a space
    private static final Pattern negativeREGEXSQLInput = Pattern.compile("^((.*[';\n\r\t].*).)*$|^ .*$|^.* $");


    public static Stage stage;

    public static void create(Login login) {
        stage = new Stage();
        stage.setTitle("Login");
        stage.getIcons().add(ICON);

        double Min_width = 533.333;
        double Min_height = 300;

        Scene scene = new Scene(login.getParent(), (WINDOW_SIZE[0]), (WINDOW_SIZE[1]));
        // set the styles for the scene
        scene.getStylesheets().addAll(Login.class.getResource("/com/waterkersapp/css/GlobalStyleSheet.css").toString(), Login.class.getResource("/com/waterkersapp/css/LoginStyle.css").toString());

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

        Text txtSysMessage = new Text ("\n\n"); // set a new line so the label is visible by using an empty line./
        txtSysMessage.setFill(Color.RED);
        txtSysMessage.setStyle("-fx-font-size: 11");


        // Check name validity
        String regexErr = "%s is niet toegestaan.\nDeze mag geen \\ ; of ' bevatten en\nniet beginnen of sluiten met een spatie.";
        tbxUsername.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSysMessage.setText("\n\n");
            if (negativeREGEXSQLInput.matcher(tbxUsername.getText()).matches()){
                txtSysMessage.setText(String.format(regexErr, "Gebruikersnaam"));
                return;
            }
        });
        tbxPassword.textProperty().addListener((observable, oldValue, newValue) ->{
            txtSysMessage.setText("\n\n");
            if (negativeREGEXSQLInput.matcher(tbxPassword.getText()).matches()){
                txtSysMessage.setText(String.format(regexErr, "Wachtwoord"));
                return;
            }
        });


        Button btnLogin = new Button("Login");
        btnLogin.requestFocus();
        btnLogin.setOnAction(event -> {
            txtSysMessage.setText("\n\n");
            // Check name validity
            if (negativeREGEXSQLInput.matcher(tbxUsername.getText()).matches()){
                txtSysMessage.setText(String.format(regexErr, "Gebruikersnaam"));
                return;
            }else if (negativeREGEXSQLInput.matcher(tbxPassword.getText()).matches()){
                txtSysMessage.setText(String.format(regexErr, "Wachtwoord"));
                return;
            }


            if (tbxUsername.getText().isEmpty()){
                txtSysMessage.setFill(Color.RED);
                txtSysMessage.setText("Gebruikersnaam mag niet leeg zijn.\n\n");
            }
            else if (tbxPassword.getText().isEmpty()){
                txtSysMessage.setFill(Color.RED);
                txtSysMessage.setText("Wachtwoord mag niet leeg zijn.\n\n");
            }
            else {// If both fields are not empty continue with the validation.

                if (!LoginController.validateLogin(tbxUsername.getText(), tbxPassword.getText())) {
                    setLoginAccepted(false);

                    txtSysMessage.setFill(Color.RED);
                    txtSysMessage.setText("Login mislukt,\ncontroleer de ingevulde informatie\ncorrect is en probeer het opnieuw.");
                } else {
                    setLoginAccepted(true);

                    txtSysMessage.setFill(Color.GREEN);
                    txtSysMessage.setText("Login gelukt,\nDit scherm zal automatisch sluiten.\n");


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
        loginBox.setMaxWidth(FieldsMaxWidth);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.prefWidthProperty().bind(wrapperBox.widthProperty().divide(50));
        loginBox.prefHeightProperty().bind(wrapperBox.heightProperty().divide(50));

        statusBox.setMaxWidth(FieldsMaxWidth);
        statusBox.setAlignment(Pos.CENTER);
        statusBox.prefWidthProperty().bind(wrapperBox.widthProperty());
        statusBox.prefHeightProperty().bind(wrapperBox.heightProperty().divide(50));

        buttonsBox.setSpacing(10);
        buttonsBox.setMaxWidth(FieldsMaxWidth);
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
