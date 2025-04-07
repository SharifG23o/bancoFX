package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.banco.modelo.entidades.Transaccion;
import co.edu.uniquindio.banco.modelo.enums.Categoria;
import co.edu.uniquindio.banco.modelo.enums.TipoTransaccion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.UUID;

public class recargaControlador implements Initializable {

    @FXML
    private TextField txtIngresarMonto;

    @FXML
    private Button btnAceptarRecarga;

    @Setter
    private BilleteraVirtual billeteraActual;

    @FXML
    void aceptarRecargaAction(ActionEvent event) {
        try {
            float monto = Float.parseFloat(txtIngresarMonto.getText());

            if (monto <= 0) {
                throw new Exception("El monto debe ser mayor a cero.");
            }


            Transaccion transaccion = new Transaccion();
            transaccion.setId(UUID.randomUUID().toString());
            transaccion.setMonto(monto);
            transaccion.setFecha(LocalDateTime.now());
            transaccion.setTipo(Categoria.RECARGA);
            transaccion.setBilleteraDestino(billeteraActual);


            billeteraActual.depositar(monto, transaccion);


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/co/edu/uniquindio/banco/vista/panelCliente.fxml"));
            Parent root = loader.load();


            PanelClienteControlador controlador = loader.getController();
            controlador.setBilleteraActual(billeteraActual);


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (NumberFormatException e) {
            mostrarAlerta("Monto inválido", "Por favor, ingresa un número válido.");
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert txtIngresarMonto != null : "fx:id=\"txtIngresarMonto\" was not injected: check your FXML file 'recarga.fxml'.";
        assert btnAceptarRecarga != null : "fx:id=\"btnAceptarRecarga\" was not injected: check your FXML file 'recarga.fxml'.";
    }
}
