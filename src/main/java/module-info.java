
module Synergy_GUI {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    requires java.sql;

    exports Login;
    opens Login to javafx.fxml;

    exports HomePage;
    opens HomePage to javafx.fxml;

    exports tests;
    opens tests to javafx.fxml;
}
