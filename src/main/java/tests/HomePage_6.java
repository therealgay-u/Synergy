package HomePage;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdbc.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomePage_6 extends Application {

    private ComboBox<String> tableDropdown;
    private Label selectedTableLabel;
    private TableView<TableRow> tableView;

    // Helper method to set tab style
    private void setTabStyle(Tab tab) {
        tab.setStyle("-fx-font-size: 16; -fx-padding: 10 20; -fx-background-color: lightblue;");
    }

    // Create content for tabs
    private VBox createTabContent(String contentText) {
        VBox tabContent = new VBox();
        tabContent.setStyle("-fx-alignment: center; -fx-padding: 20;");
        Label contentLabel = new Label(contentText);
        contentLabel.setStyle("-fx-font-size: 18; -fx-text-alignment: center;");
        tabContent.getChildren().add(contentLabel);
        return tabContent;
    }

    // Fetch list of table names from the database
    private ObservableList<String> getTableNamesFromDatabase() {
        ObservableList<String> tableNames = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection()){
             DatabaseMetaData metadata = conn.getMetaData();
             ResultSet rs = metadata.getTables(null, null, "%", new String[]{"TABLE"});

            while (rs.next()) {
                tableNames.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableNames;
    }

    // Fetch data for the selected table
    private ObservableList<TableRow> getTableDataForTable(String tableName) {
        ObservableList<TableRow> tableData = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {

            // Dynamically fetch column count
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Dynamically create table columns
            tableView.getColumns().clear(); // Clear existing columns
            for (int i = 1; i <= columnCount; i++) {
                final int columnIndex = i;
                TableColumn<TableRow, String> column = new TableColumn<>(metaData.getColumnName(i));
                column.setCellValueFactory(cellData -> cellData.getValue().getRowData().get(columnIndex - 1));
                tableView.getColumns().add(column);
            }

            // Fetch data from the table
            while (rs.next()) {
                List<String> rowData = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.add(rs.getString(i));
                }
                tableData.add(new TableRow(rowData));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableData;
    }

    @Override
    public void start(Stage primaryStage) {
        Text headerText = new Text("SMART STREET LIGHT SYSTEM");
        headerText.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        VBox header = new VBox(headerText);
        header.setStyle("-fx-padding: 10; -fx-alignment: center; -fx-border-color: black; -fx-border-width: 0 0 1 0;");

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle("-fx-padding: 10;");

        Tab homeTab = new Tab("Home");
        homeTab.setContent(createTabContent("Home: Welcome to the Smart Street Light System!"));
        setTabStyle(homeTab);

        Tab contactTab = new Tab("Contact Us");
        contactTab.setContent(createTabContent("Contact Us: Reach out to us anytime!"));
        setTabStyle(contactTab);

        Tab profileTab = new Tab("Profile");
        profileTab.setContent(createTabContent("Profile: View and manage your account details!"));
        setTabStyle(profileTab);

        // Maintenance Tab
        Tab maintenanceTab = new Tab("Maintenance");
        VBox maintenanceContent = new VBox(10);
        maintenanceContent.setStyle("-fx-padding: 20; -fx-alignment: center;");
        setTabStyle(maintenanceTab);

        Label maintenanceLabel = new Label("Select a table to view details:");
        tableDropdown = new ComboBox<>();
        selectedTableLabel = new Label("Table Details: No table selected.");

        tableDropdown.setOnAction(event -> {
            String selectedTable = tableDropdown.getValue();
            selectedTableLabel.setText("Table Details: Showing data for " + selectedTable);

            // Load the table data for the selected table
            ObservableList<TableRow> tableData = getTableDataForTable(selectedTable);
            tableView.setItems(tableData);
        });

        // Get table names from the database and populate the dropdown
        ObservableList<String> tableNames = getTableNamesFromDatabase();
        tableDropdown.setItems(tableNames);

        // TableView setup
        tableView = new TableView<>();
        tableView.setEditable(true);

        // The columns will be dynamically added based on the selected table's columns
        tableView.setPlaceholder(new Label("Select a table to see its data"));

        maintenanceContent.getChildren().addAll(maintenanceLabel, tableDropdown, selectedTableLabel, tableView);
        maintenanceTab.setContent(maintenanceContent);

        tabPane.getTabs().addAll(contactTab, homeTab, maintenanceTab, profileTab);

        tabPane.tabMinWidthProperty().bind(primaryStage.widthProperty().divide(4.8));
        tabPane.tabMaxWidthProperty().bind(primaryStage.widthProperty().divide(4.8));

        tabPane.getSelectionModel().select(1);

        Label aboutUsLabel = new Label("About Us");
        aboutUsLabel.setStyle("-fx-font-size: 16;");
        VBox mainContent = new VBox(aboutUsLabel);
        mainContent.setStyle("-fx-alignment: center; -fx-padding: 20;");

        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(tabPane);

        tabPane.prefWidthProperty().bind(primaryStage.widthProperty());

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home Page");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Helper class for representing each row in a table
    public static class TableRow {
        private final ObservableList<SimpleStringProperty> rowData;

        public TableRow(List<String> rowData) {
            this.rowData = FXCollections.observableArrayList();
            for (String data : rowData) {
                this.rowData.add(new SimpleStringProperty(data));
            }
        }

        public ObservableList<SimpleStringProperty> getRowData() {
            return rowData;
        }
    }
}
