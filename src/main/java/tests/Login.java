package tests;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create the GridPane layout
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setStyle("-fx-background-color: #f0f4f8;");

        // Add a title
        Text sceneTitle = new Text("Welcome");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
        sceneTitle.setFill(Color.DARKSLATEBLUE);
        sceneTitle.setEffect(new DropShadow(3, Color.GRAY));
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        // Username Label and TextField
        Label usernameLabel = new Label("Username:");
        usernameLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        gridPane.add(usernameLabel, 0, 1);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");
        gridPane.add(usernameField, 1, 1);

        // Password Label and PasswordField
        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        gridPane.add(passwordLabel, 0, 2);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");
        gridPane.add(passwordField, 1, 2);

        // Submit Button
        Button submitButton = new Button("Login");
        submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        submitButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5; -fx-border-radius: 5;");
        submitButton.setOnMouseEntered(e -> submitButton.setStyle(
                "-fx-background-color: #45a049; -fx-text-fill: white; -fx-background-radius: 5; -fx-border-radius: 5;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle(
                "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 5; -fx-border-radius: 5;"));

        // Add action to the button
        submitButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            System.out.println("Username: " + username + ", Password: " + password);
        });

        // Add button in a HBox to center it
        HBox buttonBox = new HBox(10, submitButton);
        buttonBox.setAlignment(Pos.CENTER);
        gridPane.add(buttonBox, 0, 3, 2, 1);

        // Create a Scene and add it to the Stage
        Scene scene = new Scene(gridPane, 400, 300);
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
