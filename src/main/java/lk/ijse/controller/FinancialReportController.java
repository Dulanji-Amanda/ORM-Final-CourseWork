package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.bo.custom.impl.PaymentBOImpl;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.view.tdm.FinancialPaymentTM;
import lk.ijse.view.tdm.PaymentTM;

import java.util.ArrayList;

public class FinancialReportController {

    @FXML
    private TableView<PaymentTM> tblPayments;

    @FXML
    private TableColumn<PaymentDTO, String> colPaymentId;

    @FXML
    private TableColumn<PaymentDTO, Double> colAmount;

    @FXML
    private TableColumn<PaymentDTO, String> colStatus;

    @FXML
    private BarChart<String, Number> barChartPayments;

    private final PaymentBO paymentBO = new PaymentBOImpl();

    public void initialize() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadPayments();
        loadPaymentStatistics();
    }

    private void loadPayments() {
        try  {
            ArrayList<PaymentDTO> payments = paymentBO.loadAllPayments();
            ObservableList<PaymentTM> paymentTMS = FXCollections.observableArrayList();

            for (PaymentDTO paymentDTO  : payments) {
                FinancialPaymentTM financialPaymentTM = new FinancialPaymentTM(
                        paymentDTO.getPaymentId(),
                        paymentDTO.getAmount(),
                        paymentDTO.getStatus()
                );

                paymentTMS.add(financialPaymentTM);
            }
            tblPayments.setItems(paymentTMS);
        } catch (Exception e) {
            showAlert("Error", "Failed to load Payments!", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadPaymentStatistics() {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Payment Status");

        long completed = paymentBO.getCompletedPaymentCount();
        long pending = paymentBO.getPendingPaymentCount();

        series.getData().add(new XYChart.Data<>("Completed", completed));
        series.getData().add(new XYChart.Data<>("Pending", pending));

        barChartPayments.getData().add(series);
    }
}