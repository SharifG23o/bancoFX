package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;



/**
 * Clase que representa el controlador de la ventana de registro de usuario
 * @author caflorezvi
 */
public class RegistroControlador {

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

    private final Banco banco = Banco.getInstancia();
    private Usuario usuarioAntiguo;

    /**
     * Método que se ejecuta al presionar el botón de registrarse
     * @param actionEvent evento de acción
     */
    public void registrarse(ActionEvent actionEvent) {
        realizarRegistro(true);
    }

    private void realizarRegistro(boolean registro) {
        try {
            if (registro) {
                banco.registrarUsuario(
                        txtIdentificacion.getText(),
                        txtNombre.getText(),
                        txtDireccion.getText(),
                        txtCorreo.getText(),
                        txtPassword.getText() );
                crearAlerta("Usuario registrado correctamente", Alert.AlertType.INFORMATION);
            } else {
                banco.editarUsuario(
                        usuarioAntiguo,
                        txtIdentificacion.getText(),
                        txtNombre.getText(),
                        txtDireccion.getText(),
                        txtCorreo.getText(),
                        txtPassword.getText() );
                crearAlerta("Usuario actualizado correctamente", Alert.AlertType.INFORMATION);
            }
            cerrarVentana();
        }catch (Exception e){
            crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
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

    /**
     * Método que se encarga de cerrar la ventana actual
     */
    public void cerrarVentana(){
        Stage stage = (Stage) txtIdentificacion.getScene().getWindow();
        stage.close();
    }

    public void actualizarDatos(Usuario usuario){
        txtIdentificacion.setText(usuario.getId());
        txtNombre.setText(usuario.getNombre());
        txtCorreo.setText(usuario.getEmail());
        txtDireccion.setText(usuario.getDireccion());
        txtPassword.setText(usuario.getPassword());

        lblTitulo.setText("Actualización de Datos");
        registroButton.setText("Actualizar");
        usuarioAntiguo = usuario;
    }

    public void cancelarRegistroAction(ActionEvent event) {
    }
}
