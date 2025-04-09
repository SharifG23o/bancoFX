package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.Sesion;
import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Clase que representa el controlador de la ventana de registro de usuario
 * @author Grupo
 */
public class RegistroControlador extends Controller {

    @FXML
    private Label lblTitulo;
    @FXML
    private TextField txtIdentificacion;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private TextField txtDireccion;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button registroButton;
    @FXML
    private Button btnCancelarRegistro;

    private final Banco banco = Banco.getInstancia();
    private final Sesion sesion = Sesion.getInstancia();
    private Usuario usuarioAntiguo;
    boolean actualizar;
    boolean cancelar;

    /**
     * Método que se ejecuta al presionar el botón de registrarse
     * @param actionEvent evento de acción
     */
    public void registrarse(ActionEvent actionEvent) {
        try {
            if (actualizar) {
                actualizarDatos();
            } else {
                banco.registrarUsuario(
                        txtIdentificacion.getText(),
                        txtNombre.getText(),
                        txtDireccion.getText(),
                        txtCorreo.getText(),
                        txtPassword.getText());
                crearAlerta("Usuario registrado correctamente", Alert.AlertType.INFORMATION);
                navegarVentana(registroButton, "/inicio.fxml", "Banco - Inicio");
            }
        } catch (Exception e) {
            crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void actualizarDatos() {
        try {
            banco.editarUsuario(
                    usuarioAntiguo,
                    txtIdentificacion.getText().trim(),
                    txtNombre.getText().trim(),
                    txtDireccion.getText().trim(),
                    txtCorreo.getText().trim(),
                    txtPassword.getText().trim());
            crearAlerta("Usuario actualizado correctamente", Alert.AlertType.INFORMATION);
            actualizar = false;
            navegarVentana(registroButton, "/panelCliente.fxml", "Banco - Panel de Cliente");
        } catch (Exception e){
            crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Método que se encarga de actualizar los datos
     * @param usuario usuario
     */
    public void actualizarDatos(Usuario usuario){
        this.usuarioAntiguo = usuario;

        txtIdentificacion.setText(usuario.getId());
        txtNombre.setText(usuario.getNombre());
        txtCorreo.setText(usuario.getEmail());
        txtDireccion.setText(usuario.getDireccion());
        txtPassword.setText(usuario.getPassword());

        lblTitulo.setText("Actualización de Datos");
        registroButton.setText("Actualizar");
        btnCancelarRegistro.setText("Cancelar");
        usuarioAntiguo = usuario;
        actualizar = true;
        cancelar = true;
    }

    /**
     * Método que se encarga de cancelar el registro
     * @param event evento de accion
     */
    @FXML
    public void cancelarRegistroAction(ActionEvent event) {
        cancelarRegistro();
    }

    private void cancelarRegistro() {
        try {
            if (cancelar) {
                navegarVentana(btnCancelarRegistro, "/panelCliente.fxml", "Banco - Panel de Cliente");
                cancelar = false;
            } else {
                navegarVentana(btnCancelarRegistro, "/inicio.fxml", "Banco - Inicio");
            }
        } catch (Exception e) {
            crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
