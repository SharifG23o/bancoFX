package co.edu.uniquindio.banco.controlador;

import co.edu.uniquindio.banco.modelo.Sesion;
import co.edu.uniquindio.banco.modelo.entidades.Banco;
import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import co.edu.uniquindio.banco.controlador.PanelClienteControlador;


/**
 * Clase que representa el controlador de la vista de login
 * @author caflorezvi
 */
public class LoginControlador {

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

    public void IniciarSesion(ActionEvent event) {
        try {
            Usuario usuario = banco.iniciarSesionUsuario(txtIdentificacion.getText(), txtPassword.getText());
            Sesion sesion = Sesion.getInstancia();
            sesion.setUsuario(usuario);
            navegarVentana("/panelCliente.fxml", "Banco - Panel Principal");
        } catch (Exception e) {
            crearAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void navegarVentana(String nombreArchivoFxml, String tituloVentana) {
        try {
            Stage stageClose = (Stage) btnIniciarSesion.getScene().getWindow();
            stageClose.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(tituloVentana);
            stage.show();

        }catch (Exception e){
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

    public void cancelarLoginAction(ActionEvent event) {
    }
}

