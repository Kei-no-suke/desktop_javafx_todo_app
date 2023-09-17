module com.keinosuke.todoapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;

    opens com.keinosuke.todoapp to javafx.fxml;
    exports com.keinosuke.todoapp;
    exports com.keinosuke.todoapp.controllers;
    exports com.keinosuke.todoapp.models;
    exports com.keinosuke.todoapp.views;
}