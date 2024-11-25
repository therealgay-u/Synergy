package tests;
/*
import Login.LoginPage;
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
import jdbc.AdminService;
import jdbc.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HomePage_trial extends Application {

    private ComboBox<String> tableDropdown;
    private Label selectedTableLabel;
    private TableView<TableRow> tableView;

    private void setTabStyle(Tab tab) {
        tab.setStyle("-fx-font-size: 16; -fx-padding: 10 20; -fx-background-color: lightblue;");
    }

    private VBox createTabContent(String contentText) {
        VBox tabContent = new VBox();
        tabContent.setStyle("-fx-alignment: center; -fx-padding: 20;");
        Label contentLabel = new Label(contentText);
        contentLabel.setStyle("-fx-font-size: 18; -fx-text-alignment: center;");
        tabContent.getChildren().add(contentLabel);
        return tabContent;
    }

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

    private ObservableList<String> getTableNamesFromDatabase() {
        ObservableList<String> tableNames = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection()) {
            DatabaseMetaData metadata = conn.getMetaData();
            ResultSet rs = metadata.getTables(null, null, "%", new String[]{"TABLE"});

            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                if (tableName != null) {
                    tableNames.add(tableName);
                }
            }

            // Filter tables based on the city name in adminmap
            String city = AdminService.adminmap.get("city");
            if (city != null) {
                tableNames.removeIf(s -> !s.toLowerCase().contains(city.toLowerCase()));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableNames;
    }

    private ObservableList<javafx.scene.control.TableRow> getTableDataForTable(String tableName) {
        ObservableList<javafx.scene.control.TableRow> tableData = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Clear existing columns
            tableView.getColumns().clear();

            // Dynamically create table columns based on metadata
            for (int i = 1; i <= columnCount; i++) {
                final int columnIndex = i - 1; // Adjust for zero-based index
                TableColumn<javafx.scene.control.TableRow, String> column = new TableColumn<>(metaData.getColumnName(i));
                column.setCellValueFactory(cellData -> cellData.getValue().getRowData().get(columnIndex));
                tableView.getColumns().add(column);
            }

            // Populate table data
            while (rs.next()) {
                List<String> rowData = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.add(rs.getString(i));
                }
                tableData.add(new javafx.scene.control.TableRow(rowData));
            }

            // Add custom columns based on the table
            if (tableName.equalsIgnoreCase(AdminService.adminmap.get("city"))) {
                addEditColumn();
            } else {
                addActionColumn();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            tableView.setPlaceholder(new Label("Error loading data for the table."));
        }

        return tableData;
    }

    private void addEditColumn() {
        TableColumn<javafx.scene.control.TableRow, Void> editColumn = new TableColumn<>("Edit");
        editColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    javafx.scene.control.TableRow currentRow = getTableView().getItems().get(getIndex());
                    editButton.setOnAction(e -> {
                        // Simulate an edit operation (e.g., update a specific column value)
                        currentRow.getRowData().get(0).set("Updated Value");
                        tableView.refresh();
                    });
                    setGraphic(editButton);
                }
            }
        });
        tableView.getColumns().add(editColumn);
    }

    private void addActionColumn() {
        TableColumn<javafx.scene.control.TableRow, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button actionButton = new Button("Resolve");

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    javafx.scene.control.TableRow currentRow = getTableView().getItems().get(getIndex());
                    String status = currentRow.getRowData().get(1).get(); // Assuming column 2 holds the status
                    if ("abnormality".equalsIgnoreCase(status)) {
                        actionButton.setDisable(false);
                        actionButton.setOnAction(e -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Email sent to resolve the issue!");
                            alert.showAndWait();
                        });
                    } else {
                        actionButton.setDisable(true);
                    }
                    setGraphic(actionButton);
                }
            }
        });
        tableView.getColumns().add(actionColumn);
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

        Label cityLabel = new Label("City: " + AdminService.adminmap.get("city"));
        Label usernameLabel = new Label("Username: " + AdminService.adminmap.get("username"));
        Label nameLabel = new Label("Name: " + AdminService.adminmap.get("name"));
        Label emailLabel = new Label("Email: " + AdminService.adminmap.get("email_id"));

        Button logOutButton = new Button("Log Out");
        logOutButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;");
        logOutButton.setOnMouseEntered(e -> logOutButton.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));
        logOutButton.setOnMouseExited(e -> logOutButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));

        logOutButton.setOnAction(e -> {
            AdminService.adminmap.clear();
            Stage stage = (Stage) logOutButton.getScene().getWindow();
            LoginPage loginPage = new LoginPage();
            try {
                loginPage.start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Failed to load Login Page.");
                alert.showAndWait();
            }
        });

        VBox profileBox = new VBox(10, cityLabel, nameLabel, usernameLabel, emailLabel, logOutButton);
        profileBox.setStyle("-fx-padding: 20; -fx-alignment: center-left; -fx-font-size: 14px;");

        profileTab.setContent(profileBox);
        setTabStyle(profileTab);

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

            ObservableList<TableRow> tableData = getTableDataForTable(selectedTable);

            if (!tableData.isEmpty()) {
                tableView.setItems(tableData);
            } else {
                tableView.getColumns().clear();
                tableView.setPlaceholder(new Label("No data available for the selected table."));
            }
        });

        ObservableList<String> tableNames = getTableNamesFromDatabase();
        tableDropdown.setItems(tableNames);

        tableView = new TableView<>();
        tableView.setEditable(true);
        tableView.setPlaceholder(new Label("Select a table to see its data"));

        maintenanceContent.getChildren().addAll(maintenanceLabel, tableDropdown, selectedTableLabel, tableView);
        maintenanceTab.setContent(maintenanceContent);

        tabPane.getTabs().addAll(contactTab, homeTab, maintenanceTab, profileTab);

        tabPane.tabMinWidthProperty().bind(primaryStage.widthProperty().divide(4.8));
        tabPane.tabMaxWidthProperty().bind(primaryStage.widthProperty().divide(4.8));

        tabPane.getSelectionModel().select(1);

        BorderPane root = new BorderPane();
        root.setTop(header);
        root.setCenter(tabPane);

        tabPane.prefWidthProperty().bind(primaryStage.widthProperty());

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home Page");
        primaryStage.show();
    }
}
*/
