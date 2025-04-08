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
 * @author caflorezvi
 */
public class TransferenciaControlador implements Initializable {



    @FXML
    private TextField txtNumeroCuenta;

    @FXML
    private TextField txtMonto;
    @FXML
    private ComboBox<String> boxCategoria;
    @FXML
    private Button btnCancelarRecarga;

    private final Banco banco = Banco.getInstancia();
    private final Sesion sesion = Sesion.getInstancia();


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

            Usuario usuarioActual = Sesion.getInstancia().getUsuario();

            if (usuarioActual == null) {
                mostrarAlerta("No hay usuario logueado.", Alert.AlertType.ERROR);
                return;
            }

            BilleteraVirtual billeteraOrigen = banco.buscarBilleteraUsuario(usuarioActual.getId());

            if (billeteraOrigen == null) {
                mostrarAlerta("No se encontró una billetera asociada al usuario.", Alert.AlertType.ERROR);
                return;
            }

            String numeroBilleteraOrigen = billeteraOrigen.getNumero();

            if (cuentaDestino.isEmpty() || montoStr.isEmpty() || categoriaSeleccionada == null) {
                mostrarAlerta("Por favor, complete todos los campos antes de continuar.", Alert.AlertType.WARNING);
                return;
            }

            float monto;
            try {
                monto = Float.parseFloat(montoStr);
                if (monto <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                mostrarAlerta("Ingrese un monto válido mayor a 0.", Alert.AlertType.ERROR);
                return;
            }

            Categoria categoria = Categoria.valueOf(categoriaSeleccionada.toUpperCase());

            banco.realizarTransferencia(numeroBilleteraOrigen, cuentaDestino, monto, categoria);

            mostrarAlerta("Transferencia realizada correctamente.", Alert.AlertType.INFORMATION);

            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageActual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/panelCliente.fxml"));
            Parent root = loader.load();

            Stage nuevoStage = new Stage();
            nuevoStage.setScene(new Scene(root));
            nuevoStage.setTitle("Panel Principal");
            nuevoStage.show();

        } catch (Exception e) {
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    /**
     * Método que se encarga de mostrar una alerta en pantalla
     * @param mensaje mensaje a mostrar
     * @param tipo tipo de alerta
     */
    public void mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void cancelarTransferenciaAction(ActionEvent event) {
    }
}
