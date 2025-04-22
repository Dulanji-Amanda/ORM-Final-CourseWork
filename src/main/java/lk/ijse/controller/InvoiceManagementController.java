package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.view.tdm.InvoiceTM;

import java.time.LocalDate;

public class InvoiceManagementController {

    @FXML
    private JFXButton btnAddNewPatient;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnExportPDF;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<InvoiceTM, LocalDate> colDate;

    @FXML
    private TableColumn<InvoiceTM, String> colInvoiceId;

    @FXML
    private TableColumn<InvoiceTM, String> colStatus;

    @FXML
    private TableColumn<InvoiceTM, Double> colTotalAmount;

    @FXML
    private DatePicker dpInvoiceDate;

    @FXML
    private ImageView imgHome;

    @FXML
    private TableView<InvoiceTM> tblInvoice;

    @FXML
    private TextField txtInvoiceId;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtTotalAmount;

    @FXML
    void btnAddNew_OnAction(ActionEvent event) {

    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) {

    }

    @FXML
    void btnExportPDF_OnAction(ActionEvent event) {

    }

    @FXML
    void btnSave_OnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdate_OnAction(ActionEvent event) {

    }

    @FXML
    void navigateToHome(MouseEvent event) {

    }

    @FXML
    void searchPatient(KeyEvent event) {

    }

}