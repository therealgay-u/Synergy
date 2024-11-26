package HomePage;
//----------------------------------------------------------------------------------------------------------------------
import Login.LoginPage;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdbc.AdminService;
import jdbc.DatabaseConnection;
import jdbc.update_tables;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//----------------------------------------------------------------------------------------------------------------------

public class HomePage extends Application {

    private ComboBox<String> tableDropdown;
    private Label selectedTableLabel;
    private TableView<HomePage.TableRow> tableView;

//----------------------------------------------------------------------------------------------------------------------


    private void setTabStyle(Tab tab) {
        tab.setStyle("-fx-font-size: 16; -fx-padding: 10 20; -fx-background-color: lightblue;");
    }

//----------------------------------------------------------------------------------------------------------------------


    private VBox createTabContent(String contentText) {
        VBox tabContent = new VBox();
        tabContent.setStyle("-fx-alignment: center; -fx-padding: 20;");
        Label contentLabel = new Label(contentText);
        contentLabel.setStyle("-fx-font-size: 18; -fx-text-alignment: center;");
        tabContent.getChildren().add(contentLabel);
        return tabContent;
    }

//----------------------------------------------------------------------------------------------------------------------


    public static class TableRow {
        private final ObservableList<SimpleStringProperty> rowData;
        public TableRow(List<String> rowData) {
            this.rowData = FXCollections.observableArrayList();
            for (String data : rowData) {
                this.rowData.add(new SimpleStringProperty(data));
            }
        }

//----------------------------------------------------------------------------------------------------------------------


        public ObservableList<SimpleStringProperty> getRowData() {
            return rowData;
        }
    }

//----------------------------------------------------------------------------------------------------------------------


    private ObservableList<String> getTableNamesFromDatabase() {
        ObservableList<String> tableNames = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection()){
            DatabaseMetaData metadata = conn.getMetaData();
            ResultSet rs = metadata.getTables(null, null, "%", new String[]{"TABLE"});

            while (rs.next()) {
                ///*
                String tableName = rs.getString("TABLE_NAME");
                if (tableName != null){
                    tableNames.add(tableName);
                }
            }
            tableNames.removeIf(s -> !s.toLowerCase().contains(AdminService.adminmap.get("city").toLowerCase()));

            return tableNames;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableNames;
    }

//----------------------------------------------------------------------------------------------------------------------


    private ObservableList<HomePage.TableRow> getTableDataForTable(String tableName) {
        ObservableList<HomePage.TableRow> tableData = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query =  "SELECT * FROM " + tableName +" ORDER BY status ASC";
            if (tableName.equalsIgnoreCase(AdminService.adminmap.get("city"))){
                query =  "SELECT * FROM " + tableName;
            }
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            tableView.getColumns().clear();
            for (int i = 1; i <= columnCount; i++) {
                final int columnIndex = i;
                TableColumn<HomePage.TableRow, String> column = new TableColumn<>(metaData.getColumnName(i));
                column.setCellValueFactory(cellData -> cellData.getValue().getRowData().get(columnIndex - 1));
                tableView.getColumns().add(column);
            }

            while (rs.next()) {
                List<String> rowData = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.add(rs.getString(i));
                }
                tableData.add(new HomePage.TableRow(rowData));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return tableData;
    }

//----------------------------------------------------------------------------------------------------------------------

    @Override
    public void start(Stage primaryStage) {
        Text headerText = new Text("SMART STREET LIGHT SYSTEM");
        headerText.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        VBox header = new VBox(headerText);
        header.setStyle("-fx-padding: 10; -fx-alignment: center; -fx-border-color: black; -fx-border-width: 0 0 1 0;");
        //--------------------------------------------------------------------------------------------------------------
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle("-fx-padding: 10;");
        //--------------------------------------------------------------------------------------------------------------
        Tab homeTab = new Tab("Home");
        homeTab.setContent(createTabContent("Home: Welcome to the Smart Street Light System!"));
        setTabStyle(homeTab);
        //--------------------------------------------------------------------------------------------------------------
        Tab contactTab = new Tab("Contact Us");
        contactTab.setContent(createTabContent("Contact Us: Reach out to us anytime!"));
        setTabStyle(contactTab);
        //--------------------------------------------------------------------------------------------------------------
        Tab profileTab = new Tab("Profile");
        Label cityLabel = new Label("City: " + AdminService.adminmap.get("city"));
        cityLabel.setFont(Font.font("SansSerif", 25));
        Label usernameLabel = new Label("Username: " + AdminService.adminmap.get("username"));
        usernameLabel.setFont(Font.font("SansSerif", 25));
        Label nameLabel = new Label("Name: " + AdminService.adminmap.get("name"));
        nameLabel.setFont(Font.font("SansSerif", 25));
        Label emailLabel = new Label("Email: " + AdminService.adminmap.get("email_id"));
        emailLabel.setFont(Font.font("SansSerif", 25));
        //--------------------------------------------------------------------------------------------------------------
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
        profileBox.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-font-size: 14px;");
        profileTab.setContent(profileBox);
        setTabStyle(profileTab);
        //--------------------------------------------------------------------------------------------------------------
        Tab maintenanceTab = new Tab("Maintenance");
        VBox maintenanceContent = new VBox(10);
        maintenanceContent.setStyle("-fx-padding: 20; -fx-alignment: center-left;");
        setTabStyle(maintenanceTab);

        Label maintenanceLabel = new Label("Select a table to view details:");
        tableDropdown = new ComboBox<>();
        selectedTableLabel = new Label("Table Details: No table selected.");

        Button refreshButton = new Button("Refresh");
        refreshButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        refreshButton.setOnAction(event -> {
            String selectedTable = tableDropdown.getValue();
            if (selectedTable != null && !selectedTable.isEmpty()) {
                ObservableList<String> updatedTableNames = getTableNamesFromDatabase();
                tableDropdown.setItems(updatedTableNames);
                ObservableList<HomePage.TableRow> refreshedData = getTableDataForTable(selectedTable);
                tableView.setItems(refreshedData);
                selectedTableLabel.setText("Table Details: Refreshed data for " + selectedTable);
            } else {
                tableView.getItems().clear();
                selectedTableLabel.setText("Table Details: No table selected.");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("No table selected. Please select a table to refresh.");
                alert.showAndWait();
            }
        });

        Button updateButton = new Button("Perform update");
        updateButton.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white; -fx-padding: 5 10;");
        updateButton.setVisible(false);
        updateButton.setOnMouseEntered(e -> updateButton.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));
        updateButton.setOnMouseExited(e -> updateButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));

        updateButton.setOnAction(event -> {
            String selectedTable = tableDropdown.getValue();
            update_tables update =  new update_tables();
            if (selectedTable.equalsIgnoreCase(AdminService.adminmap.get("city"))){
                Stage popupStage = new Stage();
                popupStage.setTitle("Update value");
                Label stnamelbl =  new Label("Enter street name");
                TextField streetnamefield = new TextField();
                streetnamefield.setPromptText("Enter street name");
                Label newnamelbl =  new Label("Enter new name");
                TextField newnamefield = new TextField();
                newnamefield.setPromptText("Enter new name");

                Button updatebtn = new Button("Update");
                updatebtn.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
                updatebtn.setOnAction(submitEvent -> {
                    String streetname = streetnamefield.getText();
                    String newname = newnamefield.getText();
                    if (update.update_mg(streetname , newname)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Value update!");
                        alert.showAndWait();
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setContentText("Error updating value");
                        alert.showAndWait();
                    }
                    popupStage.close();
                });
                VBox popupContent = new VBox(10, stnamelbl,streetnamefield ,newnamelbl, newnamefield, updatebtn);
                popupContent.setStyle("-fx-padding: 20; -fx-alignment: center;");
                Scene popupScene = new Scene(popupContent, 300, 200);
                popupStage.setScene(popupScene);
                popupStage.show();
            }
            else{
                if (update.street_status(selectedTable)){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Mails sent!");
                    alert.showAndWait();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("No mails sent!");
                    alert.showAndWait();
                }
            }
        });

        Button addbutton = new Button("add new street");
        addbutton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        addbutton.setVisible(false);
        addbutton.setOnMouseEntered(e -> addbutton.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));
        addbutton.setOnMouseExited(e -> addbutton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));


        addbutton.setOnAction(event -> {
            String selectedTable = tableDropdown.getValue();
            update_tables update =  new update_tables();
            if (selectedTable.equalsIgnoreCase(AdminService.adminmap.get("city"))){
                Stage popupStage = new Stage();
                popupStage.setTitle("Add street");

                Label newmgnamelbl =  new Label("Enter mw name");
                TextField newmgnamefield = new TextField();
                newmgnamefield.setPromptText("Enter mw name");

                Label newstreetnamelbl =  new Label("Enter street name");
                TextField newstreetnamefield = new TextField();
                newstreetnamefield.setPromptText("Enter street name");

                Button addstreetbtn = new Button("Update");
                addstreetbtn.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
                addstreetbtn.setOnMouseEntered(e -> addstreetbtn.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));
                addstreetbtn.setOnMouseExited(e -> addstreetbtn.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));

                addstreetbtn.setOnAction(submitEvent -> {
                    String newmgname = newmgnamefield.getText();
                    String newstreetname = newstreetnamefield.getText();
                    if (update.addstreet(newmgname , newstreetname)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Street added!");
                        alert.showAndWait();
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setContentText("Error updating value");
                        alert.showAndWait();
                    }
                    popupStage.close();
                });
                VBox popupContent = new VBox(10, newmgnamelbl,newmgnamefield ,newstreetnamelbl, newstreetnamefield, addstreetbtn);
                popupContent.setStyle("-fx-padding: 20; -fx-alignment: center;");
                Scene popupScene = new Scene(popupContent, 300, 200);
                popupStage.setScene(popupScene);
                popupStage.show();
            }
            else{
                Stage popupStage = new Stage();
                popupStage.setTitle("Add new street light");

                Label idlbl =  new Label("Enter id");
                TextField idfield = new TextField();
                idfield.setPromptText("Enter id");

                Button addstreetlightbtn = new Button("Add");
                addstreetlightbtn.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
                addstreetlightbtn.setOnMouseEntered(e -> addstreetlightbtn.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));
                addstreetlightbtn.setOnMouseExited(e -> addstreetlightbtn.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));

                addstreetlightbtn.setOnAction(submitEvent -> {
                    String id = idfield.getText();
                    if (update.addstreetlight(selectedTable, id)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("New street light added!");
                        alert.showAndWait();
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Error adding new street light");
                        alert.showAndWait();
                    }
                    popupStage.close();
                });
                VBox popupContent = new VBox(10, idlbl,idfield, addstreetlightbtn);
                popupContent.setStyle("-fx-padding: 20; -fx-alignment: center;");
                Scene popupScene = new Scene(popupContent, 300, 200);
                popupStage.setScene(popupScene);
                popupStage.show();
            }
        });

        Button deletebutton = new Button("delete");
        deletebutton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        deletebutton.setVisible(false);
        deletebutton.setOnMouseEntered(e -> deletebutton.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));
        deletebutton.setOnMouseExited(e -> deletebutton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));


        deletebutton.setOnAction(event -> {
            String selectedTable = tableDropdown.getValue();
            update_tables update =  new update_tables();
            if (selectedTable.equalsIgnoreCase(AdminService.adminmap.get("city"))){
                Stage popupStage = new Stage();
                popupStage.setTitle("Delete street");

                Label deletestreetlbl =  new Label("Enter street name");
                TextField deletestreetfield = new TextField();
                deletestreetfield.setPromptText("Enter street name");

                Button deletebtn = new Button("delete");
                deletebtn.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
                deletebtn.setOnMouseEntered(e -> deletebtn.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));
                deletebtn.setOnMouseExited(e -> deletebtn.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));

                deletebtn.setOnAction(submitEvent -> {
                    String deletestreetname = deletestreetfield.getText();
                    if (update.deletestreet(deletestreetname)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Street deleted!");
                        alert.showAndWait();
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setContentText("Error deleting table");
                        alert.showAndWait();
                    }
                    popupStage.close();
                });
                VBox popupContent = new VBox(10, deletestreetlbl,deletestreetfield ,deletebtn);
                popupContent.setStyle("-fx-padding: 20; -fx-alignment: center;");
                Scene popupScene = new Scene(popupContent, 300, 200);
                popupStage.setScene(popupScene);
                popupStage.show();
            }
            else{
                Stage popupStage = new Stage();
                popupStage.setTitle("Delete street light");

                Label deletestreetlightlbl =  new Label("Enter street light id");
                TextField deletestreetlightfield = new TextField();
                deletestreetlightfield.setPromptText("Enter street light id");

                Button deletebtn = new Button("Delete");
                deletebtn.setStyle("-fx-background-color: #0078D7; -fx-text-fill: white;");
                deletebtn.setOnMouseEntered(e -> deletebtn.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));
                deletebtn.setOnMouseExited(e -> deletebtn.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));

                deletebtn.setOnAction(submitEvent -> {
                    String id = deletestreetlightfield.getText();
                    if (update.deletestreetlight(selectedTable, id)){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Street light deleted!");
                        alert.showAndWait();
                    }
                    else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setContentText("Error deleting street light");
                        alert.showAndWait();
                    }
                    popupStage.close();
                });
                VBox popupContent = new VBox(10, deletestreetlightlbl,deletestreetlightfield ,deletebtn);
                popupContent.setStyle("-fx-padding: 20; -fx-alignment: center;");
                Scene popupScene = new Scene(popupContent, 300, 200);
                popupStage.setScene(popupScene);
                popupStage.show();
            }
        });


        tableDropdown.setOnAction(event -> {
            String selectedTable = tableDropdown.getValue();
            if (selectedTable != null) {
                selectedTableLabel.setText("Table Details: Showing data for " + selectedTable);
                updateButton.setVisible(true);
                addbutton.setVisible(true);
                deletebutton.setVisible(true);
                if (selectedTable.equalsIgnoreCase(AdminService.adminmap.get("city"))){
                    updateButton.setText("Update mw");
                    addbutton.setText("Add street");
                    deletebutton.setText("Delete street");
                }
                else{
                    updateButton.setText("Send action mail");
                    addbutton.setText("Add new street light");
                    deletebutton.setText("Delete street light");
                }
                ObservableList<HomePage.TableRow> tableData = getTableDataForTable(selectedTable);
                tableView.setItems(tableData);
            } else {
                updateButton.setVisible(false);
                addbutton.setVisible(false);
                deletebutton.setVisible(false);
            }
        });

        ObservableList<String> tableNames = getTableNamesFromDatabase();
        tableDropdown.setItems(tableNames);

        HBox dropdownAndButton = new HBox(10, tableDropdown, refreshButton, updateButton, addbutton, deletebutton);
        dropdownAndButton.setStyle("-fx-alignment: center-left;");

        tableView = new TableView<>();
        tableView.setEditable(true);
        tableView.setPlaceholder(new Label("Select a table to see its data"));

        maintenanceContent.getChildren().addAll(maintenanceLabel, dropdownAndButton, selectedTableLabel, tableView);
        maintenanceTab.setContent(maintenanceContent);
        //--------------------------------------------------------------------------------------------------------------
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
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(300);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
//----------------------------------------------------------------------------------------------------------------------

