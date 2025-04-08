package co.edu.uniquindio.banco.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Clase que representa el controlador de la vista Inicio
 * @author Grupo
 */
public class InicioControlador {

    /**
     * Método que permite ir a la vista de Iniciar Sesión
     * @param actionEvent Evento que representa el clic del botón
     */
    public void irIniciarSesion(ActionEvent actionEvent) {
        navegarVentana(actionEvent, "/login.fxml", "Banco - Iniciar Sesión");
    }

    /**
     * Método que permite ir a la vista de Registro de Cliente
     * @param actionEvent Evento que representa el clic del botón
     */
    public void irRegistroCliente(ActionEvent actionEvent) {
        navegarVentana(actionEvent, "/registro.fxml", "Banco - Registro de Cliente");
    }

    /**
     * Método que permite ir a la venana indicada por el nombre del archivo FXML
     * @param nombreArchivoFxml Nombre del archivo FXML
     * @param tituloVentana Título de la ventana
     */
    public void navegarVentana(ActionEvent event, String nombreArchivoFxml, String tituloVentana) {
        try {
            // Cerrar ventana actual (Inicio)
            Stage stageActual = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stageActual.close();

            // Cargar la nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreArchivoFxml));
            Parent root = loader.load();

            // Crear y mostrar nueva ventana
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(tituloVentana);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
