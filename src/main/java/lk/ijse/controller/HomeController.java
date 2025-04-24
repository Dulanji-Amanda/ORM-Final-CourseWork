package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private JFXButton btnhistory;

    @FXML
    private AnchorPane homepane;

    @FXML
    private Button logoutbtn;

    @FXML
    private Label timeLabel;

    @FXML
    private BarChart<String, Number> paymentBarChart;

    private SessionFactory factory;

    @FXML
    void logoutbtnOnAction(ActionEvent event) {
        navigateTo();
    }

    @FXML
    void btnHistoryOnAction(ActionEvent event) {
        loadUI("/view/TherapyHistory.fxml");
    }

    private void loadUI(String fxmlPath) {
        try {
            homepane.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(homepane.widthProperty());
            load.prefHeightProperty().bind(homepane.heightProperty());
            homepane.getChildren().add(load);

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        }
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginPage.fxml"));
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icon.png"))));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void navigateTo() {
        try {

            Stage stage = (Stage) homepane.getScene().getWindow();
            stage.close();

            start(new Stage());

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Define the formatter for date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        // Create a Timeline to update the label every 1 second
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            timeLabel.setText(now.format(formatter));
        }));

        clock.setCycleCount(Timeline.INDEFINITE); // Run forever
        clock.play(); // Start the clock
    }

}