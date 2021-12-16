module com.greengenie.green_genie {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.greengenie.green_genie to javafx.fxml;
    exports com.greengenie.green_genie;
}