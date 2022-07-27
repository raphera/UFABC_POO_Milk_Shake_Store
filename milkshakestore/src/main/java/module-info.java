module com.ufabc.poo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires eu.lestard.easydi;
    requires javax.inject;
    requires java.sql;

    opens com.ufabc.poo.controllers to javafx.fxml;
    exports com.ufabc.poo;
}
