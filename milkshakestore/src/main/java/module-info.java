module com.ufabc.poo {
    requires javafx.controls;
    requires javafx.fxml;
    requires eu.lestard.easydi;
    requires javax.inject;
    requires java.sql;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens com.ufabc.poo.controllers to javafx.fxml;

    exports com.ufabc.poo;
    exports com.ufabc.poo.services;
    exports com.ufabc.poo.services.interfaces;
    exports com.ufabc.poo.domain;
    exports com.ufabc.poo.domain.abstractions;
    exports com.ufabc.poo.helpers;
    exports com.ufabc.poo.controllers;
}
