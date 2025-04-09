package co.edu.uniquindio.banco.modelo.entidades;

import co.edu.uniquindio.banco.config.Constantes;
import co.edu.uniquindio.banco.modelo.enums.Categoria;
import co.edu.uniquindio.banco.modelo.vo.PorcentajeGastosIngresos;
import co.edu.uniquindio.banco.modelo.vo.SaldoTransaccionesBilletera;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Clase que representa un banco con usuarios y billeteras
 * @version 1.0
 * @author caflorezvi
 */
@Getter
@Setter
public class Banco {

    public static Banco INSTANCIA;
    private List<Usuario> usuarios;
    private List<BilleteraVirtual> billeteras;

    private Banco(){
        this.usuarios = new ArrayList<>();
        this.billeteras = new ArrayList<>();
    }

    /**
     * Permite registrar un usuario en el banco y crear su billetera
     * @param id identificación del usuario
     * @param nombre nombre del usuario
     * @param direccion dirección del usuario
     * @param email email del usuario
     * @param password contraseña del usuario
     * @throws Exception si el id, nombre, dirección, email o password son nulos o vacíos o si el usuario ya existe
     */
    public void registrarUsuario(String id, String nombre, String direccion, String email, String password) throws Exception{

        if(id == null || id.isEmpty()){
            throw new Exception("El id es obligatorio");
        }

        if(nombre == null || nombre.isEmpty()){
            throw new Exception("El nombre es obligatorio");
        }

        if(direccion == null || direccion.isEmpty()){
            throw new Exception("La dirección es obligatoria");
        }

        if(email == null || email.isEmpty()){
            throw new Exception("El email es obligatorio");
        }

        if(password == null || password.isEmpty()){
            throw new Exception("La contraseña es obligatoria");
        }

        if(buscarUsuario(id) != null){
            throw new Exception("El usuario ya existe");
        }

        Usuario usuario = new Usuario(id, nombre, direccion, email, password);
        // Se agrega el usuario a la lista de usuarios
        usuarios.add(usuario);
        // Se registra la billetera del usuario
        registrarBilletera(usuario);
    }

    /**
     * Permite editar los datos de un usuario
     * @param usuarioAntiguo usuario a editar
     * @param id id nuevo
     * @param nombre nuevo nombre
     * @param direccion nueva direccion
     * @param email nuevo email
     * @param password nueva contrasenia
     * @throws Exception Ya existe un usuario con el mismo id
     */
    public void editarUsuario(Usuario usuarioAntiguo, String id, String nombre, String direccion, String email, String password) throws Exception {
        confirmarEditarUsuario(id, nombre, direccion, email, password);
        
        if (usuarioAntiguo.getId().equals(id)) {
            usuarioAntiguo.setNombre(nombre);
            usuarioAntiguo.setDireccion(direccion);
            usuarioAntiguo.setEmail(email);
            usuarioAntiguo.setPassword(password);
        } else if (buscarUsuario(id) != null) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo id.");
        } else {
            usuarioAntiguo.setId(id);
            usuarioAntiguo.setNombre(nombre);
            usuarioAntiguo.setDireccion(direccion);
            usuarioAntiguo.setEmail(email);
            usuarioAntiguo.setPassword(password);
        }
    }

    /**
     * Permite confirmar la edicion del usuario
     * @param id id nuevo
     * @param nombre nuevo nombre
     * @param direccion nueva direccion
     * @param email nuevo email
     * @param password nueva contrasenia
     * @throws Exception falta de datos
     */
    public void confirmarEditarUsuario(String id, String nombre, String direccion, String email, String password) throws IllegalArgumentException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El id es obligatorio.");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new IllegalArgumentException("La direccion es obligatoria.");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La cotraseña es obligatoria.");
        }
    }


    /**
     * Inicia sesión de un usuario buscando en la lista por su ID y contraseña.
     *
     * @param id El identificador del usuario.
     * @param password La contraseña del usuario.
     * @return El objeto {@code Usuario} correspondiente si las credenciales son válidas.
     * @throws Exception Si el ID o la contraseña están vacíos o nulos,
     *                   o si no se encuentra un usuario con esas credenciales.
     */
    public Usuario iniciarSesionUsuario(String id, String password) throws Exception {
        if (id == null || id.isEmpty()) {
            throw new Exception("El id es obligatorio");
        }
        if (password == null || password.isEmpty()) {
            throw new Exception("La contraseña es obligatoria");
        }

        Usuario usuarioBuscado = usuarios.stream()
                .filter(usuario -> usuario.getId().equals(id)
                        && usuario.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (usuarioBuscado == null) {
            throw new Exception("El usuario no existe");
        }

        return usuarioBuscado;
    }

    /**
     * Permite registrar una billetera para un usuario
     * @param usuario usuario al que se le va a registrar la billetera
     */
    public void registrarBilletera(Usuario usuario){
        String numero = crearNumeroBilletera();
        BilleteraVirtual billetera = new BilleteraVirtual(numero, 0, usuario);
        billeteras.add(billetera);
        System.out.println("Cuenta Creada : Numero: " + numero + "Usuario: " + usuario.getNombre());
    }

    /**
     * Crea un número de billetera único
     * @return número de billetera
     */
    private String crearNumeroBilletera(){
        String numero = generarNumeroAleatorio();
        while(buscarBilletera(numero) != null){
            numero = generarNumeroAleatorio();
        }
        return numero;
    }

    /**
     * Permite generar un número aleatorio de 10 dígitos para la billetera
     * @return número aleatorio
     */
    private String generarNumeroAleatorio(){
        String numero = "";
        for(int i = 0; i < 10; i++){
            numero += ""+((int) (Math.random() * 10));
        }
        return numero;
    }

    /**
     * Permite buscar una billetera por su número
     * @param numero número de la billetera
     * @return billetera encontrada o null si no existe
     */
    public BilleteraVirtual buscarBilletera(String numero){
        return billeteras.stream()
                .filter(billetera -> billetera.getNumero().equals(numero))
                .findFirst()
                .orElse(null);
    }

    /**
     * Permite consultar el saldo y las transacciones de una billetera de un usuario
     * @param numeroIdentificacion identificación del usuario
     * @param password contraseña del usuario
     * @return Saldo y transacciones de la billetera
     * @throws Exception si el usuario no existe o la contraseña es incorrecta
     */
    public SaldoTransaccionesBilletera consultarSaldoYTransacciones(String numeroIdentificacion, String password)throws Exception{
        Usuario usuario = buscarUsuario(numeroIdentificacion);

        if(usuario == null){
            throw new Exception("El usuario no existe");
        }

        if(!usuario.getPassword().equals(password)){
            throw new Exception("Contraseña incorrecta");
        }

        BilleteraVirtual billetera = buscarBilleteraUsuario(usuario.getId());

        return new SaldoTransaccionesBilletera(
                billetera.consultarSaldo(),
                billetera.obtenerTransacciones()
        );
    }

    /**
     * Permite buscar la billetera de un usuario
     * @param idUsuario identificación del usuario
     * @return billetera del usuario o null si no existe
     */
    public BilleteraVirtual buscarBilleteraUsuario(String idUsuario){
        return billeteras.stream()
                .filter(billetera -> billetera.getUsuario().getId().equals(idUsuario))
                .findFirst()
                .orElse(null);
    }

    /**
     * Permite buscar un usuario por su id
     * @param id id del usuario
     * @return usuario encontrado o null si no existe
     */
    public Usuario buscarUsuario(String id){
        return usuarios.stream()
                .filter(usuario -> usuario.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Permite realizar una recarga a una billetera
     * @param numeroBiletera número de la billetera
     * @param monto monto a recargar
     * @throws Exception si la billetera no existe
     */
    public void recargarBilletera(String numeroBiletera, float monto) throws Exception{
        BilleteraVirtual billetera = buscarBilletera(numeroBiletera);
        if(billetera == null){
            throw new Exception("La billetera no existe.");
        }

        Transaccion transaccion = new Transaccion(
                UUID.randomUUID().toString(),
                monto,
                LocalDateTime.now(),
                Categoria.RECARGA,
                billetera,
                billetera,
                0
        );

        billetera.depositar(monto, transaccion);
    }

    /**
     * Permite realizar una transaccion
     * @param numeroBilleteraOrigen número de la billetera de origen
     * @param numeroBilleteraDestino numero de billetera de destino
     * @param monto monto a transferir
     * @param categoria categoria de la transferencia
     * @throws Exception si la billetera no existe
     */
    public void realizarTransferencia(String numeroBilleteraOrigen, String numeroBilleteraDestino, float monto, Categoria categoria) throws Exception{
        BilleteraVirtual billeteraOrigen = buscarBilletera(numeroBilleteraOrigen);
        BilleteraVirtual billeteraDestino = buscarBilletera(numeroBilleteraDestino);

        if(billeteraOrigen == null || billeteraDestino == null){
            throw new Exception("La billetera no existe");
        }

        if(!billeteraOrigen.tieneSaldo(monto)){
            throw new Exception("Saldo insuficiente");
        }

        if(monto <= 0){
            throw new Exception("Ingrese monto mayor a 0");
        }

        Transaccion transaccion = new Transaccion(
                UUID.randomUUID().toString(),
                monto,
                LocalDateTime.now(),
                categoria,
                billeteraOrigen,
                billeteraDestino,
                Constantes.COMISION
        );

        billeteraOrigen.retirar(monto, transaccion);
        billeteraDestino.depositar(monto, transaccion);

    }

    /**
     * Permite obtener la transferencia
     * @param numeroBilletera número de la billetera
     */
    public List<Transaccion> obtenerTransacciones(String numeroBilletera){
        BilleteraVirtual billetera = buscarBilletera(numeroBilletera);
        if(billetera != null){
            return billetera.obtenerTransacciones();
        }
        return new ArrayList<>();
    }

    /**
     * Permite obtener transaccioneas en un periodo de tiempo
     * @param numeroBilletera número de la billetera
     * @param inicio fecha de inicio
     * @param fin fecha de final
     */
    public List<Transaccion> obtenerTransaccionesPeriodo(String numeroBilletera, LocalDateTime inicio, LocalDateTime fin){
        BilleteraVirtual billetera = buscarBilletera(numeroBilletera);
        if(billetera != null){
            return billetera.obtenerTransaccionesPeriodo(inicio, fin);
        }
        return new ArrayList<>();
    }

    /**
     * Permite obtener el porcentaje de gastos
     * @param numeroBilletera número de la billetera
     * @param mes mes a evaluar
     * @param anio anio a evaluar
     * @throws Exception si la billetera no existe
     */
    public PorcentajeGastosIngresos obtenerPorcentajeGastosIngresos(String numeroBilletera, int mes, int anio) throws Exception{
        BilleteraVirtual billetera = buscarBilletera(numeroBilletera);
        if(billetera == null){
            throw new Exception("La billetera no existe");
        }
        return billetera.obtenerPorcentajeGastosIngresos(mes, anio);
    }

    /**
     * Metodo Get para la instancia
     */
    public static Banco getInstancia(){
        if(INSTANCIA == null){
            INSTANCIA = new Banco();
        }
        return INSTANCIA;
    }
}