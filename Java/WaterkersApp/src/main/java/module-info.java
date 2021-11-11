module com.waterkersapp.waterkersapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.waterkersapp.waterkersapp to javafx.fxml;
    exports com.waterkersapp.waterkersapp;
}