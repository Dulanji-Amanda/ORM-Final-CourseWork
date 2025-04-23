package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.TherapySessionBO;
import lk.ijse.bo.custom.impl.TherapySessionBOImpl;
import lk.ijse.dto.TherapySessionDTO;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapySessionManagementFormController implements Initializable {

    @FXML
    private JFXButton btnAddPatient;

    @FXML
    private JFXButton btnAddTherapist;

    @FXML
    private JFXButton btnAddTherapyProgram;

    @FXML
    private JFXButton btnCompleteSetup;

    @FXML
    private JFXButton btnSeeAll;

    @FXML
    private DatePicker dpSessionDate;

    @FXML
    private ImageView imgHome;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane sessionPane;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtSessionId;

    @FXML
    private TextField txtSessionTime;

    @FXML
    private TextField txtTherapistId;

    @FXML
    private TextField txtprogramId;

    private static TherapySessionManagementFormController instance;

    // set therapist id to text field
    public TherapySessionManagementFormController(){
        instance = this;
    }

    public static TherapySessionManagementFormController getInstance() {
        return instance;
    }

    public void setProgramId(String id) {txtprogramId.setText(id);}

    public void setPatientId(String id) {txtPatientId.setText(id);}

    public void setTherapistId(String id) {txtTherapistId.setText(id);}

    private final  TherapySessionBO therapySessionBO = new TherapySessionBOImpl();

    PaymentManagementController paymentFormController = new PaymentManagementController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUI("/view/Patient-Table-View.fxml");
    }

    @FXML
    void btnAddPatient_OnAction(ActionEvent event) {
        loadUI("/view/Patient-Table-View.fxml");
    }

    @FXML
    void btnAddTherapist_OnAction(ActionEvent event) {
        loadUI("/view/Therapist-Table-View.fxml");
    }

    @FXML
    void btnAddTherapyProgram_OnAction(ActionEvent event) {
        loadUI("/view/TherapyProgram-Table-View.fxml");
    }

    @FXML
    void btnCompleteSetupOnAction(ActionEvent event) {
        try {
            // Step 1: Validate input fields
            if (!validateInputs()) {
                return;
            }

            // Step 2: Collect session data
            TherapySessionDTO sessionDTO = collectSessionData();
            handlePaymentComplete(sessionDTO);

            // Step 3: Ask for confirmation
            if (confirmSessionBooking()) {
                // Step 4: Navigate to payment form
                navigateToPaymentForm(sessionDTO);

            }
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private boolean validateInputs() {
        // Validate session ID
        if (txtSessionId.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Session ID cannot be empty", Alert.AlertType.ERROR);
            txtSessionId.requestFocus();
            return false;
        }

        // Validate patient ID
        if (txtPatientId.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Patient ID cannot be empty", Alert.AlertType.ERROR);
            txtPatientId.requestFocus();
            return false;
        }

        // Validate therapist ID
        if (txtTherapistId.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Therapist ID cannot be empty", Alert.AlertType.ERROR);
            txtTherapistId.requestFocus();
            return false;
        }

        // Validate program ID
        if (txtprogramId.getText().trim().isEmpty()) {
            showAlert("Validation Error", "Program ID cannot be empty", Alert.AlertType.ERROR);
            txtprogramId.requestFocus();
            return false;
        }

        // Validate date
        if (dpSessionDate.getValue() == null) {
            showAlert("Validation Error", "Session date must be selected", Alert.AlertType.ERROR);
            dpSessionDate.requestFocus();
            return false;
        }

        // Validate time
        try {
            LocalTime.parse(txtSessionTime.getText());
        } catch (Exception e) {
            showAlert("Validation Error", "Session time must be in a valid format (HH:MM)", Alert.AlertType.ERROR);
            txtSessionTime.requestFocus();
            return false;
        }

        return true;
    }

    private TherapySessionDTO collectSessionData() {
        TherapySessionDTO sessionDTO = new TherapySessionDTO();
        sessionDTO.setSessionId(txtSessionId.getText());
        sessionDTO.setPatientId(txtPatientId.getText());
        sessionDTO.setTherapistId(txtTherapistId.getText());
        sessionDTO.setProgramId(txtprogramId.getText());
        sessionDTO.setSessionDate(dpSessionDate.getValue());
        sessionDTO.setSessionTime(LocalTime.parse(txtSessionTime.getText()));
        return sessionDTO;
    }

    private boolean confirmSessionBooking() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Pay payment to confirm this session",
                ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();
        return result.isPresent() && result.get() == ButtonType.YES;
    }

    private void navigateToPaymentForm(TherapySessionDTO sessionDTO) throws IOException {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Manage-Payment-Form.fxml"));
            AnchorPane pane = loader.load();

            // Get the actual controller instance associated with the FXML
            PaymentManagementController paymentController = loader.getController();

            // Set session details
            paymentController.setSessionId(sessionDTO.getSessionId());
            paymentController.setParentController(this);

            // Now display the pane
            sessionPane.getChildren().setAll(pane);

            // Simulate the user completing the form and saving payment
            paymentController.savePaymentWithSession();

//            if (dto != null) {
//                handlePaymentComplete(sessionDTO);
//            }

        } catch (IOException e) {
            showAlert("Error", "Failed to load payment form!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void handlePaymentComplete(TherapySessionDTO sessionDTO) {
        try {
            boolean isBooked = therapySessionBO.bookSession(
                    sessionDTO.getSessionId(),
                    sessionDTO.getPatientId(),
                    sessionDTO.getTherapistId(),
                    sessionDTO.getProgramId(),
                    sessionDTO.getSessionDate(),
                    sessionDTO.getSessionTime()
            );

            if (isBooked) {
                showAlert("Success", "Therapy session booked successfully", Alert.AlertType.INFORMATION);
                clearFields();


            } else {
                showAlert("Error", "Failed to book session.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "Error saving session: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    public void onPaymentCompleted() {
        try {
            navigateToSessionList();
        } catch (IOException e) {
            showAlert("Error", "Could not navigate to session list after payment", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void navigateToSessionList() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TherapySession-Table-View.fxml"));
        AnchorPane pane = loader.load();
        sessionPane.getChildren().setAll(pane);
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnRescheduleOnAction(ActionEvent event) {

        // For now, reschedule to a new hardcoded time (e.g., next day, same time)
        LocalDate newDate = dpSessionDate.getValue(); // you can show date/time pickers
        LocalTime newTime = LocalTime.parse(txtSessionTime.getText()); // or change time here

        boolean isUpdated = therapySessionBO.rescheduleSession(
                txtSessionId.getText(),
                newDate,
                newTime
        );

        if (isUpdated) {
            showAlert("Success", "Session rescheduled successfully!", Alert.AlertType.INFORMATION);
            try {
                AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/TherapySession-Table-View.fxml")));
                sessionPane.getChildren().setAll(pane);
            } catch (IOException e) {
                showAlert("Error", "Failed to load session list!", Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Failed to reschedule the session.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void searchPatient(KeyEvent event) {
        String name = ((TextField) event.getSource()).getText();

        try {
            TherapySessionDTO sessionDTO = therapySessionBO.getSessionByPatientName(name);

            if (sessionDTO != null) {
                txtSessionId.setText(sessionDTO.getSessionId());
                txtPatientId.setText(sessionDTO.getPatientId());
                txtTherapistId.setText(sessionDTO.getTherapistId());
                txtprogramId.setText(sessionDTO.getProgramId());
                dpSessionDate.setValue(sessionDTO.getSessionDate());
                txtSessionTime.setText(sessionDTO.getSessionTime().toString());
            } else {
                clearFields();
            }

        } catch (Exception e) {
            showAlert("Error", "Failed to search therapy session", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void btnSeeAllOnAction(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/TherapySession-Table-View.fxml")));
            sessionPane.getChildren().setAll(pane);
        } catch (IOException e) {
            showAlert("Error", "Failed to load session list!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void navigateToHome(MouseEvent event) {

    }

    private void loadUI(String resource) {
        mainPane.getChildren().clear();
        try {
            mainPane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
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

    private void clearFields() {
        txtSessionId.clear();
        txtPatientId.clear();
        txtTherapistId.clear();
        txtprogramId.clear();
        txtSessionTime.clear();
        dpSessionDate.setValue(null);
    }

}