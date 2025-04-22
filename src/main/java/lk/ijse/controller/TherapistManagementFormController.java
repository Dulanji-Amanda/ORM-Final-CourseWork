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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.bo.custom.TherapistBO;
import lk.ijse.bo.custom.impl.TherapistBOImpl;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dto.TherapistDTO;
import lk.ijse.view.tdm.TherapistTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapistManagementFormController implements Initializable {

    @FXML
    private JFXButton btnAddNewTherapist;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<TherapistTM, String> clmTherapisstAvailability;

    @FXML
    private TableColumn<TherapistTM, String> clmTherapistId;

    @FXML
    private TableColumn<TherapistTM, String> clmTherapistName;

    @FXML
    private TableColumn<TherapistTM, String> clmTherapistSpeciality;

    @FXML
    private ImageView imgHome;

    @FXML
    private TableView<TherapistTM> tblTherapists;

    @FXML
    private TextField txtTherapistAvailability;

    @FXML
    private TextField txtTherapistId;

    @FXML
    private TextField txtTherapistName;

    @FXML
    private TextField txtTherapistSpecialty;

    @FXML
    private AnchorPane viewTherapyProgramePane;

    private final FactoryConfiguration factoryConfiguration = new FactoryConfiguration();
    private final TherapistBO therapistBO = new TherapistBOImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clmTherapistId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmTherapistName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmTherapistSpeciality.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        clmTherapisstAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));

        // Load data into table
        loadTherapists();
    }

    private void loadTherapists() {
//        therapistList.clear();
        try  {
            ArrayList<TherapistDTO> therapists = therapistBO.loadAllTherapists();
            ObservableList<TherapistTM> therapistTMList = FXCollections.observableArrayList();

            for (TherapistDTO therapistDTO : therapists) {

                TherapistTM therapistTM = new TherapistTM(
                        therapistDTO.getTherapistID(),
                        therapistDTO.getTherapistName(),
                        therapistDTO.getSpecialization(),
                        therapistDTO.getAvailability()
                );

                therapistTMList.add(therapistTM);
            }
            tblTherapists.setItems(therapistTMList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load therapists!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }

        //table ek reload wenn hdnn
    }


    @FXML
    void btnAddNew_OnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) {
        TherapistTM selectedTherapist = tblTherapists.getSelectionModel().getSelectedItem();
        if (selectedTherapist == null) {
            showAlert("Warning", "Please select a therapist to delete!", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this therapist?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDelete = therapistBO.deleteTherapist(selectedTherapist.getId());

                if (isDelete) {
                    showAlert("Success", "Therapist deleted successfully!", Alert.AlertType.INFORMATION);

                } else {
                    showAlert("Error", "Failed to delete therapist!", Alert.AlertType.ERROR);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            loadTherapists();
            clearFields();
        }
    }

    @FXML
    void btnSave_OnAction(ActionEvent event) {
        String id = txtTherapistId.getText();
        String name = txtTherapistName.getText();
        String specialty = txtTherapistSpecialty.getText();
        String availability = txtTherapistAvailability.getText();

        if (id.isEmpty() || name.isEmpty() || specialty.isEmpty() || availability.isEmpty()) {
            showAlert("Warning", "Please fill all fields!", Alert.AlertType.WARNING);
            return;
        }

        TherapistDTO therapistDTO = new TherapistDTO(id, name, specialty, availability);
        try{
            boolean isSaved = therapistBO.saveTherapist(therapistDTO);

            if (isSaved) {
                showAlert("Success", "Therapist save successfully!", Alert.AlertType.INFORMATION);

            } else {
                showAlert("Error", "Failed to save therapist!", Alert.AlertType.ERROR);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        loadTherapists();
        clearFields();
    }

    @FXML
    void btnUpdate_OnAction(ActionEvent event) {
        String id = txtTherapistId.getText();
        String name = txtTherapistName.getText();
        String specialty = txtTherapistSpecialty.getText();
        String availability = txtTherapistAvailability.getText();

        if (id.isEmpty() || name.isEmpty() || specialty.isEmpty() || availability.isEmpty()) {
            showAlert("Warning", "Please fill all fields!", Alert.AlertType.WARNING);
            return;
        }

        try {
            TherapistDTO therapistDTO = new TherapistDTO(id, name, specialty, availability);

            boolean isUpdate = therapistBO.updateTherapist(therapistDTO);
            if (isUpdate) {
                showAlert("Success", "Therapist update successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to update therapist!", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadTherapists();
        clearFields();
    }

    @FXML
    void navigateToHome(MouseEvent event) {
        loadUI("/view/Home.fxml");
    }

    @FXML
    void tblTherapistsOnClick(MouseEvent event) {
        TherapistTM selectedItem = tblTherapists.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Warning", "Please select a therapist", Alert.AlertType.WARNING);
        }else {
            txtTherapistId.setText(selectedItem.getId());
            txtTherapistName.setText(selectedItem.getName());
            txtTherapistSpecialty.setText(selectedItem.getSpecialization());
            txtTherapistAvailability.setText(selectedItem.getAvailability());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void loadUI(String resource) {
        viewTherapyProgramePane.getChildren().clear();
        try {
            viewTherapyProgramePane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            showAlert("Error", "Failed to load the requested view!", Alert.AlertType.ERROR);
        }
    }

    private void clearFields() {
        txtTherapistId.clear();
        txtTherapistName.clear();
        txtTherapistSpecialty.clear();
        txtTherapistAvailability.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}