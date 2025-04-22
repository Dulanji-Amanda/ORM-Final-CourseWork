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
import lk.ijse.bo.custom.TherapistBO;
import lk.ijse.bo.custom.impl.TherapistBOImpl;
import lk.ijse.dto.TherapistDTO;
import lk.ijse.view.tdm.TherapistTM;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TherapistTableFormController implements Initializable {

    private final TherapistBO therapistBO = new TherapistBOImpl();

    @FXML
    private TableColumn<TherapistTM, String> clmAvailability, clmTherapistName, clmSpeciality, clmTherapistId;

    @FXML
    private TableView<TherapistTM> tblTherapist;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clmTherapistId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmTherapistName.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmSpeciality.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        clmAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));

        // Load data into table
        loadTherapists();
    }

    private void loadTherapists() {
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
            tblTherapist.setItems(therapistTMList);
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
    void tblTherapistOnClick(MouseEvent event) {
        TherapistTM selectedItem = tblTherapist.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert("Warning", "Please select a therapist", Alert.AlertType.WARNING);
        }else {
            TherapySessionManagementFormController.getInstance().setTherapistId(selectedItem.getTherapistID());

        }
    }

}