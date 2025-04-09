package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.Sesion;
import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.banco.modelo.entidades.Transaccion;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
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
public class RecargaControlador extends Controller implements Initializable {

    @FXML
    private TextField txtIngresarMonto;
    @FXML
    private Button btnAceptarRecarga;
    @FXML
    private Button btnCancelarRecarga;

    BilleteraVirtual billeteraActual;

    private final Banco banco = Banco.getInstancia();
    private final Sesion sesion = Sesion.getInstancia();

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
            volver();
        } catch (NumberFormatException e) {
            crearAlerta("Ingrese un formato valido al monto.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Método que se encarga de volver de panel
     */
    private void volver() {
        navegarVentana(btnAceptarRecarga, "/panelCliente.fxml", "Banco - Panel Principal");
    }

    /**
     * Método que se encarga de cancelar la recarga
     * @param event evento de accion
     */
    public void cancelarRecargaAction(ActionEvent event) {
        navegarVentana(btnCancelarRecarga, "/panelCliente.fxml", "Banco - Panel Principal");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Usuario usuario = sesion.getUsuario();
        billeteraActual = banco.buscarBilleteraUsuario(usuario.getId());
    }
}
