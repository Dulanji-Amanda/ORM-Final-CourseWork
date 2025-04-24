package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReceptionistPageController implements Initializable {
    @FXML
    private Button btnAppointment;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnPatient;

    @FXML
    private Button btnPayment;

    @FXML
    private ImageView imgLogout;

    @FXML
    private ImageView imgSettings;

    @FXML
    private Label lblAdd;

    @FXML
    private Label lblWelcome;

    @FXML
    private AnchorPane mainContent;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button btnFinancereport;

    @FXML
    void btnFinancereportOnAction(ActionEvent event) {
        loadUI("/view/FinancialReport.fxml");
    }

    @FXML
    void btnAppointmentOnAction(ActionEvent event) {
        loadUI("/view/TherapySessionManagementForm.fxml");
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) {
        loadUI("/view/Home.fxml");
    }

    @FXML
    void btnPatientOnAction(ActionEvent event) {
        loadUI("/view/PatientManagementForm.fxml");
    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {
        loadUI("/view/PaymentManagementForm.fxml");
    }

    @FXML
    void onLogoutClick(MouseEvent event) {
        rootPane.getChildren().clear();
        try {
            rootPane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginPage.fxml"))));
        } catch (IOException e) {
            showAlert("Error", "Failed to load dashboard!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void onSettingsClick(MouseEvent event) {
        navigateTo("/view/SettingForm.fxml");
    }

    private void loadUI(String resource) {
        mainContent.getChildren().clear();
        try {
            mainContent.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            showAlert("Error", "Failed to load dashboard!", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void navigateTo(String fxmlPath) {
        try {
            mainContent.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(mainContent.widthProperty());
            load.prefHeightProperty().bind(mainContent.heightProperty());
            mainContent.getChildren().add(load);

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigateTo("/view/Home.fxml");
    }

}
