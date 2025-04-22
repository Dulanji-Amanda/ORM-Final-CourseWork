package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.bo.custom.PatientBO;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.bo.custom.TherapyProgramsBO;
import lk.ijse.bo.custom.impl.PatientBOImpl;
import lk.ijse.bo.custom.impl.PaymentBOImpl;
import lk.ijse.bo.custom.impl.TherapyProgramsBOImpl;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.entity.Patient;
import lk.ijse.view.tdm.PaymentTM;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PaymentManagementController implements Initializable {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnGetInvoice;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> cmbPatient;

    @FXML
    private TableColumn<PaymentTM, Double> colAmount;

    @FXML
    private TableColumn<PaymentTM, LocalDate> colDate;

    @FXML
    private TableColumn<PaymentTM, String> colPatient;

    @FXML
    private TableColumn<PaymentTM, String> colPaymentId;

    @FXML
    private TableColumn<PaymentTM, String> colSession;

    @FXML
    private TableColumn<PaymentTM, String> colStatus;

    @FXML
    private DatePicker dpPaymentDate;

    @FXML
    private ImageView imgHome;

    @FXML
    private TableView<PaymentTM> tblPayment;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtPaymentId;

    @FXML
    private TextField txtSessionId;

    @FXML
    private ComboBox<String> cmbFilterStatus;

    private Map<String, String> patientNameIdMap = new HashMap<>();

    private static PaymentManagementController instance;

    public PaymentManagementController() {
        this.instance = this;
    }

    public static PaymentManagementController getInstance() {
        return instance;
    }

    public void setSessionId(String sessionId) {
        this.txtSessionId.setText(sessionId);
    }

    private TherapySessionManagementFormController parentController;

    public void setParentController(TherapySessionManagementFormController controller) {
        this.parentController = controller;
    }

    private final TherapyProgramsBO programsBO = new TherapyProgramsBOImpl();
    private final PaymentBO paymentBO = new PaymentBOImpl();
    private final PatientBO patientBO = new PatientBOImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPatient.setCellValueFactory(new PropertyValueFactory<>("patientId"));
        colSession.setCellValueFactory(new PropertyValueFactory<>("sessionId"));

        generateNewPaymentID();
        loadPatientCombo();
        loadPayments();
        cmbFilterStatus.setItems(FXCollections.observableArrayList("ALL", "PENDING", "COMPLETED"));

    }

    private void generateNewPaymentID() {
        txtPaymentId.setText(paymentBO.getNextPaymentID());
    }

    private void loadPayments() {
        try  {
            ArrayList<PaymentDTO> payments = paymentBO.loadAllPayments();
            ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

            for (PaymentDTO paymentDTO  : payments) {

                PaymentTM paymentTM = new PaymentTM(
                        paymentDTO.getPaymentId(),
                        paymentDTO.getAmount(),
                        paymentDTO.getPaymentDate(),
                        paymentDTO.getStatus(),
                        paymentDTO.getPatientId(),
                        paymentDTO.getSessionId()
                );

                paymentTMS.add(paymentTM);
            }
            tblPayment.setItems(paymentTMS);
        } catch (Exception e) {
            showAlert("Error", "Failed to load Payments!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void loadPatientCombo() {
        try {
            ArrayList<Patient> allPatients = patientBO.loadAllpatientsInCombo();

            patientNameIdMap.clear(); // Clear old data

            for (Patient patient : allPatients) {
                String fullName = patient.getName(); // If needed, format first + last name here
                patientNameIdMap.put(fullName, patient.getId());
            }

            cmbPatient.getItems().setAll(patientNameIdMap.keySet());

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load patients!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void cmbPatientOnAction(ActionEvent event) {

    }

    @FXML
    void btnClear_OnAction(ActionEvent event) {

    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) {

    }

    @FXML
    void btnGetInvoice_OnAction(ActionEvent event) {

    }

    @FXML
    void btnSave_OnAction(ActionEvent event) {
        boolean isSave =  savePaymentWithSession();
        if (isSave) {
            showAlert("Success", "Payment Saved!", Alert.AlertType.INFORMATION);
        }else{
            showAlert("Error", "Failed to save payment!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void btnUpdate_OnAction(ActionEvent event) {

    }

    @FXML
    void navigateToHome(MouseEvent event) {

    }

    public boolean savePaymentWithSession() {
        String paymentId = txtPaymentId.getText();
        String amountText = txtAmount.getText();
        LocalDate date = dpPaymentDate.getValue();
        String status = cmbFilterStatus.getValue();
        String selectedName = cmbPatient.getValue();
        String sessionId = txtSessionId.getText();

        if (paymentId.isEmpty() || amountText.isEmpty() || date == null ||
                status.isEmpty() || selectedName == null || sessionId.isEmpty()) {
            showAlert("Warning", "Please fill all fields!", Alert.AlertType.WARNING);
            return false;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid amount entered!", Alert.AlertType.ERROR);
            return false;
        }

        // Get the patient ID using the selected name from the map
        String patientId = patientNameIdMap.get(selectedName);

        PaymentDTO paymentDTO = new PaymentDTO(paymentId, amount, date, status, patientId, sessionId);
        boolean isPaymentSaved = paymentBO.savePayment(paymentDTO);

        if (isPaymentSaved) {
            showAlert("Success", "Payment saved successfully!", Alert.AlertType.INFORMATION);
            if (parentController != null) {
                parentController.onPaymentCompleted();
            }
            return true;
        } else {
            showAlert("Error", "Failed to save payment!", Alert.AlertType.ERROR);
            return false;
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}