module com.greengenie.green_genie {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires commons.dbcp2;
    requires java.management;
    requires java.desktop; // Important, if not specified causes 'java: cannot access javax.management.MBeanRegistration'


    opens com.greengenie.green_genie to javafx.fxml;
    exports com.greengenie.green_genie;
}