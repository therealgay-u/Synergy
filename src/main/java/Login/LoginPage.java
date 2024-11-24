package Login;

import jdbc.AdminService;
import HomePage.HomePage;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class LoginPage extends Application {
    AdminService admin =  new AdminService();

    @Override
    public void start(Stage primaryStage) {

        Text title = new Text("Login");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.DARKBLUE);
        title.setEffect(new DropShadow(2, Color.GRAY));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5;");
        usernameField.setMaxWidth(200);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5;");
        passwordField.setMaxWidth(200);

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;");

        submitButton.setOnMouseEntered(e -> submitButton.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));
        submitButton.setOnMouseExited(e -> submitButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));

        submitButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (admin.login(username, password)) {
                Alert success = new Alert(AlertType.INFORMATION);
                success.setContentText("Login Successful!");
                success.showAndWait();
                try {
                    HomePage homePage = new HomePage();
                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    homePage.start(stage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showError("Failed to load Home Page.");
                }
            } else {
                showError("Invalid Credentials!");
            }
        });

        Button signUpButton = new Button("Sign Up");
        signUpButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;");
        signUpButton.setOnMouseEntered(e -> signUpButton.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));
        signUpButton.setOnMouseExited(e -> signUpButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));

        HBox buttonBox = new HBox(10, submitButton, signUpButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(15, title, usernameField, passwordField, buttonBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle(
                "-fx-background-color: #ffffff; " +
                        "-fx-border-color: #000000; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 15; " +
                        "-fx-background-radius: 15;"
        );
        vbox.setMaxSize(300, 250);

        signUpButton.setOnAction(e -> {
            vbox.getChildren().clear();

            Text signUpTitle = new Text("Sign Up");
            signUpTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            signUpTitle.setFill(Color.DARKBLUE);
            signUpTitle.setEffect(new DropShadow(2, Color.GRAY));

            TextField specialKeyField = new TextField();
            specialKeyField.setPromptText("Special Key");
            specialKeyField.setStyle("-fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5;");
            specialKeyField.setMaxWidth(200);

            TextField signup_nameField = new TextField();
            signup_nameField.setPromptText("Name");
            signup_nameField.setStyle("-fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5;");
            signup_nameField.setMaxWidth(200);

            TextField signup_usernameField = new TextField();
            signup_usernameField.setPromptText("Username");
            signup_usernameField.setStyle("-fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5;");
            signup_usernameField.setMaxWidth(200);

            PasswordField signup_passwordField = new PasswordField();
            signup_passwordField.setPromptText("Password");
            signup_passwordField.setStyle("-fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5;");
            signup_passwordField.setMaxWidth(200);

            TextField signup_emailField = new TextField();
            signup_emailField.setPromptText("Email");
            signup_emailField.setStyle("-fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5;");
            signup_emailField.setMaxWidth(200);

            Button signup_SubmitButton = new Button("Submit");
            signup_SubmitButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;");
            signup_SubmitButton.setOnMouseEntered(evt -> signup_SubmitButton.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));
            signup_SubmitButton.setOnMouseExited(evt -> signup_SubmitButton.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;"));

            signup_SubmitButton.setOnAction(evt -> {

                String specialKey = specialKeyField.getText();
                String name = signup_nameField.getText();
                String username = signup_usernameField.getText();
                String password = signup_passwordField.getText();
                String email = signup_emailField.getText();

                if (specialKey.isEmpty() || name.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    showError("Please fill in all fields.");
                } else {
                    if (admin.signup(username, password, name, email, specialKey)) {
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Sign Up Successful");
                        success.setHeaderText(null);
                        success.setContentText("You have successfully signed up!");
                        success.showAndWait();

                        start(primaryStage);
                        if (admin.newsignin(specialKey)){
                            Alert table_success = new Alert(Alert.AlertType.INFORMATION);
                            table_success.setTitle("Sign Up Successful");
                            table_success.setHeaderText(null);
                            table_success.setContentText("New database has been set up!");
                            table_success.showAndWait();
                        }
                    } else {
                        showError("Sign Up failed. Please check your inputs.");
                    }
                }
            });
            vbox.getChildren().addAll(signUpTitle, specialKeyField, signup_nameField, signup_usernameField, signup_passwordField, signup_emailField, signup_SubmitButton);
        });

        StackPane stackPane = new StackPane(vbox);
        stackPane.setStyle("-fx-background-color: #f0f4f8;");
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setPrefSize(400, 300);

        Scene scene = new Scene(stackPane, 400, 300);
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(300);
        primaryStage.show();

    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
