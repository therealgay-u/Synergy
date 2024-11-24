package tests;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePage_4 extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Header Section
        Text headerText = new Text("SMART STREET LIGHT SYSTEM");
        headerText.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        VBox header = new VBox(headerText);
        header.setStyle("-fx-padding: 10; -fx-alignment: center; -fx-border-color: black; -fx-border-width: 0 0 1 0;");

        // Menu Bar (using TabPane instead of buttons)
        TabPane tabPane = new TabPane();

        // Create tabs
        Tab contactUsTab = new Tab("Contact Us");
        contactUsTab.setClosable(false);
        contactUsTab.setContent(createTabContent("Contact Us"));

        Tab homeTab = new Tab("Home");
        homeTab.setClosable(false);
        homeTab.setContent(createTabContent("Home Page Content"));

        Tab maintenanceTab = new Tab("Maintenance");
        maintenanceTab.setClosable(false);
        maintenanceTab.setContent(createTabContent("Maintenance Page"));

        Tab profileTab = new Tab("Profile");
        profileTab.setClosable(false);
        profileTab.setContent(createTabContent("Profile Page"));

        // Add tabs to the TabPane
        tabPane.getTabs().addAll(contactUsTab, homeTab, maintenanceTab, profileTab);

        // Set the default tab
        tabPane.getSelectionModel().select(1); // "Home" tab is selected by default

        // Main Content (About Us)
        Label aboutUsLabel = new Label("About Us");
        aboutUsLabel.setStyle("-fx-font-size: 16;");
        VBox mainContent = new VBox(aboutUsLabel);
        mainContent.setStyle("-fx-alignment: center; -fx-padding: 20;");

        // Combine Header, TabPane, and Main Content in BorderPane
        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(tabPane);

        // Set Scene
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home Page");
        primaryStage.show();
    }

    // Method to create content for each tab
    private VBox createTabContent(String contentText) {
        VBox tabContent = new VBox();
        Label contentLabel = new Label(contentText);
        contentLabel.setStyle("-fx-font-size: 18;");
        contentLabel.setStyle("-fx-alignment: center; -fx-padding: 20;");
        tabContent.getChildren().add(contentLabel);
        return tabContent;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
