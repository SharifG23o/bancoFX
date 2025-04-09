package co.edu.uniquindio.banco.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Clase que representa el controlador de la vista Inicio
 * @author Grupo
 */
public class InicioControlador extends Controller {

    @FXML
    private Button sesionbutton;
    @FXML
    private Button registrarButton;

    /**
     * Método que permite ir a la vista de Iniciar Sesión
     * @param actionEvent Evento que representa el clic del botón
     */
    public void irIniciarSesion(ActionEvent actionEvent) {
        navegarVentana(sesionbutton, "/login.fxml", "Banco - Iniciar Sesión");
    }

    /**
     * Método que permite ir a la vista de Registro de Cliente
     * @param actionEvent Evento que representa el clic del botón
     */
    public void irRegistroCliente(ActionEvent actionEvent) {
        navegarVentana(registrarButton, "/registro.fxml", "Banco - Registro de Cliente");
    }
}
