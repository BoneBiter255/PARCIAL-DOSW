package eci.edu.dosw.parcial.util;

public class TarjetaFactory implements PagoFactory {
    public MetodoPago crearPago() { return new PagoTarjeta(); }
    public ValidadorPago crearValidador() { return new ValidadorTarjeta(); }
}