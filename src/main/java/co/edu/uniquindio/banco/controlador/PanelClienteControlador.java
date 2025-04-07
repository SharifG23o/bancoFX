package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.BilleteraVirtual;
import co.edu.uniquindio.banco.modelo.entidades.Transaccion;
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

    private BilleteraVirtual billeteraActual;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    public void setBilleteraActual(BilleteraVirtual billetera) {
        this.billeteraActual = billetera;

        if (billetera != null && billetera.getUsuario() != null) {
            String nombre = billetera.getUsuario().getNombre();
            String numeroCuenta = billetera.getNumero();

            cuentaTxt.setText("Nro. de Cuenta : " + numeroCuenta);
            bienvenidaClienteTxt.setText("Bienvenido/a, " + nombre + ", aquí podrá ver sus transacciones.");

            cargarTransacciones();
        }
    }

    public void cerrarSesionAction(ActionEvent event) {
        try {
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
        cargarTransacciones();
    }

    private void cargarTransacciones() {
        if (billeteraActual != null) {
            ObservableList<Transaccion> lista = FXCollections.observableArrayList(billeteraActual.obtenerTransacciones());
            tblPanelCliente.setItems(lista);
        }
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
        
    }
}
