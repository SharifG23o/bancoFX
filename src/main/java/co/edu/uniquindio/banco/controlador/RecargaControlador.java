package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.banco.modelo.entidades.Transaccion;
import co.edu.uniquindio.banco.modelo.enums.Categoria;
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

/**
 * Clase que se encarga de controlar las recargas de las billeteras
 * @author caflorezvi
 */
public class RecargaControlador {

    @FXML
    private TextField txtIngresarMonto;

    @FXML
    private Button btnAceptarRecarga;

    @FXML
    private Button btnCancelarRecarga;

    @Setter
    private BilleteraVirtual billeteraActual;
    private final Banco banco = Banco.getInstancia();

    /**
     * Método que se encarga de aceptar la recarga
     * @param event evento de accion
     */
    @FXML
    void aceptarRecargaAction(ActionEvent event) {
        try {
            float monto = Float.parseFloat(txtIngresarMonto.getText());
            String numero = billeteraActual.getNumero();
            banco.recargarBilletera(numero, monto);
            crearAlerta("Recargado exitosamente.", Alert.AlertType.INFORMATION);
            volver(event);
        } catch (NumberFormatException e) {
            crearAlerta("Ingrese un formato valido al monto.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Método que se encarga de volver de panel
     * @param event evento de accion
     */
    private void volver(ActionEvent event) {
        try {
            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageActual.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/panelCliente.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Banco - Panel Principal");
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Método que se encarga de mostrar una alerta
     * @param mensaje mensaje a mostrar
     * @param tipo tipo de alerta
     */
    public void crearAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Método que se encarga de cancelar la recarga
     * @param event evento de accion
     */
    public void cancelarRecargaAction(ActionEvent event) {
        try {
            Stage stageClose = (Stage) btnCancelarRecarga.getScene().getWindow();
            stageClose.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/panelCliente.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Banco - Panel Principal");
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
