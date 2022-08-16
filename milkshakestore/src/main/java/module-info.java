
module com.ufabc.poo {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive javafx.base;
    requires transitive eu.lestard.easydi;
    requires javax.inject;
    requires java.sql;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.controlsfx.controls;

    opens com.ufabc.poo.controllers to javafx.fxml, javafx.base;

    exports com.ufabc.poo;
    exports com.ufabc.poo.services;
    exports com.ufabc.poo.services.interfaces;
    exports com.ufabc.poo.domain;
    exports com.ufabc.poo.domain.abstractions;
    exports com.ufabc.poo.helpers;
    exports com.ufabc.poo.controllers;
}
