package eci.edu.dosw.parcial.util;

public class PagoPayPal implements MetodoPago {
    public void pagar(double monto) {
        System.out.println("Pago con PayPal: $" + monto);
    }
}