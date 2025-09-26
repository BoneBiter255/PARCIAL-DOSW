package eci.edu.dosw.parcial.util;

public class PagoTarjeta implements MetodoPago {
    public void pagar(double monto) {
        System.out.println("Pago con tarjeta: $" + monto);
    }
}