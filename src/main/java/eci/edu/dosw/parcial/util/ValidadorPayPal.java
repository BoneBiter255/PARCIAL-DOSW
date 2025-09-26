package eci.edu.dosw.parcial.util;

public class ValidadorPayPal implements ValidadorPago {
    public boolean validar() {
        System.out.println("Validando PayPal...");
        return true;
    }
}