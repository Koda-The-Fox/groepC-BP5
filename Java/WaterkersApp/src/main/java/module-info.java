module com.waterkersapp.waterkersapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.waterkersapp.waterkersapp to javafx.fxml;
    exports com.waterkersapp.waterkersapp;
}