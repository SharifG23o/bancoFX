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
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import co.edu.uniquindio.banco.controlador.PanelClienteControlador;

import java.awt.*;

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

    private final Sesion sesion = Sesion.getInstancia();
    private final Banco banco = Banco.getInstancia();

    public void IniciarSesion(ActionEvent event) {
        try {
            Usuario usuario = banco.iniciarSesionUsuario(txtIdentificacion.getText(), txtPassword.getText());
            Sesion sesion = Sesion.getInstancia();
            sesion.setUsuario(usuario);
            navegarVentana("/panelCliente.fxml", "Banco - Panel Principal");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public void navegarVentana(String nombreArchivoFxml, String tituloVentana) {
        try {

            // Cargar la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(tituloVentana);

            // Mostrar la nueva ventana
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

