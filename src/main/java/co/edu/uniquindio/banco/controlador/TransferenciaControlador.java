package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import co.edu.uniquindio.banco.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.banco.modelo.Sesion;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de controlar la creación de transferencias entre billeteras
 * @author Grupo
 */
public class TransferenciaControlador extends Controller implements Initializable {

    @FXML
    private TextField txtNumeroCuenta;
    @FXML
    private TextField txtMonto;
    @FXML
    private ComboBox<String> boxCategoria;
    @FXML
    private Button btnTransferir;
    @FXML
    private Button btnCancelarTransferencia;

    private final Banco banco = Banco.getInstancia();
    private final Sesion sesion = Sesion.getInstancia();

    /**
     * Método que se encarga de inicializar
     * @param location localizacion
     * @param resources recursos
     */
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

    /**
     * Método que se encarga de realizar las transferencias
     * @param event evento de acción
     */
    @FXML
    public void transferirActionBtn(ActionEvent event) {
        try {
            String cuentaDestino = txtNumeroCuenta.getText();
            String montoStr = txtMonto.getText();
            String categoriaSeleccionada = boxCategoria.getValue();
            Usuario usuarioActual = sesion.getUsuario();
            BilleteraVirtual billeteraOrigen = banco.buscarBilleteraUsuario(usuarioActual.getId());
            String numeroBilleteraOrigen = billeteraOrigen.getNumero();

            if (cuentaDestino.isEmpty() || montoStr.isEmpty() || categoriaSeleccionada == null) {
                crearAlerta("Por favor, complete todos los campos antes de continuar.", Alert.AlertType.WARNING);
                return;
            }

            float monto;
            try {
                monto = Float.parseFloat(montoStr);
                if (monto <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                crearAlerta("Ingrese un monto válido mayor a 0.", Alert.AlertType.ERROR);return;
            }

            Categoria categoria = Categoria.valueOf(categoriaSeleccionada.toUpperCase());
            banco.realizarTransferencia(numeroBilleteraOrigen, cuentaDestino, monto, categoria);
            crearAlerta("Transferencia realizada correctamente.", Alert.AlertType.INFORMATION);

            navegarVentana(btnTransferir, "/panelCliente.fxml", "Banco - Panel Principal");
        } catch (Exception e) {
            crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Método que se encarga de cancelar una transferencia
     * @param event evento de accion
     */
    public void cancelarTransferenciaAction(ActionEvent event) {
        navegarVentana(btnCancelarTransferencia, "/panelCliente.fxml", "Banco - Panel Principal");
    }
}
