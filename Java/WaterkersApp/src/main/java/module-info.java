module com.waterkersapp.waterkersapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires commons.dbcp2;
    requires java.management; // Important, if not specified causes 'java: cannot access javax.management.MBeanRegistration'


    opens com.waterkersapp.waterkersapp to javafx.fxml;
    exports com.waterkersapp.waterkersapp;

    // An error happened using value factories in table views.
    /*
        java.lang.IllegalAccessException: module javafx.base cannot access class com.waterkersapp.waterkersapp.model.ArduinoLocatie (in module com.waterkersapp.waterkersapp) because module com.waterkersapp.waterkersapp does not open com.waterkersapp.waterkersapp.model to javafx.base
     */
    // The solution was simple see the code beneath.
    // Fix source: https://stackoverflow.com/questions/67372505/java-lang-illegalaccessexception-module-javafx-base-cannot-access-class-sample
    opens com.waterkersapp.waterkersapp.model to javafx.fxml;
    exports com.waterkersapp.waterkersapp.model;
}