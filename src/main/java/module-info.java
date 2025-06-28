module com.aims.son {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.aims.son to javafx.fxml;
    opens com.aims.son.views to javafx.fxml;
    opens com.aims.son.views.home to javafx.fxml;
    opens com.aims.son.views.popup to javafx.fxml;
    opens com.aims.son.views.cart to javafx.fxml;
    opens com.aims.son.views.shipping to javafx.fxml;
    opens com.aims.son.views.invoice to javafx.fxml;
    opens com.aims.son.views.payment to javafx.fxml;

    exports com.aims.son;
}