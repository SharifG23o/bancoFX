package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.Sesion;
import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.banco.modelo.entidades.Transaccion;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import co.edu.uniquindio.banco.modelo.enums.Categoria;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de controlar el panel del cliente
 * @author Grupo
 */
public class PanelClienteControlador implements Initializable {

    public Button btnRecargar;
    public Button btnCerrarSesion;
    public Button btnConsultar;
    public Button btnTransferir;
    public Button btnActualizar;

    public TableView<Transaccion> tblPanelCliente;
    public TableColumn<Transaccion, String> colTipo;
    public TableColumn<Transaccion, String> colFecha;
    public TableColumn<Transaccion, Float> colValor;
    public TableColumn<Transaccion, String> colUsuario;
    public TableColumn<Transaccion, Categoria> colCategoria;

    public Text cuentaTxt;
    public Text bienvenidaClienteTxt;

    private final Banco banco = Banco.getInstancia();
    private final Sesion sesion = Sesion.getInstancia();
    private Usuario usuario;
    private BilleteraVirtual billetera;

    /**
     * Método que se encarga de inicializar
     * @param location localizacion
     * @param resources recursos
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTabla();
        usuario = sesion.getUsuario();
        initData(usuario);
    }

    /**
     * Método que se encarga de inicializar la informacion de la tabla
     */
    public void initTabla() {
        colTipo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipo().name()));
        colFecha.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
        colValor.setCellValueFactory(cellData ->
                new SimpleFloatProperty(cellData.getValue().getMonto()).asObject());
        colUsuario.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getBilleteraOrigen().getUsuario().getNombre()));
        colCategoria.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getTipo()));
    }

    /**
     * Método que se encarga de inicializar los datos
     * @param usuario usuario
     */
    public void initData(Usuario usuario) {
        billetera = banco.buscarBilleteraUsuario(usuario.getId());
        cargarDatosUsuario(billetera);
        cargarTransacciones(billetera);
    }

    /**
     * Método que se encarga de cargar los datos del usuario
     * @param billetera billetera actual
     */
    public void cargarDatosUsuario(BilleteraVirtual billetera) {
        String nombre = billetera.getUsuario().getNombre();
        String numeroCuenta = billetera.getNumero();

        bienvenidaClienteTxt.setText("Bienvenido/a, " + nombre + ", aquí podrá ver sus transacciones.");
        cuentaTxt.setText("Nro. de Cuenta : " + numeroCuenta);
    }

    /**
     * Método que se encarga de cargar las transaccciones
     * @param billetera billetera
     */
    private void cargarTransacciones(BilleteraVirtual billetera) {
        ObservableList<Transaccion> lista = FXCollections.observableArrayList(billetera.obtenerTransacciones());
        tblPanelCliente.setItems(lista);
    }

    /**
     * Método que se encarga de cerrar la sesion
     * @param event evento de accion
     */
    public void cerrarSesionAction(ActionEvent event) {
        try {
            sesion.cerrarSesion();
            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageActual.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            Stage nuevoStage = new Stage();
            nuevoStage.setScene(new Scene(root));
            nuevoStage.setTitle("Login");
            nuevoStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que se encarga de consultar las acciones
     * @param event evento de accion
     */
    public void consultarAction(ActionEvent event) {
        String nombre = usuario.getNombre();
        float saldo = billetera.consultarSaldo();
        crearAlerta(nombre + " tienes un saldo en tu cuenta de $" + saldo + " pesos.", Alert.AlertType.INFORMATION);
    }

    /**
     * Método que se encarga de realizar la transferencia
     * @param event evento de accion
     */
    public void transferirAction(ActionEvent event) {
        try {
            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageActual.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/transferencia.fxml"));
            Parent root = loader.load();

            Stage nuevoStage = new Stage();
            nuevoStage.setScene(new Scene(root));
            nuevoStage.setTitle("Transferencia");
            nuevoStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que se encarga de recargar la billetera
     * @param event evento de accion
     */
    public void recargarAction(ActionEvent event) {
        try {
            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageActual.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/recarga.fxml"));
            Parent root = loader.load();

            RecargaControlador controlador = loader.getController();
            controlador.setBilleteraActual(billetera);

            Stage nuevoStage = new Stage();
            nuevoStage.setScene(new Scene(root));
            nuevoStage.setTitle("Recargar Saldo");
            nuevoStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que se encarga de actualizar los datos
     * @param event evento de accion
     */
    public void actualizarAction(ActionEvent event) {
        try {
            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageActual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/registro.fxml"));
            Parent root = loader.load();

            RegistroControlador controlador = loader.getController();
            controlador.actualizarDatos(usuario);
            controlador.setEsRegistro(false); // ✅ marcamos que es edición

            Stage nuevoStage = new Stage();
            nuevoStage.setScene(new Scene(root));
            nuevoStage.setTitle("Actualización de Datos");
            nuevoStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que se encarga de mostrar una alerta en pantalla
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
}
