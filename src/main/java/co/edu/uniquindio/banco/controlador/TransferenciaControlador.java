package co.edu.uniquindio.banco.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de controlar la creación de transferencias entre billeteras
 * @author caflorezvi
 */
public class TransferenciaControlador implements Initializable {



    @FXML
    private TextField txtNumeroCuenta;

    @FXML
    private TextField txtMonto;
    @FXML
    private ComboBox<String> boxCategoria;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boxCategoria.getItems().addAll(
                "VIAJES",
                "FACTURAS",
                "GASOLINA",
                "ROPA",
                "PAGO",
                "OTROS"
        );
    }


    @FXML
    public void transferirActionBtn(ActionEvent event) {
        try {
            String cuenta = txtNumeroCuenta.getText();
            String monto = txtMonto.getText();
            String categoria = boxCategoria.getValue();


            if (cuenta.isEmpty() || monto.isEmpty() || categoria == null) {
                Alert alertaError = new Alert(Alert.AlertType.WARNING);
                alertaError.setTitle("Campos Incompletos");
                alertaError.setHeaderText(null);
                alertaError.setContentText("Por favor, complete todos los campos antes de continuar.");
                alertaError.showAndWait();
                return;
            }


            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Transferencia Exitosa");
            alerta.setHeaderText(null);
            alerta.setContentText("Transferencia realizada correctamente.");
            alerta.showAndWait();


            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageActual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/panelCliente.fxml"));
            Parent root = loader.load();

            Stage nuevoStage = new Stage();
            nuevoStage.setScene(new Scene(root));
            nuevoStage.setTitle("Panel Principal");
            nuevoStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
