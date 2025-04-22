package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.impl.UserBoImpl;
import lk.ijse.bo.custom.UserBo;
import lk.ijse.util.Role;

import java.io.IOException;

public class SignupPageController {

    @FXML
    private Button btnSignUp;

    @FXML
    private ComboBox<?> cmbRole;

    @FXML
    private AnchorPane signupPage;

    @FXML
    private Label lblLogin;

    @FXML
    private AnchorPane whiteap;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtemail;

    private final UserBo userBo = new UserBoImpl();

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@gmail\\.com$";

    @FXML
    void btnSignUpOnAction(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String email = txtemail.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();
        String selectedRole = (String) cmbRole.getValue();

        if (username.isEmpty() ||email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || selectedRole == null) {
            showAlert("Error", "All fields are required!", Alert.AlertType.ERROR);
            return;
        }

        if (!isValidEmail(txtemail.getText())) {
            showAlert("Error", "Please enter a valid email address!", Alert.AlertType.ERROR);
            return;
        }
        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match!", Alert.AlertType.ERROR);
            return;
        }

        // Convert string to Role Enum
        Role role = Role.valueOf(selectedRole);

        // Save user to database
        boolean success = userBo.registerUser(username,email, password, role);

        if (success) {
            showAlert("Success", "Account created successfully!", Alert.AlertType.INFORMATION);
            goToLogin(null);
        } else {
            showAlert("Error", "Username already exists!", Alert.AlertType.ERROR);
        }
    }

    private boolean isValidEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    @FXML
    void goToLogin(MouseEvent event) {
        loadUI("/view/LoginPage.fxml");
    }

    private void loadUI(String resource) {
        signupPage.getChildren().clear();
        try {
            signupPage.getChildren().add(FXMLLoader.load(getClass().getResource(resource)));
        } catch (IOException e) {
            showAlert("Error", "Failed to load page!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
