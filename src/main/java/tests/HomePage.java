package tests;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePage extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Header Section
        Text headerText = new Text("SMART STREET LIGHT SYSTEM");
        headerText.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        HBox header = new HBox(headerText);
        header.setStyle("-fx-padding: 10; -fx-alignment: center; -fx-border-color: black; -fx-border-width: 0 0 1 0;");

        // Menu Bar
        Button contactUsButton = new Button("Contact us");
        Button homeButton = new Button("Home");
        Button maintenanceButton = new Button("Maintenance");
        Button profileButton = new Button("Profile");

        HBox menuBar = new HBox(10, contactUsButton, homeButton, maintenanceButton, profileButton);
        menuBar.setStyle("-fx-padding: 10; -fx-alignment: center; -fx-border-color: black; -fx-border-width: 0 0 1 0;");

        // Main Content (About Us)
        Text aboutUsText = new Text("About us");
        aboutUsText.setStyle("-fx-font-size: 16;");
        VBox mainContent = new VBox(aboutUsText);
        mainContent.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Combine All in BorderPane
        BorderPane root = new BorderPane();
        root.setTop(new VBox(header, menuBar));
        root.setCenter(mainContent);

        // Set Scene
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home Page");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
