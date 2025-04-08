package co.edu.uniquindio.banco.modelo;


import co.edu.uniquindio.banco.modelo.entidades.Usuario;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase encargada de gestionar los datos de cada inicio de sesion
 */
public class Sesion {

    public static Sesion INSTANCIA;

    @Getter @Setter
    private Usuario usuario;

    private Sesion() {
    }

    /**
     * Método Get para la instancia
     */
    public static Sesion getInstancia() {
        if (INSTANCIA == null) {
            INSTANCIA = new Sesion();
        }
        return INSTANCIA;
    }

    public void cerrarSesion() {
        usuario = null;
    }
}
