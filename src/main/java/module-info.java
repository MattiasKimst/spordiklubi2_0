module com.example.spordiklubi2_0 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.spordiklubi2_0 to javafx.fxml;
    exports com.example.spordiklubi2_0;
}