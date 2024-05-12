module com.example.l9 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens com.example.l9 to javafx.fxml;
    opens com.example.l9.client.controllers to javafx.fxml;
    exports com.example.l9;
    opens com.example.l9.client to javafx.fxml;
}