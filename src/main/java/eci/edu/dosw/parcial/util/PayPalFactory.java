package eci.edu.dosw.parcial.util;

public class PayPalFactory implements PagoFactory {
    public MetodoPago crearPago() { return new PagoPayPal(); }
    public ValidadorPago crearValidador() { return new ValidadorPayPal(); }
}