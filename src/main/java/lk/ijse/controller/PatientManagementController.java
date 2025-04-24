package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.bo.custom.PatientBO;
import lk.ijse.bo.custom.impl.PatientBOImpl;
import lk.ijse.dto.PatientDTO;
import lk.ijse.view.tdm.PatientTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PatientManagementController implements Initializable {

    @FXML
    private JFXButton btnAddNewPatient;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<PatientTM, Integer> clmContact;

    @FXML
    private TableColumn<PatientTM, String > clmGender;

    @FXML
    private TableColumn<PatientTM, String> clmMedicalHistory;

    @FXML
    private TableColumn<PatientTM, String> clmPatientId;

    @FXML
    private TableColumn<PatientTM, String> clmPatientName;

    @FXML
    private ComboBox<String> cmbGender;

    @FXML
    private TextArea historyArea;

    @FXML
    private ImageView imgHome;

    @FXML
    private ImageView imgPatient;

    @FXML
    private TableView<PatientTM> tblPatient;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtPatientId;

    @FXML
    private TextField txtPatientName;

    @FXML
    private TextField txtSearch;

    private final PatientBO patientBO = new PatientBOImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clmPatientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmPatientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmContact.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        clmGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        clmMedicalHistory.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));

        loadPatients();
        try {
            generateNewPatientId();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNewPatientId() throws SQLException, ClassNotFoundException {
        txtPatientId.setText(patientBO.getNextPatientID());
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void loadPatients() {
        try  {
            ArrayList<PatientDTO> patients = patientBO.loadAllPatient();
            ObservableList<PatientTM> patientTMS = FXCollections.observableArrayList();

            for (PatientDTO patientDTO  : patients) {

                PatientTM patientTM = new PatientTM(
                        patientDTO.getId(),
                        patientDTO.getName(),
                        patientDTO.getContactInfo(),
                        patientDTO.getGender(),
                        patientDTO.getMedicalHistory()
                );

                patientTMS.add(patientTM);
            }
            tblPatient.setItems(patientTMS);
        } catch (Exception e) {
            showAlert("Error", "Failed to load Patient!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void btnAddNew_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        generateNewPatientId();
        clearFields();
    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) throws Exception {
        String customer_id = txtPatientId.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this patient?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = patientBO.deletePatient(customer_id);

            if (!isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Patient deleted...!").show();
                clearFields();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete patient...!").show();
            }
        }
    }

    @FXML
    void btnSave_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtPatientId.getText();
        String name = txtPatientName.getText();
        String contact = txtContact.getText();
        String gender = cmbGender.getValue();
        String history = historyArea.getText();

        // Validate input fields
        if (id.isEmpty() || name.isEmpty() || contact.isEmpty() || history.isEmpty()) {
            showAlert("Warning", "Please fill all fields!", Alert.AlertType.WARNING);
            return;
        }

        PatientDTO patientDTO = new PatientDTO(id, name, contact, gender, history);

        try {
            boolean isSaved = patientBO.savePatient(patientDTO);

            if (isSaved) {
                showAlert("Success", "Patient saved successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to save patient! Possible duplicate ID.", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Log error details
            showAlert("Error", "An unexpected error occurred!", Alert.AlertType.ERROR);
        }

        loadPatients(); // Refresh therapist list
        clearFields(); // Clear input fields
    }

    private void clearFields() throws SQLException, ClassNotFoundException {
        txtPatientId.clear();
        txtPatientName.clear();
        txtContact.clear();
        historyArea.clear();

        generateNewPatientId();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void btnUpdate_OnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtPatientId.getText();
        String name = txtPatientName.getText();
        String contact = txtContact.getText();
        String gender = cmbGender.getValue();
        String history = historyArea.getText();

        // Validate input fields
        if (id.isEmpty() || name.isEmpty() || contact.isEmpty() || history.isEmpty()) {
            showAlert("Warning", "Please fill all fields!", Alert.AlertType.WARNING);
            return;
        }

        PatientDTO patientDTO = new PatientDTO(id, name, contact, gender, history);

        try {
            boolean isSaved = patientBO.updatePatient(patientDTO);

            if (isSaved) {
                showAlert("Success", "Patient saved successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to save patient! Possible duplicate ID.", Alert.AlertType.ERROR);
            }

        } catch (Exception e) {
            e.printStackTrace(); // Log error details
            showAlert("Error", "An unexpected error occurred!", Alert.AlertType.ERROR);
        }

        loadPatients(); // Refresh therapist list
        clearFields(); // Clear input fields
    }

    @FXML
    void navigateToHome(MouseEvent event) {
        loadUI("/view/Home.fxml");
    }

    private void loadUI(String resource) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
            fxmlLoader.load();
        } catch (IOException e) {
            showAlert("Error", "Failed to load dashboard!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void searchPatient(KeyEvent event) {

    }

    @FXML
    void tblPatientOnClick(MouseEvent event) {
        PatientTM selectedItem = tblPatient.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtPatientId.setText(selectedItem.getId());
            txtPatientName.setText(selectedItem.getName());
            txtContact.setText(selectedItem.getContactInfo());
            cmbGender.setValue(selectedItem.getGender());
            historyArea.setText(selectedItem.getMedicalHistory());

            btnSave.setDisable(true);
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

}