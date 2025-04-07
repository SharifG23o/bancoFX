package co.edu.uniquindio.banco.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que se encarga de controlar la creación de transferencias entre billeteras
 * @author caflorezvi
 */
public class TransferenciaControlador implements Initializable {



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


    public void transferirActionBtn(ActionEvent event) {
    }
}
