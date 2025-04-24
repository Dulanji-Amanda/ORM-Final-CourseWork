package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.bo.custom.PatientBO;
import lk.ijse.bo.custom.impl.PatientBOImpl;
import lk.ijse.dto.PatientDTO;
import lk.ijse.view.tdm.PatientTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PatientTableFormController implements Initializable {

    @FXML
    private TableColumn<PatientTM, Integer> clmContact;

    @FXML
    private TableColumn<PatientTM, String> clmGender;

    @FXML
    private TableColumn<PatientTM, String> clmHistory;

    @FXML
    private TableColumn<PatientTM, String> clmPatientId;

    @FXML
    private TableColumn<PatientTM, String> clmPatientName;

    @FXML
    private TableView<PatientTM> tblPatient;

    private final PatientBO patientBO = new PatientBOImpl();

    @FXML
    void tblPatientOnClick(MouseEvent event) {
        PatientTM selectedItem = tblPatient.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Warning", "Please select a program", Alert.AlertType.WARNING);
        }else {
            TherapySessionManagementFormController.getInstance().setPatientId(selectedItem.getId());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clmPatientId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmPatientName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmContact.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));
        clmGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        clmHistory.setCellValueFactory(new PropertyValueFactory<>("medicalHistory"));

        loadPatients();
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

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}