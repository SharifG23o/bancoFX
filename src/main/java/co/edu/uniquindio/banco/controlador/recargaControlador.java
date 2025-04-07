package co.edu.uniquindio.banco.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class recargaControlador {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtIngresarMonto;

    @FXML
    private Button btnAceptarRecarga;

    @FXML
    void aceptarRecargaAction(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert txtIngresarMonto != null : "fx:id=\"txtIngresarMonto\" was not injected: check your FXML file 'recarga.fxml'.";
        assert btnAceptarRecarga != null : "fx:id=\"btnAceptarRecarga\" was not injected: check your FXML file 'recarga.fxml'.";

    }
}
