module com.aims.group19 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.aims.group19 to javafx.fxml;
    opens com.aims.group19.views to javafx.fxml;
    opens com.aims.group19.views.home to javafx.fxml;
    opens com.aims.group19.views.popup to javafx.fxml;
    opens com.aims.group19.views.cart to javafx.fxml;
    opens com.aims.group19.views.shipping to javafx.fxml;
    opens com.aims.group19.views.invoice to javafx.fxml;
    opens com.aims.group19.views.payment to javafx.fxml;

    exports com.aims.group19;
}