module com.waterkersapp.waterkersapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires commons.dbcp2;
    requires java.management;
    requires java.desktop; // Important, if not specified causes 'java: cannot access javax.management.MBeanRegistration'


    opens com.waterkersapp.waterkersapp to javafx.fxml;
    exports com.waterkersapp.waterkersapp;

}