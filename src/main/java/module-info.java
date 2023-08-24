module com.example.project1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    requires org.seleniumhq.selenium.api;
    requires org.seleniumhq.selenium.chrome_driver;
    requires org.seleniumhq.selenium.firefox_driver;
    requires com.google.common;
    requires dev.failsafe.core;
    requires org.slf4j;


    opens com.example.project1 to javafx.fxml;
    exports com.example.project1;
}