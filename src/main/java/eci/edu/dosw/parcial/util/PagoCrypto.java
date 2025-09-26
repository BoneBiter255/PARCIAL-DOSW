package eci.edu.dosw.parcial.util;

public class PagoCrypto implements MetodoPago {
    public void pagar(double monto) {
        System.out.println("Pago con Criptomonedas: $" + monto);
    }
}