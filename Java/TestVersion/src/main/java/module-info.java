module groepc.application.application {
    requires javafx.controls;
    requires javafx.fxml;


    opens groepc.application.application to javafx.fxml;
    exports groepc.application.application;
}