package tests;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePage_5 extends Application {

    private final SimpleStringProperty mainContentText = new SimpleStringProperty("Welcome to the Smart Street Light System!");

    private void setTabStyle(Tab tab) {
        tab.setStyle("-fx-font-size: 16; -fx-padding: 10 20; -fx-background-color: lightblue;");
    }

    @Override
    public void start(Stage primaryStage) {
        // Header TabPane
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle("-fx-tab-min-width: 120px; -fx-tab-max-width: 200px; -fx-padding: 10;");
        // Bind TabPane width to the Scene's width for dynamic resizing
        tabPane.prefWidthProperty().bind(primaryStage.widthProperty());


        // Home Tab
        Tab homeTab = new Tab("Home");
        homeTab.setContent(createTabContent("Home: Welcome to the Smart Street Light System!"));
        setTabStyle(homeTab);

        // Contact Us Tab
        Tab contactTab = new Tab("Contact Us");
        contactTab.setContent(createTabContent("Contact Us: Reach out to us anytime!"));
        setTabStyle(contactTab);

        // Profile Tab
        Tab profileTab = new Tab("Profile");
        profileTab.setContent(createTabContent("Profile: View and manage your account details!"));
        setTabStyle(profileTab);

        // Maintenance Tab with Dropdown
        Tab maintenanceTab = new Tab("Maintenance");
        VBox maintenanceContent = new VBox(10);
        maintenanceContent.setStyle("-fx-padding: 20; -fx-alignment: center;");
        setTabStyle(maintenanceTab);

        Label maintenanceLabel = new Label("Select a street to view details:");
        ComboBox<String> streetDropdown = new ComboBox<>();
        streetDropdown.getItems().addAll("Street 1", "Street 2", "Street 3", "Street 4");
        Label selectedStreetLabel = new Label("Street Details: No street selected.");

        streetDropdown.setOnAction(event -> {
            String selectedStreet = streetDropdown.getValue();
            selectedStreetLabel.setText("Street Details: Maintenance details for " + selectedStreet);
            mainContentText.set("Street Details: Maintenance details for " + selectedStreet);
        });

        maintenanceContent.getChildren().addAll(maintenanceLabel, streetDropdown, selectedStreetLabel);
        maintenanceTab.setContent(maintenanceContent);

        // Add Tabs
        tabPane.getTabs().addAll(contactTab, homeTab, maintenanceTab, profileTab);

        tabPane.tabMinWidthProperty().bind(primaryStage.widthProperty().divide(tabPane.getTabs().size()));
        tabPane.tabMaxWidthProperty().bind(primaryStage.widthProperty().divide(tabPane.getTabs().size()));


        // Main Content
        Label mainContentLabel = new Label();
        mainContentLabel.textProperty().bind(mainContentText);
        mainContentLabel.setStyle("-fx-font-size: 16; -fx-padding: 20;");

        // Layout
        BorderPane root = new BorderPane();
        root.setTop(tabPane);
        root.setCenter(mainContentLabel);

        // Scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Smart Street Light System");
        primaryStage.show();
    }

    private VBox createTabContent(String text) {
        VBox content = new VBox(10);
        content.setStyle("-fx-padding: 20; -fx-alignment: center;");
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 16;");
        content.getChildren().add(label);
        return content;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
