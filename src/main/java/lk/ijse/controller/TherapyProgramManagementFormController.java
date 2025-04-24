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
import lk.ijse.bo.custom.TherapyProgramsBO;
import lk.ijse.bo.custom.impl.TherapyProgramsBOImpl;
import lk.ijse.dto.TherapyProgramDTO;
import lk.ijse.view.tdm.TherapyProgramTM;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class TherapyProgramManagementFormController implements Initializable {

    @FXML
    private AnchorPane addTherapistsPane;

    @FXML
    private JFXButton btnAddNewTherapist;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<TherapyProgramTM, Integer> clmDuration;

    @FXML
    private TableColumn<TherapyProgramTM, Double> clmFee;

    @FXML
    private TableColumn<TherapyProgramTM, String> clmProgramId;

    @FXML
    private TableColumn<TherapyProgramTM, String> clmProgramName;

    @FXML
    private ImageView imgHome;

    @FXML
    private TableView<TherapyProgramTM> tblTherapyPrograms;

    @FXML
    private TextField txtFee;

    @FXML
    private TextField txtProgramDuration;

    @FXML
    private TextField txtProgramId;

    @FXML
    private TextField txtProgramName;

    private final TherapyProgramsBO therapyProgramsBO = new TherapyProgramsBOImpl();

    private static TherapyProgramManagementFormController instance;

    // set therapist id to text field
    public TherapyProgramManagementFormController(){
        instance = this;
    }

    public static TherapyProgramManagementFormController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clmProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        clmProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        clmDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        clmFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        loadTherapyPrograms();
        try {
            generateNewID();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void generateNewID() {
        txtProgramId.setText(therapyProgramsBO.getNextTherapyProgramId());
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void loadTherapyPrograms() {
        try  {
            ArrayList<TherapyProgramDTO> therapyProgramDTOS = therapyProgramsBO.loadAllTherapyPrograms();
            ObservableList<TherapyProgramTM> therapyProgramTMSList = FXCollections.observableArrayList();

            for (TherapyProgramDTO therapyProgramDTO : therapyProgramDTOS) {

                TherapyProgramTM therapyProgramTM = new TherapyProgramTM(
                        therapyProgramDTO.getProgramId(),
                        therapyProgramDTO.getProgramName(),
                        therapyProgramDTO.getDuration(),
                        therapyProgramDTO.getFee()
                );

                therapyProgramTMSList.add(therapyProgramTM);
            }
            tblTherapyPrograms.setItems(therapyProgramTMSList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load therapists!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void btnAddNew_OnAction(ActionEvent event) {
        clearFields();
        generateNewID();
    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) {
        TherapyProgramTM selectedTherapyProgram = tblTherapyPrograms.getSelectionModel().getSelectedItem();
        if (selectedTherapyProgram == null) {
            showAlert("Warning", "Please select a therapy program to delete!", Alert.AlertType.WARNING);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this therapy program?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> result = confirm.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            try {
                boolean isDelete = therapyProgramsBO.deleteTherapyPrograms(selectedTherapyProgram.getProgramId());

                if (isDelete) {
                    showAlert("Success", "Therapy program deleted successfully!", Alert.AlertType.INFORMATION);

                } else {
                    showAlert("Error", "Failed to delete therapy program!", Alert.AlertType.ERROR);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            loadTherapyPrograms();
            clearFields();
        }
    }

    @FXML
    void btnSave_OnAction(ActionEvent event) {
        String id = txtProgramId.getText();
        String name = txtProgramName.getText();
        int duration = Integer.parseInt(txtProgramDuration.getText());
        double fee = Double.parseDouble(txtFee.getText());

        if(id.isEmpty() || name.isEmpty() || duration < 0 || fee < 0)  {
            showAlert("Warning", "Please fill all fields!", Alert.AlertType.WARNING);
            return;
        }

        TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO(id, name, duration, fee);
        try{
            boolean isSaved = therapyProgramsBO.saveTherapyPrograms(therapyProgramDTO);

            if (isSaved) {
                clearFields();
                loadTherapyPrograms();
                generateNewID();
                showAlert("Success", "Therapy Program save successfully!", Alert.AlertType.INFORMATION);

            } else {
                showAlert("Error", "Failed to save Therapy Program!", Alert.AlertType.ERROR);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void btnUpdate_OnAction(ActionEvent event) {
        String id = txtProgramId.getText();
        String name = txtProgramName.getText();
        int duration = Integer.parseInt(txtProgramDuration.getText());
        double fee = Double.parseDouble(txtFee.getText());

        if(id.isEmpty() || name.isEmpty() || duration < 0 || fee < 0)  {
            showAlert("Warning", "Please fill all fields!", Alert.AlertType.WARNING);
            return;
        }

        TherapyProgramDTO therapyProgramDTO = new TherapyProgramDTO(id, name, duration, fee );
        try{
            boolean isUpdate = therapyProgramsBO.updateTherapyPrograms(therapyProgramDTO);

            if (isUpdate) {
                showAlert("Success", "Therapy Program update successfully!", Alert.AlertType.INFORMATION);

            } else {
                showAlert("Error", "Failed to update Therapy Program!", Alert.AlertType.ERROR);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void clearFields() {
        txtProgramId.clear();
        txtProgramName.clear();
        txtProgramDuration.clear();
        txtFee.clear();
        loadTherapyPrograms();
        generateNewID();
    }

    @FXML
    void navigateToHome(MouseEvent event) {
        loadUI("/view/Home.fxml");
    }

    private void loadUI(String resource) {
        addTherapistsPane.getChildren().clear();
        try {
            addTherapistsPane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
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

    @FXML
    void tblTherapyProgramsOnClick(MouseEvent event) {
        TherapyProgramTM selectedItem = tblTherapyPrograms.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Warning", "Please select a therapy program", Alert.AlertType.WARNING);
        }else {
            txtProgramId.setText(selectedItem.getProgramId());
            txtProgramName.setText(selectedItem.getProgramName());
            txtProgramDuration.setText(String.valueOf(selectedItem.getDuration()));
            txtFee.setText(String.valueOf(selectedItem.getFee()));
//            txtTherapistId.setText(selectedItem.getTherapistId());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

}