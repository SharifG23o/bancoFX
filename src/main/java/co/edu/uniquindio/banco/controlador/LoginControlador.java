package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.Sesion;
import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Clase que representa el controlador de la vista de login
 * @author grupo
 */
public class LoginControlador extends Controller {

    @FXML
    private Button btnIniciarSesion;
    @FXML
    private TextField txtIdentificacion;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnCancelarLogin;

    private final Sesion sesion = Sesion.getInstancia();
    private final Banco banco = Banco.getInstancia();

    /**
     * Método que se encarga de Iniciar Sesion
     * @param event evento de accion
     */
    public void IniciarSesion(ActionEvent event) {
        try {
            Usuario usuario = banco.iniciarSesionUsuario(txtIdentificacion.getText(), txtPassword.getText());
            sesion.setUsuario(usuario);
            navegarVentana(btnIniciarSesion, "/panelCliente.fxml", "Banco - Panel Principal");
        } catch (Exception e) {
            crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Método que se encarga de cancelar el Login
     * @param event evento de accion
     */
    public void cancelarLoginAction(ActionEvent event) {
        navegarVentana(btnCancelarLogin, "/inicio.fxml", "Banco - Inicio");
    }
}

