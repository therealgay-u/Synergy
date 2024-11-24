package tests;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HomePage_3 extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create a VBox to hold the header and the TabPane
        VBox root = new VBox();

        // Create the header with the title
        Label titleLabel = new Label("SMART STREET LIGHT SYSTEM");
        titleLabel.setFont(new Font("Arial", 36));
        titleLabel.setStyle("-fx-font-weight: bold; -fx-padding: 20px; -fx-text-fill: blue; -fx-alignment: center;");

        // Create the TabPane for tabs
        TabPane tabPane = new TabPane();

        // Create individual tabs
        Tab homeTab = new Tab("Home");
        homeTab.setContent(createHomePage()); // Home content

        Tab maintenanceTab = new Tab("Maintenance");
        maintenanceTab.setContent(createMaintenancePage()); // Maintenance content

        Tab profileTab = new Tab("Profile");
        profileTab.setContent(createProfilePage()); // Profile content

        Tab aboutUsTab = new Tab("About Us");
        aboutUsTab.setContent(createAboutUsPage()); // About Us content

        // Add tabs to the TabPane
        tabPane.getTabs().addAll(homeTab, maintenanceTab, profileTab, aboutUsTab);

        // Disable tab closing (as in your design)
        homeTab.setClosable(false);
        maintenanceTab.setClosable(false);
        profileTab.setClosable(false);
        aboutUsTab.setClosable(false);

        // Set the default tab
        tabPane.getSelectionModel().select(0); // Select "Home" tab by default

        // Add the title label and TabPane to the VBox
        root.getChildren().addAll(titleLabel, tabPane);

        // Set padding and spacing for the root layout
        root.setSpacing(10);
        root.setStyle("-fx-background-color: white; -fx-padding: 10px;");

        // Create a scene with the root layout
        Scene scene = new Scene(root, 900, 600);

        // Set the stage (window)
        primaryStage.setTitle("Smart Street Light System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to create Home tab content
    private StackPane createHomePage() {
        StackPane homePage = new StackPane();
        Label label = new Label("Welcome to the Home Page");
        label.setFont(new Font("Arial", 24));
        label.setStyle("-fx-text-fill: #333333;");
        homePage.getChildren().add(label);

        // Styling for the Home tab
        homePage.setStyle("-fx-background-color: lightblue; -fx-alignment: center;");
        return homePage;
    }

    // Method to create Maintenance tab content
    private StackPane createMaintenancePage() {
        StackPane maintenancePage = new StackPane();
        Label label = new Label("Welcome to the Maintenance Page");
        label.setFont(new Font("Arial", 24));
        label.setStyle("-fx-text-fill: #333333;");
        maintenancePage.getChildren().add(label);

        // Styling for the Maintenance tab
        maintenancePage.setStyle("-fx-background-color: lightgreen; -fx-alignment: center;");
        return maintenancePage;
    }

    // Method to create Profile tab content
    private StackPane createProfilePage() {
        StackPane profilePage = new StackPane();
        Label label = new Label("Welcome to the Profile Page");
        label.setFont(new Font("Arial", 24));
        label.setStyle("-fx-text-fill: #333333;");
        profilePage.getChildren().add(label);

        // Styling for the Profile tab
        profilePage.setStyle("-fx-background-color: lightyellow; -fx-alignment: center;");
        return profilePage;
    }

    // Method to create About Us tab content
    private StackPane createAboutUsPage() {
        StackPane aboutUsPage = new StackPane();
        Label label = new Label("About the Smart Street Light System");
        label.setFont(new Font("Arial", 24));
        label.setStyle("-fx-text-fill: #333333;");
        aboutUsPage.getChildren().add(label);

        // Styling for the About Us tab
        aboutUsPage.setStyle("-fx-background-color: lightcoral; -fx-alignment: center;");
        return aboutUsPage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
