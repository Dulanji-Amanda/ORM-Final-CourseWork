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
import lk.ijse.bo.custom.TherapyProgramsBO;
import lk.ijse.bo.custom.impl.TherapyProgramsBOImpl;
import lk.ijse.dto.TherapyProgramDTO;
import lk.ijse.view.tdm.TherapyProgramTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TherapyProgramTableFormController implements Initializable {

    @FXML
    private TableColumn<TherapyProgramTM, Integer> clmDuration;

    @FXML
    private TableColumn<TherapyProgramTM, Double> clmFee;

    @FXML
    private TableColumn<TherapyProgramTM, String> clmProgramId;

    @FXML
    private TableColumn<TherapyProgramTM, String> clmProgramName;

    @FXML
    private TableView<TherapyProgramTM> tblProgram;

    private final TherapyProgramsBO therapyProgramsBO = new TherapyProgramsBOImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clmProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        clmProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        clmDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        clmFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

        loadTherapyPrograms();

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
            tblProgram.setItems(therapyProgramTMSList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load therapists!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void tblProgramOnClick(MouseEvent event) {
        TherapyProgramTM selectedItem = tblProgram.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Warning", "Please select a program", Alert.AlertType.WARNING);
        }else {
            TherapySessionManagementFormController.getInstance().setProgramId(selectedItem.getProgramId());

        }
    }

}