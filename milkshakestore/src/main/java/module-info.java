module com.ufabc.poo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires com.google.guice;
    requires javax.inject;
    requires java.sql;

    opens com.ufabc.poo to javafx.fxml;
    exports com.ufabc.poo;
}
