package tests;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login_1 extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Title text
        Text title = new Text("Login");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.DARKBLUE);
        title.setEffect(new DropShadow(2, Color.GRAY));

        // Username field
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Password field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Submit button
        Button submitButton = new Button("Submit");
        submitButton.setStyle(
                "-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5;");
        submitButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            System.out.println("Username: " + username + ", Password: " + password);
        });

        // VBox layout for form elements
        VBox vbox = new VBox(15, title, usernameField, passwordField, submitButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle(
                "-fx-background-color: #ffffff; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: #000000;");
        vbox.setPrefWidth(250);

        // Center the VBox in a StackPane
        StackPane stackPane = new StackPane(vbox);
        stackPane.setStyle("-fx-background-color: #f0f4f8;");

        // Create the scene
        Scene scene = new Scene(stackPane, 400, 300);
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}