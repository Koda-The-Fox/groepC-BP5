package com.greengenie.green_genie;

import com.greengenie.green_genie.model.SQLConnection;
import com.greengenie.green_genie.util.JsonMethods;
import com.greengenie.green_genie.view.Menu;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.Objects;

public class MainWindow extends Application {

    public static final Image ICON = new Image(Objects.requireNonNull(MainWindow.class.getResourceAsStream("/com/greengenie/media/images/GreenGenie-Logo(256x).png")));

    public static void main(String[] args) {
        launch(args);
    }


    public static File DOCUMENTS_FOLDER = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/GroepC/WaterkersApp");
    public static File SQLFILE = new File(DOCUMENTS_FOLDER.getPath() + "/SQLConnection.json");

    public static SQLConnection SQLconnection = new SQLConnection();

    //public static Alert alrtNOSQLData = new Alert(Alert.AlertType.ERROR, "De ingevulde SQl data is niet compleet.\nOpen het bestand: '"+SQLFILE+"'", ButtonType.OK);

    // SQL JSON
    static {
        // Init check

        // Validate if Documents files exists, Otherwise create a blank one.

        // Get the save location for our documents
        DOCUMENTS_FOLDER.mkdirs(); // If the folders don't exist make those.
        System.out.println("DocumentsFolder: " + DOCUMENTS_FOLDER);

        // SQL
        try {
            if (!SQLFILE.exists()){
                SQLFILE.createNewFile(); // if file already exists will do nothing.
                JsonMethods.setJSONToFile(SQLconnection.toJSONObject(), SQLFILE.getPath());
            }
            JSONObject JsobSQL = JsonMethods.getJSONFromFile(SQLFILE.getPath());
            SQLconnection.fromnJSONObject(JsobSQL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void start(Stage primaryStage) {

        try {
            if (SQLconnection.filled()) {
                // MENU
                Menu menu = new Menu();
                Menu.create(menu);
            } else {
                new Alert(Alert.AlertType.ERROR, "De ingevulde SQl data is niet ingevuld of niet compleet.\nOpen het bestand: '" + SQLFILE + "', En vul de juiste SQL data in.", ButtonType.OK).showAndWait();
                System.exit(0);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }


}