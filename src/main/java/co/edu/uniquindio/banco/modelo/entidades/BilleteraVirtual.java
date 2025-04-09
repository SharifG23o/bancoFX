package co.edu.uniquindio.banco.modelo.entidades;

import co.edu.uniquindio.banco.config.Constantes;
import co.edu.uniquindio.banco.modelo.vo.PorcentajeGastosIngresos;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BilleteraVirtual {
    private String numero;
    private float saldo;
    private Usuario usuario;
    private ArrayList<Transaccion> transacciones;

    /**
     * Constructor de Billetera Virtual
     * @param numero numero de billetera
     * @param saldo saldo de la billetera
     * @param usuario usuario duenio
     */
    public BilleteraVirtual(String numero, float saldo, Usuario usuario) {
        this.numero = numero;
        this.saldo = saldo;
        this.usuario = usuario;
        this.transacciones = new ArrayList<>();
    }

    /**
     * Permite verificar si la billetera tiene saldo suficiente
     * @param monto monto necesario
     */
    public boolean tieneSaldo(float monto) {
        float montoConComision = monto + Constantes.COMISION;
        return saldo >= montoConComision;
    }

    /**
     * Permite retirar dinero de la billetera
     * @param monto monto a retirar
     * @param transaccion transacion en proceso
     */
    public void retirar(float monto, Transaccion transaccion) throws Exception{

        float montoConComision = monto + Constantes.COMISION;

        if (montoConComision <= 0){
            throw new Exception("El monto a retirar debe ser mayor a cero");
        }

        transaccion.setComision(Constantes.COMISION);

        saldo -= montoConComision;
        transacciones.add(transaccion);
    }

    /**
     * Permite depositar dinero en la billetera
     * @param monto monto a depositar
     * @param transaccion transacion en proceso
     */
    public void depositar(float monto, Transaccion transaccion) throws Exception {

        if (monto <= 0){
            throw new Exception("El monto a retirar debe ser mayor a cero");
        }

        saldo += monto;
        transacciones.add(transaccion);
    }

    /**
     * Permite obtener transacciones en un periodo de tiempo
     * @param inicio inicio del periodo
     * @param fin fin del periodo
     */
    public ArrayList<Transaccion> obtenerTransaccionesPeriodo(LocalDateTime inicio, LocalDateTime fin) {

        ArrayList<Transaccion> transaccionesMes = new ArrayList<>();

        for (int i = 0; i < transacciones.size(); i++) {
            if(transacciones.get(i).getFecha().isAfter(inicio) && transacciones.get(i).getFecha().isBefore(fin)){
                transaccionesMes.add(transacciones.get(i));
            }
        }
        return transaccionesMes;
    }

    /**
     * Permite obtener porcentajes de gatos e ingresos
     * @param mes mes a evaluar
     * @param anio anio a evaluar
     */
    public PorcentajeGastosIngresos obtenerPorcentajeGastosIngresos(int mes, int anio) {

        float ingresos = 0;
        float egresos = 0; //gastos

        for (Transaccion transaccion : transacciones){
            if(transaccion.getFecha().getMonthValue() == mes && transaccion.getFecha().getYear() == anio){
                if(transaccion.getBilleteraOrigen() == this) {
                    egresos += transaccion.getMonto() + transaccion.getComision();
                }else{
                    ingresos += transaccion.getMonto();
                }
            }
        }

        float total = ingresos + egresos;
        float porcentajeGastos = (egresos / total) * 100;
        float porcentajeIngresos = (ingresos / total) * 100;

        return new PorcentajeGastosIngresos(
                porcentajeGastos,
                porcentajeIngresos
        );
    }

    public ArrayList<Transaccion> obtenerTransacciones() {
        return transacciones;
    }

    public String getNumero() {
        return numero;
    }

    public float consultarSaldo() {
        return saldo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}