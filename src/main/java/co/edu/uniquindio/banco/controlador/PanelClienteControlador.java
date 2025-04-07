package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.Sesion;
import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.banco.modelo.entidades.Transaccion;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import co.edu.uniquindio.banco.modelo.enums.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

public class PanelClienteControlador implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTabla();
        usuario = sesion.getUsuario();
        initData(usuario);
    }

    public void initTabla() {
        colTipo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTipo().name()));
        colFecha.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))));
        colValor.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleFloatProperty(cellData.getValue().getMonto()).asObject());
        colUsuario.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getBilleteraOrigen().getUsuario().getNombre()));
        colCategoria.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getTipo()));
    }

    public void initData(Usuario usuario) {
        billetera = banco.buscarBilleteraUsuario(usuario.getId());
        cargarDatosUsuario(billetera);
        cargarTransacciones(billetera);
    }

    public void cargarDatosUsuario(BilleteraVirtual billetera) {
        String nombre = billetera.getUsuario().getNombre();
        String numeroCuenta = billetera.getNumero();

        bienvenidaClienteTxt.setText("Bienvenido/a, " + nombre + ", aquí podrá ver sus transacciones.");
        cuentaTxt.setText("Nro. de Cuenta : " + numeroCuenta);
    }

    private void cargarTransacciones(BilleteraVirtual billetera) {
        ObservableList<Transaccion> lista = FXCollections.observableArrayList(billetera.obtenerTransacciones());
        tblPanelCliente.setItems(lista);
    }

    public void cerrarSesionAction(ActionEvent event) {
        try {
            sesion.cerrarSesion();
            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageActual.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/inicio.fxml"));
            Parent root = loader.load();

            Stage nuevoStage = new Stage();
            nuevoStage.setScene(new Scene(root));
            nuevoStage.setTitle("Inicio");
            nuevoStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void consultarAction(ActionEvent event) {
        String nombre = usuario.getNombre();
        float saldo = billetera.consultarSaldo();
        crearAlerta(nombre + "tienes un saldo en tu cuenta de " + saldo + "pesos.", Alert.AlertType.INFORMATION);
    }

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

    public void actualizarAction(ActionEvent event) {
        try {
            Stage stageActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageActual.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/registro.fxml"));
            Parent root = loader.load();
            RegistroControlador controlador = loader.getController();
            controlador.actualizarDatos(usuario);

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
